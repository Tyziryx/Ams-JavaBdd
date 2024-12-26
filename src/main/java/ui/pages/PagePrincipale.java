package main.java.ui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.chart.*;
import main.java.data.entities.Vente;
import main.java.data.sql.Gestion;
import main.java.data.sql.Tables;
import main.java.ui.components.Table;
import main.java.util.Colonne;


import java.net.URL;
import java.util.ArrayList;

public class PagePrincipale extends Page{
    public PagePrincipale(BorderPane page, double spacing) throws Exception {
        super(spacing, "Accueil");
        this.page = page ;
        ObservableList<Node> components = this.getChildren();
        components.addAll(title);

        Gestion.getTable(Tables.VENTE);


        HBox hBox = new HBox();





        ObservableList<PieChart.Data> produit =
                FXCollections.observableArrayList(



                        new PieChart.Data(" er", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30),
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                         new PieChart.Data("Pears", 22),
                         new PieChart.Data("Apples", 30));

        PieChart pieChart = new PieChart(produit);
        pieChart.setTitle("Ventes par produit");
        pieChart.setLabelsVisible(false);
        pieChart.setLegendVisible(true);
        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.LEFT);
        hBox.getChildren().add(pieChart);
        components.add(hBox);

    }



}
