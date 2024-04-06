/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

/**
 *
 * @author staider
 */
public class AuthenticationService {

    public static Object[] Authenticate(String email, String password) {
        // Extract domain name
        String[] parts = email.split("@");
        if (parts.length == 2) {
            String domain = parts[1];
            // Check if domain name is "predictif"
            if (domain.equals("predictif.fr")) {
                Long id = EmployeeService.authentifierEmploye(email, password);
                if (id != -1) {
                    return new Object[] { id, "employee" };
                } else {
                    System.out.println("Invalid email or password");
                    return null;
                }
            } else {
                Long id = ClientService.authentifierClient(email, password);
                if (id != -1) {
                    return new Object[] { id, "client" };
                } else {
                    System.out.println("Invalid email or password");
                    return null;
                }
            }
        } else {
            System.out.println("Invalid email format");
            return null;
        }
    }
}
