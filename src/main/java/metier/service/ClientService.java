/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.JpaUtil;
import metier.modele.Employee;
import javax.persistence.RollbackException;
import util.GeoNetApi;
import util.Message;
import dao.ClientDao;
import java.util.List;

/**
 *
 * @author jsun
 */
public class ClientService {

    public static Boolean inscrireClient(Employee client) {
        try {
            LatLng clientCoord = GeoNetApi.getLatLng(client.getAdressePostale());
            if (clientCoord == null) {
                sendErrorEmail(client, "Adresse postale invalide");
                return false;
            }

            client.setLatitude(clientCoord.lat);
            client.setLongitude(clientCoord.lng);

            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            ClientDao.create(client);
            JpaUtil.validerTransaction();

            // Send confirmation email
            sendConfirmationEmail(client);
            return true;

        } catch (RollbackException re) {
            re.printStackTrace();
            JpaUtil.annulerTransaction();
            sendErrorEmail(client, "Erreur lors de l'enregistrement dans la base de données (RollbackException)");
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
            sendErrorEmail(client, "Erreur lors de l'enregistrement dans la base de données (Other Exception)");
            return false;
        }
    }

    // Ce service identifie un client à partir de son adresse mail, puis vérifie si
    // le mot de passe indiqué correspond au mot de passe enregistré. Ce service
    // renvoie l'entité Client si l'authentification a réussie, ou null en cas
    // d'échec.
    public static Employee authentifierClient(String mail, String motDePasse) {

        JpaUtil.creerContextePersistance();
        Employee client = ClientDao.findByEmail(mail);
        if (client != null && client.getMotDePasse().equals(motDePasse)) {
            return client;
        } else {
            return null;
        }
    }

    public static Boolean updatePassword(Employee client, String newPassword) {
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            client.setMotDePasse(newPassword);
            ClientDao.update(client);
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

    public static Employee chercherClientParMail(String mail) {
        JpaUtil.creerContextePersistance();
        return ClientDao.findByEmail(mail);
    }

    public static Employee chercherClientParId(Long id) {
        JpaUtil.creerContextePersistance();
        return ClientDao.findById(id);
    }

    // Ce service renvoie toutes les entités Client triées par ordre alphabétique
    // (nom/prénom).
    public static List<Employee> consulterListeClients() {
        JpaUtil.creerContextePersistance();
        return ClientDao.getAll();
    }

    private static void sendConfirmationEmail(Employee client) {
        String subject = "Inscription réussie";
        String body = "Merci, votre inscription a été enregistrée avec succès.";

        Message.envoyerMail("noreply@votreentreprise.com", client.getMail(), subject, body);
    }

    private static void sendErrorEmail(Employee client, String error) {
        String subject = "Erreur lors de l'inscription";
        String body = "Désolé, une erreur est survenue lors de votre inscription.\nErreur : " + error;

        Message.envoyerMail("noreply@votreentreprise.com", client.getMail(), subject, body);
    }
}
