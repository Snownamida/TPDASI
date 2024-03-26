/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

/**
 *
 * @author snownamida
 */
public class Spirite extends Medium {

    private String support;

    public Spirite(String support, String denomination, String presentation, String genre, String profilePhotoPath) {
        super(denomination, presentation, genre, profilePhotoPath);
        this.support = support;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

}
