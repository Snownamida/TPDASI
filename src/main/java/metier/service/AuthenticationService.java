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
    public static void Authenticate(String email, String password) {

        // Extract domain name
        String[] parts = email.split("@");
        if (parts.length == 2) {
            String domain = parts[1];
            // Check if domain name is "predictif"
            if (domain.equals("predictif.com")) {
                EmployeeService.authentifierEmploye(email, password);
            } else {
                ClientService.authentifierClient(email, password);
            }
        } else {
            System.out.println("Invalid email format");
        }
    }
}
