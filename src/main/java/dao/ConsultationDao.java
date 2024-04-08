/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import metier.modele.Consultation;

import java.util.Map;
import java.util.ArrayList;

/**
 *
 * @author staider
 */
public class ConsultationDao {

    public static void create(Consultation consultation) {
        JpaUtil.obtenirContextePersistance().persist(consultation);
    }

    public static void update(Consultation consultation) {
        JpaUtil.obtenirContextePersistance().merge(consultation);
    }

    public static List<Consultation> getAll() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Consultation> query = em.createQuery("SELECT c FROM Consultation c", Consultation.class);
        return query.getResultList();
    }

    // Get the number of consultations per client
    public static List<Map<String, Object>> getConsultationByClient() {
        List<Map<String, Object>> consultationDataList = new ArrayList<>();
        ResultSet res = null;
        try {
            // Charger la classe du pilote (driver) du SGBD cible
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            // Ouvrir une connexion
            String url_db = "jdbc:derby://localhost:1527/DASI TP DB [dasi on DASI]";
            Connection cx = DriverManager.getConnection(url_db, "dasi", "dasi");
            // Créer une instruction et exécuter la requête
            Statement stat = cx.createStatement();
            res = stat
                    .executeQuery("SELECT client, count(*) AS consultation_count FROM Consultation GROUP BY client");
            // Extraire les données du résultat de la requête
            while (res.next()) {
                String client = res.getString("client");
                int count = res.getInt("consultation_count");
                Map<String, Object> consultationData = new HashMap<>();
                consultationData.put("client", client);
                consultationData.put("consultation_count", count);
                consultationDataList.add(consultationData);
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("Driver not found.");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.err.println("SQL Exception occurred.");
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error while closing result set.");
                ex.printStackTrace();
            }
        }
        return consultationDataList;
    }

    // Get the number of consultations per Medium
    public static List<Map<String, Object>> getConsultationByMedium() {
        List<Map<String, Object>> consultationDataList = new ArrayList<>();
        ResultSet res = null;
        try {
            // Charger la classe du pilote (driver) du SGBD cible
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            // Ouvrir une connexion
            String url_db = "jdbc:derby://localhost:1527/DASI TP DB [dasi on DASI]";
            Connection cx = DriverManager.getConnection(url_db, "dasi", "dasi");
            // Créer une instruction et exécuter la requête
            Statement stat = cx.createStatement();
            res = stat
                    .executeQuery("SELECT medium, count(*) AS consultation_count FROM Consultation GROUP BY medium");
            // Extraire les données du résultat de la requête
            while (res.next()) {
                String medium = res.getString("medium");
                int count = res.getInt("consultation_count");
                Map<String, Object> consultationData = new HashMap<>();
                consultationData.put("medium", medium);
                consultationData.put("consultation_count", count);
                consultationDataList.add(consultationData);
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("Driver not found.");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.err.println("SQL Exception occurred.");
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error while closing result set.");
                ex.printStackTrace();
            }
        }
        return consultationDataList;
    }

    // Get the number of consultations per Employe
    public static List<Map<String, Object>> getConsultationByEmployee() {
        List<Map<String, Object>> consultationDataList = new ArrayList<>();
        ResultSet res = null;
        try {
            // Charger la classe du pilote (driver) du SGBD cible
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            // Ouvrir une connexion
            String url_db = "jdbc:derby://localhost:1527/DASI TP DB [dasi on DASI]";
            Connection cx = DriverManager.getConnection(url_db, "dasi", "dasi");
            // Créer une instruction et exécuter la requête
            Statement stat = cx.createStatement();
            res = stat
                    .executeQuery("SELECT employee, count(*) AS consultation_count FROM Consultation GROUP BY employee");
            // Extraire les données du résultat de la requête
            while (res.next()) {
                String employe = res.getString("employee");
                int count = res.getInt("consultation_count");
                Map<String, Object> consultationData = new HashMap<>();
                consultationData.put("employe", employe);
                consultationData.put("consultation_count", count);
                consultationDataList.add(consultationData);
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("Driver not found.");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.err.println("SQL Exception occurred.");
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error while closing result set.");
                ex.printStackTrace();
            }
        }
        return consultationDataList;
    }
}