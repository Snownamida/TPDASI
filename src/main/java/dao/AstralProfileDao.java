package dao;

import metier.modele.AstralProfile;

public class AstralProfileDao {

    public static void create(AstralProfile astralProfile) {
        JpaUtil.obtenirContextePersistance().persist(astralProfile);
    }



}
