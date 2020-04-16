/* eero.visuri@tuni.fi
 * OOPE2 - Olio-ohjelmoinnin perusteet 2
 * Harjoitustyö
 */


package harjoitustyo.dokumentit;

import harjoitustyo.apulaiset.*;

import java.util.LinkedList;



import java.util.*;
import java.time.*;

public abstract class Dokumentti implements Comparable<Dokumentti>, Tietoinen<Dokumentti> {

    /*
     * Attribuutit
     */

    private int tunniste;

    private String teksti;

    /*
     * Aksessorit
     */

    public int tunnite() {
        return tunniste;
    }

    // getteri
    public String teksti() {
        return teksti;
    }

    // setteri
    public void tunniste(int tunniste) throws IllegalArgumentException {
        if (tunniste > 0) {
            this.tunniste = tunniste;
        } else {
            throw new IllegalArgumentException("Dokumentin tunniste null.");
        }
    }

    // setteri
    public void teksti(String teksti) throws IllegalArgumentException {
        if (teksti != null) {
            this.teksti = teksti;
        }
        throw new IllegalArgumentException("Dokumentin teksti null.");
    }

    // parametrillinen rakentaja
    public Dokumentti(int tunniste, String teksti) throws IllegalArgumentException {
        if (tunniste <= 0 || teksti == null || teksti.length() <= 0) {
            throw new IllegalArgumentException("Dokumentin tunnisteessa/tekstissä vikaa.");
        }
        this.teksti = teksti;
        this.tunniste = tunniste;
    }

    /*
     * Korvattu equals-metodi siten, että dokumentit katsotaan samoiksi, jos niiden
     * tunnisteet ovat sama kokonaisluku, jolloin equals palauttaa true. Muutoin se
     * palauttaa false.
     */

    @Override
    public boolean equals(Object olio) {
        Dokumentti kasiteltava = (Dokumentti) olio;
        if (this.tunniste  == kasiteltava.tunniste) {
            return true;
        }
        else {
            return false;
        }
        
        
    }

    /*
     * Korvattu compareTo-metodi, joka vertailee parametrina saatua Dokumentti
     * T-tunnistetta. Jos tunnisteet ovat samat, se palauttaa 0, jos tunniste on >
     * T.tunniste, se palauttaa 1, muuten -1. Metodilla voi päätellä, mihin kohtaan
     * listaa alkio tulee sijoittaa.
     */

    @Override
    public int compareTo(Dokumentti T) {
        if (tunniste == T.tunniste) {
            return 0;
        } else if (tunniste > T.tunniste) {
            return 1;
        } else {
            return -1;
        }
    }

    /*
     * Korvattu toString()-metodi siten, että se palauttaa tunnisteen ja tekstin
     * eroteltuna merkkijonolla "///".
     */
    @Override
    public String toString() {
        return tunniste + "///" + teksti;
    }

    /*
     * Tämä metodi etsii toisena parametrinaan saamasta teksti-muuttujasta
     * ensimmäisenä parametrinaan saatuja hakusanoja, jos se löytää hakusanat
     * tekstistä, se palauttaa true, muuten false.
     */

    public boolean sanatTäsmäävät(LinkedList<String> hakusanat, String teksti) throws IllegalArgumentException {

        if (hakusanat == null || hakusanat.size() <= 0) {
            throw new IllegalArgumentException("Hakusanat joko null tai koko <= 0");
        }

        boolean sanatsamat = false;

        for (int i = 0; i < hakusanat.size(); i++) {
            if (teksti.contains(hakusanat.get(i))) {
                sanatsamat = true;
                continue;
            } else {
                return sanatsamat;
            }
        }
        return sanatsamat;
    }

}
