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

    public int tunniste() {
        return tunniste;
    }

    // getteri
    public String teksti() {
        return teksti;
    }

    // setteri
    public void tunniste(int tunniste) throws IllegalArgumentException {
        if (tunniste < 0) {
            throw new IllegalArgumentException("Dokumentin tunniste null.");
        }
        this.tunniste = tunniste;
    }

    // setteri
    public void teksti(String teksti) throws IllegalArgumentException {
        if (teksti == null) {
            throw new IllegalArgumentException("Dokumentin teksti null.");
        }
        this.teksti = teksti;
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
     * palauttaa false. Try-catch muodossa jotta ei kaadu null-arvoihin.
     */

    @Override
    public boolean equals(Object olio) {
        try {
            Dokumentti kasiteltava = (Dokumentti) olio;
            if (this.tunniste == kasiteltava.tunniste) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;

    }

    /*
     * Korvattu compareTo-metodi, joka vertailee parametrina saatua Dokumentti
     * T-tunnistetta. Jos tunnisteet ovat samat, se palauttaa 0, jos tunniste on >
     * T.tunniste, se palauttaa 1, muuten -1. Metodilla voi päätellä, mihin kohtaan
     * listaa alkio tulee sijoittaa.
     */

    @Override
    public int compareTo(Dokumentti T) {
        if (tunniste > T.tunniste) {
            return 1;
        } else if (tunniste < T.tunniste) {
            return -1;
        }
        return 0;
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

    @Override
    public boolean sanatTäsmäävät(LinkedList<String> hakusanat) throws IllegalArgumentException {
        // virheentarkistus
        if (hakusanat == null || hakusanat.size() <= 0) {
            throw new IllegalArgumentException("Hakusanat joko null tai koko <= 0");
        }

        // Pilkotaan teksti listaan
        String[] dokumentinTeksti = teksti.split(" ");

        for (int i = 0; i < hakusanat.size(); i++) {
            // boolean-tyyppinen muuttuja kertomaan ovatko sanat löytyneet
            boolean sanatlöytyi = false;

            for (int j = 0; j < dokumentinTeksti.length; j++) {
                // napataan tekstinpala merkkijonoksi
                String dokumentinTekstinPala = dokumentinTeksti[j];
                // katsotaan löytyykö se hakusanoista
                if (dokumentinTekstinPala.equals(hakusanat.get(i))) {
                    sanatlöytyi = true;
                }
            }
            // jos sanoja ei löytynyt, palautetaan false.
            if (!sanatlöytyi) {
                return false;
            }

        }
        // jos päästiin tänne asti, niin sanat löytyivät.
        return true;
    }

    /*
     * Metodi poistaa ensin dokumentin tekstistä kaikki annetut välimerkit ja
     * muuntaa sitten kaikki kirjainmerkit pieniksi ja poistaa lopuksi kaikki
     * sulkusanojen esiintymät.
     */
    @Override
    public void siivoa(LinkedList<String> sulkusanat, String välimerkit) 
            throws IllegalArgumentException {
        // virheentarkistus
        if (sulkusanat == null || välimerkit == null || välimerkit.length() <= 0 
                || sulkusanat.size() <= 0) { throw new 
            IllegalArgumentException("Siivoa-metodin virheelliset " + "sulkusanat/välimerkit!");
        }
        
        // poistetaan annetut välimerkit tekstistä
        for (int i = 0; i < välimerkit.length(); i++) {
            char merkki = välimerkit.charAt(i);
            String s = Character.toString(merkki);
            teksti = teksti.replace(s, "");
        }

        // Lisätään välilyönnit tekstin loppuun ja alkuun, jotta saadaan ensimmäinen
        // ja viimeinenkin sana poistettua asianmukaisesti.
        teksti = " " + teksti + " ";

        // muutetaan koko teksti pieniksi kirjaimiksi
        teksti = teksti.toLowerCase();

        // jos tekstistä löytyy sulkusanoja, poistetaan ne.
        for (int i = 0; i < sulkusanat.size(); i++) {
            while (teksti.contains(" " + sulkusanat.get(i) + " ")) {
                teksti = teksti.replace(" " + sulkusanat.get(i) + " ", " ");
            }
            

        }
        // Poistetaan tekstin ensimmäinen ja viimeinen välilyönti

        teksti = teksti.trim();
        
        //poistetaan kaksinkertaiset välilyönnit
        teksti = teksti.replaceAll("( )+", " ");

    }

}
