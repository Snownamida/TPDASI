/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import java.util.List;

import javax.persistence.RollbackException;

import dao.ConsultationDao;
import dao.EmployeeDao;
import dao.JpaUtil;
import dao.MediumDao;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employee;
import metier.modele.Medium;
import metier.modele.Spirite;
import metier.modele.Astrologer;
import metier.modele.Cartomancien;
import util.AstroNetApi;

/**
 *
 * @author staider
 */
public class EmployeeService {

    public static Boolean InitEmployee() {

        Employee emp1 = new Employee("Smith", "John", "john.smith@predictif.fr", "password1", "male", "1234567890");
        Employee emp2 = new Employee("Johnson", "Mary", "mary.johnson@predictif.fr", "password2", "female",
                "2345678901");
        Employee emp3 = new Employee("Brown", "James", "james.brown@predictif.fr", "password3", "male", "3456789012");

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            EmployeeDao.create(emp1);
            EmployeeDao.create(emp2);
            EmployeeDao.create(emp3);

            JpaUtil.validerTransaction();

            return true;

        } catch (RollbackException re) {
            re.printStackTrace();
            JpaUtil.annulerTransaction();
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
            return false;
        }
    }

    public static Boolean InitMedium() {

        Medium med1 = new Astrologer("ENS-Astro", "2006", "Serena",
                "Basée à Champigny-sur-Marne, Serena vous révèlera votre avenir pour éclairer votre passé.", "female",
                "../images/mediums/serena.jpg");
        Medium med2 = new Cartomancien("Mme Irma", "Votre avenir est devant vous: regardons-le ensemble!", "female",
                "../images/mediums/irma.jpg");
        Medium med3 = new Spirite("Marc de café", "Gwen",
                "Spécialiste des grandes conversations au-delà de TOUTES les frontières", "male",
                "../images/mediums/gwen.jpg");

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            MediumDao.create(med1);
            MediumDao.create(med2);
            MediumDao.create(med3);

            JpaUtil.validerTransaction();

            return true;

        } catch (RollbackException re) {
            re.printStackTrace();
            JpaUtil.annulerTransaction();
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
            return false;
        }
    }

    // Ce service identifie un client à partir de son adresse mail, puis vérifie si
    // le mot de passe indiqué correspond au mot de passe enregistré. Ce service
    // renvoie l'entité Client si l'authentification a réussie, ou null en cas
    // d'échec.
    public static long authentifierEmploye(String mail, String motDePasse) {

        JpaUtil.creerContextePersistance();
        Employee employee = EmployeeDao.findByEmail(mail);
        if (employee != null && employee.getMotDePasse().equals(motDePasse)) {
            return employee.getId();
        } else {
            return -1;
        }
    }

    public static Employee chercherEmployeParMail(String mail) {
        JpaUtil.creerContextePersistance();
        return EmployeeDao.findByEmail(mail);
    }

    // Ce service renvoie toutes les entités Client triées par ordre alphabétique
    public static List<Employee> consulterListeEmployes() {
        JpaUtil.creerContextePersistance();
        return EmployeeDao.getAll();
    }

    public static List<String> GetHelp(Client client, int love, int health, int job) {

        AstroNetApi astroApi = new AstroNetApi();
        try {
            List<String> predictions = astroApi.getPredictions(client.getAstralProfile().getCouleurPorteBonheur(),
                    client.getAstralProfile().getAnimalTotem(), love, health, job);
            return predictions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void FinConsultation(Consultation consultation, String comment, int duration) {
        consultation.setCommentaire(comment);
        consultation.setDuree(duration);
        ConsultationDao.update(consultation);
    }
}
