/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import metier.modele.Client;
import metier.modele.Consultation;

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



}
