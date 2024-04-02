/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.RollbackException;

import com.google.maps.model.LatLng;

import dao.ClientDao;
import dao.JpaUtil;
import metier.modele.AstralProfile;
import metier.modele.Client;
import util.AstroNetApi;
import util.GeoNetApi;
import util.Message;

/**
 *
 * @author jsun
 */
public class ClientService {

    public static Boolean inscrireClient(String nom, String prenom, String mail, String motDePasse,
            String adressePostale, String phone,
            String birthdateString) {

        Date birthdate;
        try {
            birthdate = new SimpleDateFormat("yyyy-MM-dd").parse(birthdateString);
        } catch (ParseException e) {
            e.printStackTrace();
            Message.envoyerMail("noreply@votreentreprise.com", mail, "Erreur lors de l'inscription",
                    "Invalid date format. Please use yyyy-MM-dd");
            return false;
        }

        try {
            Client client = new Client(nom, prenom, mail, motDePasse, adressePostale, phone, birthdate);
            LatLng clientCoord = GeoNetApi.getLatLng(adressePostale);
            if (clientCoord == null) {
                sendErrorEmail(mail, "Adresse postale invalide");
                return false;
            }

            client.setLatitude(clientCoord.lat);
            client.setLongitude(clientCoord.lng);

            AstroNetApi astroApi = new AstroNetApi();

            List<String> profil = astroApi.getProfil(prenom, birthdate);
            String signeZodiaque = profil.get(0);
            String signeChinois = profil.get(1);
            String couleur = profil.get(2);
            String animal = profil.get(3);

            AstralProfile astralProfile = new AstralProfile(couleur, animal, signeChinois, signeZodiaque);

            client.setAstralProfile(astralProfile);

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
            sendErrorEmail(mail, "Erreur lors de l'enregistrement dans la base de données (RollbackException)");
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
            sendErrorEmail(mail, "Erreur lors de l'enregistrement dans la base de données (Other Exception)");
            return false;
        }
    }

    // Ce service identifie un client à partir de son adresse mail, puis vérifie si
    // le mot de passe indiqué correspond au mot de passe enregistré. Ce service
    // renvoie l'entité Client si l'authentification a réussie, ou null en cas
    // d'échec.
    public static long authentifierClient(String mail, String motDePasse) {

        JpaUtil.creerContextePersistance();
        Client client = ClientDao.findByEmail(mail);
        if (client != null && client.getMotDePasse().equals(motDePasse)) {
            return client.getId();
        } else {
            return -1;
        }
    }

    public static Client chercherClientParMail(String mail) {
        JpaUtil.creerContextePersistance();
        return ClientDao.findByEmail(mail);
    }

    public static Client chercherClientParId(Long id) {
        JpaUtil.creerContextePersistance();
        return ClientDao.findById(id);
    }

    // Ce service renvoie toutes les entités Client triées par ordre alphabétique
    // (nom/prénom).
    public static List<Client> consulterListeClients() {
        JpaUtil.creerContextePersistance();
        return ClientDao.getAll();
    }

    private static void sendConfirmationEmail(Client client) {
        String subject = "Inscription réussie";
        String body = "Merci, votre inscription a été enregistrée avec succès.";

        Message.envoyerMail("noreply@votreentreprise.com", client.getMail(), subject, body);
    }

    private static void sendErrorEmail(String destinataire, String error) {
        String subject = "Erreur lors de l'inscription";
        String body = "Désolé, une erreur est survenue lors de votre inscription.\nErreur : " + error;

        Message.envoyerMail("noreply@votreentreprise.com", destinataire, subject, body);
    }

}
