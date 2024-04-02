package dao;

import metier.modele.Consultation;

public class ConsultationDao {
    
    public static void create(Consultation consultation) {
        JpaUtil.obtenirContextePersistance().persist(consultation);
    }
}
