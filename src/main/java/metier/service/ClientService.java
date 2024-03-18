/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.JpaUtil;
import metier.modele.Client;
import javax.persistence.RollbackException;
import util.GeoNetApi;
import util.Message;
import dao.ClientDao;

/**
 *
 * @author jsun
 */
public class ClientService {

    public static Boolean inscrireClient(Client client) {
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

    private static void sendConfirmationEmail(Client client) {
        String subject = "Inscription réussie";
        String body = "Merci, votre inscription a été enregistrée avec succès.";

        Message.envoyerMail("noreply@votreentreprise.com", client.getMail(), subject, body);
    }

    private static void sendErrorEmail(Client client, String error) {
        String subject = "Erreur lors de l'inscription";
        String body = "Désolé, une erreur est survenue lors de votre inscription.\nErreur : " + error;

        Message.envoyerMail("noreply@votreentreprise.com", client.getMail(), subject, body);
    }
}
