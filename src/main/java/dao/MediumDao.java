/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import metier.modele.Medium;

/**
 *
 * @author hp
 */
public class MediumDao {

    public static void create(Medium medium) {
        JpaUtil.obtenirContextePersistance().persist(medium);
    }

    public static Medium findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Medium.class, id);
    }

    // Ce service renvoie toutes les entités Medium triées par ordre alphabétique
    // (denmination).
    public static List<Medium> getAll() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Medium> query = em.createQuery("SELECT m FROM Medium m ORDER BY m.denomination", Medium.class);
        return query.getResultList();
    }

    public static void update(Medium medium) {
        JpaUtil.obtenirContextePersistance().merge(medium);
    }
}
