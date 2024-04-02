package metier.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.RollbackException;

import dao.JpaUtil;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employee;
import metier.modele.Medium;

public class ConsultationService {

    public static Consultation creerConsultation(String dateString, int duree, String commentaire, Medium medium,
            Client client,
            Employee employee) {

        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        try {
            Consultation consultation = new Consultation(date, duree, medium, client, employee);
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            dao.ConsultationDao.create(consultation);
            JpaUtil.validerTransaction();
            return consultation;
        } catch (RollbackException re) {
            re.printStackTrace();
            JpaUtil.annulerTransaction();
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
            return null;
        }
    }

}
