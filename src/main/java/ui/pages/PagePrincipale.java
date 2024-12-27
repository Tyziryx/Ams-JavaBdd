package main.java.ui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.util.stream.Collectors;
import javafx.scene.layout.VBox;
import javafx.scene.chart.*;
import main.java.data.sql.Gestion;


import java.net.URL;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.IntStream;

public class PagePrincipale extends Page {
    private final BorderPane page;
    VBox vbox = new VBox();

    public PagePrincipale(BorderPane page, double spacing) throws Exception {
        super(spacing, "Accueil");
        this.page = page;
        ObservableList<Node> components = this.getChildren();
        components.addAll(title);

        LinkedHashMap<String, Integer> dataJour = loadDataJour();
        if (dataJour.isEmpty()) {
            components.add(new Label("Aucune donnée disponible JOUR."));
            System.out.println(dataJour);
            return;
        }
        LinkedHashMap<String, Integer> dataMois = loadDataMois();
        if (dataMois.isEmpty()) {
            components.add(new Label("Aucune donnée disponible MOIS."));
            return;
        }

        PieChart pieChartJour = createPieChart(dataJour);
        PieChart pieChartMois = createPieChart(dataMois);
        HBox piechartHbox = new HBox();
        piechartHbox.getChildren().add(pieChartJour);
        piechartHbox.getChildren().add(pieChartMois);
        vbox.getChildren().add(piechartHbox);


        HBox barChart = CreateBarChart(page);
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
        pieChart.setTitle("Ventes par produit");
        pieChart.setLabelsVisible(false);
        pieChart.setLegendVisible(true);

        pieChart.getData().addAll(
                IntStream.range(0, nomListe.size()).mapToObj(i -> new PieChart.Data(nomListe.get(i), benefListe.get(i))).collect(Collectors.toList()));
        return pieChart;
    }

    private HBox CreateBarChart(BorderPane page) throws Exception {

        // Définir les axes
        CategoryAxis axeXJour = new CategoryAxis();
        axeXJour.setCategories(FXCollections.observableArrayList("Bénéfices", "Coûts", "CA"));

        NumberAxis axeYJour = new NumberAxis();
        axeYJour.setLabel("Montant (€)");

        CategoryAxis axeXMois = new CategoryAxis();
        axeXJour.setCategories(FXCollections.observableArrayList("Bénéfices", "Coûts", "CA"));


        NumberAxis axeYMois = new NumberAxis();
        axeYJour.setLabel("Montant (€)");


        // Créer le BarChart
        BarChart<String, Number> barChartJour = new BarChart<>(axeXJour, axeYJour);
        barChartJour.setTitle("Analyse des ventes du jour");

        BarChart<String, Number> barChartMois = new BarChart<>(axeXMois, axeYMois);
        barChartMois.setTitle("Analyse des ventes du mois");

        // Charger les données
        LinkedHashMap<String, Integer> dataJour = loadDataJour();
        LinkedHashMap<String, Integer> dataMois = loadDataMois();

        // Vérification des données
        if (dataJour == null || dataJour.isEmpty()) {
            throw new Exception("Les données du jour n'ont pas pu être chargées.");
        }

        // Extraire les bénéfices, coûts et CA
        int totalBeneficesJour = dataJour.values().stream().mapToInt(Integer::intValue).sum();
        int totalCoutsJour = calculateTotalCoutsJour(); // Méthode à définir pour récupérer les coûts
        int totalCAJour = totalBeneficesJour + totalCoutsJour;


        // Créer les séries pour le graphique
        XYChart.Series<String, Number> seriesJour = new XYChart.Series<>();
        seriesJour.setName("Données du jour");

        seriesJour.getData().add(new XYChart.Data<>("Bénéfices", totalBeneficesJour));
        seriesJour.getData().add(new XYChart.Data<>("Coûts", totalCoutsJour));
        seriesJour.getData().add(new XYChart.Data<>("CA", totalCAJour));


        XYChart.Series<String, Number> seriesMois = new XYChart.Series<>();
        seriesMois.setName("Données du mois");

        int totalBeneficesMois = dataMois.values().stream().mapToInt(Integer::intValue).sum();
        int totalCoutsMois = calculateTotalCoutsMois(); // Méthode à définir pour récupérer les coûts
        int totalCAMois = totalBeneficesMois + totalCoutsMois;

        seriesMois.getData().add(new XYChart.Data<>("Bénéfices", totalBeneficesMois));
        seriesMois.getData().add(new XYChart.Data<>("Coûts", totalCoutsMois));
        seriesMois.getData().add(new XYChart.Data<>("CA", totalCAMois));

        // Ajouter les données au BarChart
        barChartJour.getData().add(seriesJour);
        barChartMois.getData().add(seriesMois);
        HBox hbox = new HBox();
        hbox.getChildren().add(barChartJour);
        hbox.getChildren().add(barChartMois);
        return hbox;
    }

    // Exemple de méthode pour calculer les coûts totaux
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

//    private void applyStyles(PieChart pieChart, LinkedHashMap<String, Integer> data) {
//        // Liste des couleurs à appliquer
//        List<String> colors = Arrays.asList("#5D414D", "#FFEE7D", "#AC7D88", "#2E236C", "#79BD8F",
//                "#D3E0EA", "#FFDEDE", "#FF9F66", "#F15A59", "#85603F");
//
//        // Appliquer les couleurs aux tranches du PieChart
//        int index = 0;
//        for (PieChart.Data slice : pieChart.getData()) {
//            String style = "-fx-pie-color: " + colors.get(index % colors.size()) + ";";
//            slice.getNode().setStyle(style);
//            index++;
//        }
//    }

}







