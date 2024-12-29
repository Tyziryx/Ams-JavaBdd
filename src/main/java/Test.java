package main.java;

import main.java.data.entities.Vente;
import main.java.data.sql.Connexion;
import main.java.data.sql.Gestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

public class Test {
    public static void main(String[] args) throws SQLException {

        Connexion connexion = new Connexion();
        connexion.connect();
        Connection conn = connexion.getConn();

        String insertSQL = "INSERT INTO vente (numero_ticket, id_produit, date_vente, prix_unite, quantite, id_lot_de_produit) VALUES (?, ?, ?, ?, ?, ?)";
        Random random = new Random();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(2);

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                for (int i = 0; i < 12; i++) {
                    int numeroTicket = random.nextInt(1000) + 1;
                    int idProduit = random.nextInt(35) + 1;
                    float prixUnite = random.nextFloat() * 100;
                    int quantite = random.nextInt(10) + 1;
                    UUID idLotDeProduit = UUID.randomUUID();

                    Vente vente = new Vente(numeroTicket, idProduit, java.sql.Date.valueOf(date), prixUnite, quantite, idLotDeProduit);

                    pstmt.setInt(1, vente.getNumero_ticket());
                    pstmt.setInt(2, vente.getId_produit());
                    pstmt.setDate(3, new java.sql.Date(vente.getDate_vente().getTime()));
                    pstmt.setFloat(4, vente.getPrix_unite());
                    pstmt.setInt(5, vente.getQuantite());
                    pstmt.setObject(6, vente.getId_lot_de_produit());
                    pstmt.addBatch();
                }
            }
            pstmt.executeBatch();
            System.out.println("Insertions completed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connexion.disconnect();
        }


    }

}
