/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier.service;

import java.util.List;

import javax.persistence.RollbackException;

import dao.EmployeeDao;
import dao.JpaUtil;
import metier.modele.Employee;

/**
 *
 * @author staider
 */
public class EmployeeService {

    public static Boolean InitEmployee() {

        Employee emp1 = new Employee("Smith", "John", "john.smith@predictif.fr", "password1", "male", "1234567890",
                true);
        Employee emp2 = new Employee("Johnson", "Mary", "mary.johnson@predictif.fr", "password2", "female",
                "2345678901", false);
        Employee emp3 = new Employee("Brown", "James", "james.brown@predictif.fr", "password3", "male", "3456789012",
                true);

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

    // Ce service identifie un client à partir de son adresse mail, puis vérifie si
    // le mot de passe indiqué correspond au mot de passe enregistré. Ce service
    // renvoie l'entité Client si l'authentification a réussie, ou null en cas
    // d'échec.
    public static Employee authentifierEmploye(String mail, String motDePasse) {

        JpaUtil.creerContextePersistance();
        Employee employee = EmployeeDao.findByEmail(mail);
        if (employee != null && employee.getMotDePasse().equals(motDePasse)) {
            return employee;
        } else {
            return null;
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
}
