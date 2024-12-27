package main.java.ui.pages;

import com.sun.javafx.charts.Legend;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.util.stream.Collectors;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.chart.*;
import main.java.data.entities.Vente;
import main.java.data.sql.Gestion;
import main.java.data.sql.Tables;
import main.java.ui.components.Table;
import main.java.util.Colonne;
import javafx.scene.paint.Color;


import java.net.URL;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.IntStream;

public class PagePrincipale extends Page {
    private final BorderPane page;

    public PagePrincipale(BorderPane page, double spacing) throws Exception {
        super(spacing, "Accueil");
        this.page = page;
        ObservableList<Node> components = this.getChildren();
        components.addAll(title);

        LinkedHashMap<String, Integer> dataJour = loadDataJour();
        if (dataJour.isEmpty()) {
            components.add(new Label("Aucune donnée disponible."));
            return;
        }
        LinkedHashMap<String, Integer> dataMois = loadDataMois();
        if (dataMois.isEmpty()) {
            components.add(new Label("Aucune donnée disponible."));
            return;
        }

        PieChart pieChartJour = createPieChart(dataJour);
        PieChart pieChartMois = createPieChart(dataMois);

        BarChart barChart = CreateBarChart(page);
        HBox hBox = new HBox(pieChartJour);
        hBox.getChildren().add(pieChartMois);
        hBox.getChildren().add(barChart);
        components.add(hBox);
    }

    private LinkedHashMap<String, Integer> loadDataJour() throws Exception {
        LinkedHashMap<String, Integer> dataJour = new LinkedHashMap<>();
        String query = "SELECT produit.nom, SUM((vente.prix_unite - prix_fournisseur.prix) * vente.quantite) AS benef " +
                "FROM vente " +
                "JOIN produit ON vente.id_produit = produit.id_produit " +
                "JOIN prix_fournisseur ON vente.id_produit = prix_fournisseur.id_produit " +
              " WHERE vente.date_vente >= CURRENT_DATE\n" +
                "  AND vente.date_vente < CURRENT_DATE + INTERVAL '1 day'" +
                "GROUP BY produit.nom ORDER BY benef DESC LIMIT 10";
        try (ResultSet rs = Gestion.execute(query)) {
            while (rs.next()) {

                String nom = rs.getString("nom");
                int benef = rs.getInt("benef");
               // java.sql.Timestamp dateVente = rs.getTimestamp("date_vente");
                if (nom != null) {
                    dataJour.put(nom, benef);
                }
               // System.out.println(dateVente);
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
         nomListe.add(nom);
        });

        PieChart pieChart = new PieChart();
        pieChart.getStylesheets().add("css/style.css");
        pieChart.setTitle("Ventes par produit");
        pieChart.setLabelsVisible(false);
        pieChart.setLegendVisible(true);


        pieChart.getData().addAll(
                IntStream.range(0,nomListe.size()).mapToObj(i -> new PieChart.Data(nomListe.get(i), benefListe.get(i))).collect(Collectors.toList()));
        return pieChart;
    }

    private BarChart<String, Number> CreateBarChart(BorderPane page) throws Exception {
        // Définir les axes
        CategoryAxis axeX = new CategoryAxis();
        axeX.setCategories(FXCollections.observableArrayList("Bénéfices", "Coûts", "CA"));

        NumberAxis axeY = new NumberAxis();
        axeY.setLabel("Montant (€)");

        // Créer le graphique
        BarChart<String, Number> barChart = new BarChart<>(axeX, axeY);
        barChart.setTitle("Analyse des ventes");

        // Charger les données
        LinkedHashMap<String, Integer> dataJour = loadDataJour();

        // Vérifier que les données sont bien présentes
        if (dataJour == null) {
            throw new Exception("Les données journalières ou mensuelles n'ont pas pu être chargées.");
        }

        // Créer les séries pour le jour
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Jour");
        series1.getData().add(new XYChart.Data<>("Bénéfices", dataJour.getOrDefault("benef", 0)));
        series1.getData().add(new XYChart.Data<>("Coûts", dataJour.getOrDefault("prix_fournisseur.prix", 0)));
        series1.getData().add(new XYChart.Data<>("CA",
                dataJour.getOrDefault("benef", 0) + dataJour.getOrDefault("prix_fournisseur.prix", 0)));

        // Créer les séries pour le mois

        // Ajouter les séries au graphique
        barChart.getData().addAll(series1);

        return barChart;


    }






  /*  private void applyStyles(PieChart pieChart, LinkedHashMap<String, Integer> data) {
        // Liste des couleurs à appliquer
        List<String> colors = Arrays.asList("#5D414D", "#FFEE7D", "#AC7D88", "#2E236C", "#79BD8F",
                "#D3E0EA", "#FFDEDE", "#FF9F66", "#F15A59", "#85603F");

        // Appliquer les couleurs aux tranches du PieChart
        int index = 0;
        for (PieChart.Data slice : pieChart.getData()) {
            String style = "-fx-pie-color: " + colors.get(index % colors.size()) + ";";
            slice.getNode().setStyle(style);
            index++;
        }
*/




    }









