/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import metier.modele.Employee;

/**
 *
 * @author snownamida
 */
public class EmployeeDao {

    public static void create(Employee employee) {
        JpaUtil.obtenirContextePersistance().persist(employee);
    }

    public static void update(Employee employee) {
        JpaUtil.obtenirContextePersistance().merge(employee);
    }
}
