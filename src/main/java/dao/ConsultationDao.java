/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employee;
import metier.modele.Medium;

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
    public static Map<Client, Long> getConsultationCountPerClient() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Object[]> query = em.createQuery("SELECT c.client, COUNT(c) FROM Consultation c GROUP BY c.client", Object[].class);
        List<Object[]> results = query.getResultList();
        Map<Client, Long> consultationCountPerClient = new HashMap<Client, Long>();
        for (Object[] result : results) {
            consultationCountPerClient.put((Client) result[0], (Long) result[1]);
        }
        return consultationCountPerClient;
    }

    // Get the number of consultations per medium
    public static Map<Medium, Long> getConsultationCountPerMedium() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Object[]> query = em.createQuery("SELECT c.medium, COUNT(c) FROM Consultation c GROUP BY c.medium", Object[].class);
        List<Object[]> results = query.getResultList();
        Map<Medium, Long> consultationCountPerMedium = new HashMap<Medium, Long>();
        for (Object[] result : results) {
            consultationCountPerMedium.put((Medium) result[0], (Long) result[1]);
        }
        return consultationCountPerMedium;
    }

    // Get the number of consultations per employee
    public static Map<Employee, Long> getConsultationCountPerEmployee() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Object[]> query = em.createQuery("SELECT c.employee, COUNT(c) FROM Consultation c GROUP BY c.employee", Object[].class);
        List<Object[]> results = query.getResultList();
        Map<Employee, Long> consultationCountPerEmployee = new HashMap<Employee, Long>();
        for (Object[] result : results) {
            consultationCountPerEmployee.put((Employee) result[0], (Long) result[1]);
        }
        return consultationCountPerEmployee;
    }
}