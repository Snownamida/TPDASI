/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.service;

import javax.persistence.RollbackException;

import dao.EmployeeDao;
import dao.JpaUtil;
import dao.MediumDao;
import metier.modele.Astrologer;
import metier.modele.Cartomancien;
import metier.modele.Employee;
import metier.modele.Medium;
import metier.modele.Spirite;

/**
 *
 * @author staider
 */
/**
 * This class provides methods for initializing the employee and medium data in the application.
 */
/**
 * This class provides methods for initializing the employee and medium data in the application.
 */
public class InitializationService {


    /**
     * Initializes the employee data.
     * 
     * @return true if the employee data is successfully initialized, false otherwise.
     */
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

    /**
     * Initializes the list of mediums.
     * 
     * @return true if the initialization is successful, false otherwise.
     */
    public static Boolean InitMedium() {

        Medium med1 = new Astrologer("ENS-Astro", "2006", "Serena",
                "Basée à Champigny-sur-Marne, Serena vous révèlera votre avenir pour éclairer votre passé.", "female",
                "../images/mediums/serena.jpg");
        Medium med2 = new Cartomancien("Irma", "Votre avenir est devant vous: regardons-le ensemble!", "female",
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
}
