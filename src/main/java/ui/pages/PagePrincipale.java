package main.java.ui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.data.sql.Gestion;
import Exception.GrapheDataException;

import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PagePrincipale extends Page {
    private final BorderPane page;
    VBox vbox = new VBox();

    public PagePrincipale(BorderPane page, double spacing) throws Exception {
        super(spacing, "Accueil");
        this.page = page;
        ObservableList<Node> components = this.getChildren();
        components.addAll(title);

        LinkedHashMap<String, Integer> dataJour = null;
        try {
            dataJour = loadDataJour();
            if (dataJour.isEmpty()) {
                throw new GrapheDataException("Aucune donnée disponible pour aujourd'hui");
            }
        } catch (GrapheDataException e) {
            components.add(new Label(e.getMessage()));
        }

        LinkedHashMap<String, Integer> dataMois;
        try {
            dataMois = loadDataMois();
            if (dataMois.isEmpty()) {
                throw new GrapheDataException("Aucune donnée disponible pour ce mois");
            }
        } catch (GrapheDataException e) {
            components.add(new Label(e.getMessage()));
            displayDefaultGraph();
            return;
        }

        HBox piechartHbox = new HBox(20); // Add spacing between pie charts
        piechartHbox.setPadding(new Insets(10)); // Add padding around the HBox
        if (dataJour != null && !dataJour.isEmpty()) {
            PieChart pieChartJour = createPieChart(dataJour);
            pieChartJour.setTitle("Top 10 du jour");
            pieChartJour.setPrefSize(400, 400); // Set preferred size
            piechartHbox.getChildren().add(pieChartJour);
        }

        PieChart pieChartMois = createPieChart(dataMois);
        pieChartMois.setTitle("Top 10 du mois");
        pieChartMois.setPrefSize(400, 400); // Set preferred size
        piechartHbox.getChildren().add(pieChartMois);

        vbox.getChildren().add(piechartHbox);

        HBox barChart = CreateBarChart(page, dataJour, dataMois);
        vbox.getChildren().add(barChart);

        components.add(vbox);
    }

    private LinkedHashMap<String, Integer> loadDataJour() throws Exception {
        LinkedHashMap<String, Integer> dataJour = new LinkedHashMap<>();
        String query = "SELECT produit.nom, SUM((vente.prix_unite - prix_fournisseur.prix) * vente.quantite) AS benef " +
                "FROM vente " +
                "JOIN produit ON vente.id_produit = produit.id_produit " +
                "JOIN prix_fournisseur ON vente.id_produit = prix_fournisseur.id_produit " +
                " WHERE vente.date_vente >= CURRENT_DATE" +
                "  AND vente.date_vente < CURRENT_DATE + INTERVAL '1 day'" +
                "GROUP BY produit.nom ORDER BY benef DESC LIMIT 10";
        try (ResultSet rs = Gestion.execute(query)) {
            while (rs.next()) {
                String nom = rs.getString("nom");
                int benef = rs.getInt("benef");
                if (nom != null) {
                    dataJour.put(nom, benef);
                }
            }
        }
        return dataJour;
    }

    private LinkedHashMap<String, Integer> loadDataMois() throws Exception {
        LinkedHashMap<String, Integer> dataMois = new LinkedHashMap<>();
        String query = "SELECT produit.nom, SUM((vente.prix_unite - prix_fournisseur.prix) * vente.quantite) AS benef\n" +
                "FROM vente\n" +
                "JOIN produit ON vente.id_produit = produit.id_produit\n" +
                "JOIN prix_fournisseur ON vente.id_produit = prix_fournisseur.id_produit\n" +
                "WHERE vente.date_vente >= CURRENT_DATE - INTERVAL '1 month' " +
                "GROUP BY produit.nom\n" +
                "ORDER BY benef DESC\n" +
                "LIMIT 10;";
        try (ResultSet rs = Gestion.execute(query)) {
            while (rs.next()) {
                String nom = rs.getString("nom");
                int benef = rs.getInt("benef");
                if (nom != null) {
                    dataMois.put(nom, benef);
                }
            }
        }
        return dataMois;
    }

    private PieChart createPieChart(LinkedHashMap<String, Integer> data) {
        double total = data.values().stream().mapToDouble(Integer::doubleValue).sum();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ArrayList<String> nomListe = new ArrayList<>();
        ArrayList<Integer> benefListe = new ArrayList<>();
        data.forEach((nom, benef) -> {
            double percentage = (benef / total) * 100;
            benefListe.add((int) percentage);
            nomListe.add(nom  +  " - " +  benef +"€");
        });

        PieChart pieChart = new PieChart();
        pieChart.getStylesheets().add("css/style.css");
        pieChart.setLabelsVisible(false);
        pieChart.setLegendVisible(true);

        pieChart.getData().addAll(
                IntStream.range(0, nomListe.size()).mapToObj(i -> new PieChart.Data(nomListe.get(i), benefListe.get(i))).collect(Collectors.toList()));
        return pieChart;
    }

    private HBox CreateBarChart(BorderPane page, LinkedHashMap<String, Integer> dataJour, LinkedHashMap<String, Integer> dataMois) throws Exception {
        CategoryAxis axeXJour = new CategoryAxis();
        axeXJour.setCategories(FXCollections.observableArrayList("Bénéfices", "Coûts", "CA"));

        NumberAxis axeYJour = new NumberAxis();
        axeYJour.setLabel("Montant (€)");

        CategoryAxis axeXMois = new CategoryAxis();
        axeXJour.setCategories(FXCollections.observableArrayList("Bénéfices", "Coûts", "CA"));

        NumberAxis axeYMois = new NumberAxis();
        axeYJour.setLabel("Montant (€)");

        BarChart<String, Number> barChartJour = new BarChart<>(axeXJour, axeYJour);
        barChartJour.setTitle("Analyse des ventes du jour");

        BarChart<String, Number> barChartMois = new BarChart<>(axeXMois, axeYMois);
        barChartMois.setTitle("Analyse des ventes du mois");

        HBox hbox = new HBox();

        if (dataJour != null && !dataJour.isEmpty()) {
            int totalBeneficesJour = dataJour.values().stream().mapToInt(Integer::intValue).sum();
            int totalCoutsJour = calculateTotalCoutsJour();
            int totalCAJour = totalBeneficesJour + totalCoutsJour;

            XYChart.Series<String, Number> seriesJour = new XYChart.Series<>();
            seriesJour.setName("Données du jour");

            seriesJour.getData().add(new XYChart.Data<>("Bénéfices", totalBeneficesJour));
            seriesJour.getData().add(new XYChart.Data<>("Coûts", totalCoutsJour));
            seriesJour.getData().add(new XYChart.Data<>("CA", totalCAJour));

            barChartJour.getData().add(seriesJour);
            hbox.getChildren().add(barChartJour);
        }

        int totalBeneficesMois = dataMois.values().stream().mapToInt(Integer::intValue).sum();
        int totalCoutsMois = calculateTotalCoutsMois();
        int totalCAMois = totalBeneficesMois + totalCoutsMois;

        XYChart.Series<String, Number> seriesMois = new XYChart.Series<>();
        seriesMois.setName("Données du mois");

        seriesMois.getData().add(new XYChart.Data<>("Bénéfices", totalBeneficesMois));
        seriesMois.getData().add(new XYChart.Data<>("Coûts", totalCoutsMois));
        seriesMois.getData().add(new XYChart.Data<>("CA", totalCAMois));

        barChartMois.getData().add(seriesMois);
        hbox.getChildren().add(barChartMois);

        return hbox;
    }

    private int calculateTotalCoutsJour() throws Exception {
        String query = "SELECT SUM(prix_fournisseur.prix * vente.quantite) AS total_couts " +
                "FROM vente " +
                "JOIN prix_fournisseur ON vente.id_produit = prix_fournisseur.id_produit " +
                "WHERE vente.date_vente >= CURRENT_DATE " +
                "  AND vente.date_vente < CURRENT_DATE + INTERVAL '1 day'";
        try (ResultSet rs = Gestion.execute(query)) {
            if (rs.next()) {
                return rs.getInt("total_couts");
            }
        }
        return 0;
    }

    private int calculateTotalCoutsMois() throws Exception {
        String query = "SELECT SUM(prix_fournisseur.prix * vente.quantite) AS total_couts " +
                "FROM vente " +
                "JOIN prix_fournisseur ON vente.id_produit = prix_fournisseur.id_produit " +
                "WHERE vente.date_vente >= CURRENT_DATE - INTERVAL '1 month'";
        try (ResultSet rs = Gestion.execute(query)) {
            if (rs.next()) {
                return rs.getInt("total_couts");
            }
        }
        return 0;
    }

    private void displayDefaultGraph() {
        System.out.println("Aucune donnée disponible. Affichage du graphe par défaut.");
    }
}