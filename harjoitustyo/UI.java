package harjoitustyo;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

import harjoitustyo.dokumentit.Dokumentti;
import harjoitustyo.dokumentit.Uutinen;
import harjoitustyo.dokumentit.Vitsi;
import harjoitustyo.kokoelma.Kokoelma;

/**
 * 
 * @author Eero Visuri
 * 
 * UI-luokka sisältää käyttöliittymän ohjelmalle. Komennot on luokkavakioitu
 * ja niitä kysellään käyttäjältä while-silmukassa. Luokasta kutsutaan Kokoelma-luokan
 * metodeja. 
 *
 */


public class UI {
    

    // importataan lukija
    public static final Scanner LUKIJA = new Scanner(System.in);
    
    //komennot luokkavakioina
    
    public static final String PRINTTAUS = "print";
    
    public static final String KAIUTUS = "echo";
    
    public static final String LOPETUS = "quit";
    
    public static final String LISAYS = "add";
    
    public static final String ETSI = "find";
    
    public static final String POISTA = "remove";
    
    public static final String SIISTI = "polish";
    
    public static final String ALUSTA = "reset";

    
    /**
     * 
     * @param komento
     * @param kokoelma
     * 
     * Tämä metodi saa parametrinaan komennon (joka on print tai
     * print ja kokonaisluku) ja tulostettavan kokoelman. Metodi
     * tulostaa joko koko kokoelman jos parametri oli pelkkä print,
     * ja tunnisteen perusteella tietyn dokumentin jos toinen
     * parametri oli kokonaisluku joka vastaa kokoelmasta löytyvää
     * tunnistetta.
     */

    public static void tulosta(String komento, Kokoelma kokoelma) {



        // pilkotaan komento paloihin
        String[] komennonpalat = komento.split(" ");
        // jos kokoelma on tyhjä, heitetään virheviesti
        if (kokoelma == null) {
            System.out.println("Error!");
            return;
        }

        // jos komentona on pelkkä "print" tulostetaan koko kokoelma ulos
        if (komennonpalat.length == 1 && komento.equals(PRINTTAUS)) {
            for (int i = 0; i < kokoelma.dokumentit().size(); i++) {
                System.out.println(kokoelma.dokumentit().get(i));
            }
            return;
        }

        // jos komennossa on kaksi osaa, toinen osista on haettavan dokumentin tunniste
        // try-catch jossa käsitellään virhe, jos toinen osa komentoa ei ole
        // kokonaisluku
        // ja käsitellään Out of Bounds-virhe.
        try {
            if (komennonpalat.length == 2) {
                int tulostettavatunniste = Integer.parseInt(komennonpalat[1]);
                if (kokoelma.hae(tulostettavatunniste) != null) {
                    System.out.println(kokoelma.hae(tulostettavatunniste));
                } else {
                    System.out.println("Error!");
                }

            } else {
                System.out.println("Error!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error!");
        }

    }

    /**
     * 
     * @param tiedostonnimi
     * @param sulkusanat
     * 
     * Metodi käynnistää ohjelman käyttöliittymän ja lataa parametrinaan saamansa tiedoston 
     * ja sulkusanat ohjelman rakenteisiin. 
     */
    
    public void aloita(String tiedostonnimi, String sulkusanat) {
        // lippumuuttujat ohjelman suorittamiselle ja komentojen kaiuttamiselle
        boolean suoritetaan = true;
        boolean kaiutetaan = false;

        // tulostetaan tervehdys
        System.out.println("Welcome to L.O.T.");




        // luodaan uusi kokoelma, johon vitsejä/uutisia ruvetaan tunkemaan
        Kokoelma kokoelma = new Kokoelma();

        // testataan onnistuuko tiedoston lataaminen

        if (!kokoelma.lataaTiedosto(tiedostonnimi, kokoelma)) {
            System.out.println("Program terminated.");
            suoritetaan = false;
        }

        // pääasiallinen käyttöliittymän while-looppi
        while (suoritetaan) {

            // kysytään komento

            System.out.println("Please, enter a command:");

            // sijoitetaan komento Stringiin

            String komento = LUKIJA.nextLine();

            // Komentojen kaiutuksen lippumuuttujan vaihtaminen jos komentona on "echo".
            if (komento.equals(KAIUTUS)) {
                kaiutetaan = !kaiutetaan;
                if (kaiutetaan == true) {
                    System.out.println("echo");
                }
                continue;
            }
            // komentojen kaiutus
            if (kaiutetaan) {
                System.out.println(komento);
            }

            // jos komento on "quit", suljetaan ohjelma
            if (komento.equals(LOPETUS)) {
                System.out.println("Program terminated.");
                suoritetaan = false;
                System.exit(0);
            }
            // jos komennossa on "print", annetaan se tulosta-metodille. Virheentarkistus
            // metodissa.
            else if (komento.contains(PRINTTAUS)) {
                tulosta(komento, kokoelma);
            }
            // jos komennossa on "add", pilkotaan komento paloihin
            // ja suoritetaan virheentarkistus parametrien oikeellisuudesta
            else if (komento.contains(LISAYS)) {
                if (komento.length() < 4) {
                    System.out.println("Error!");
                    continue;
                }
                String palat[] = komento.split(" ", 2);
                if (palat.length > 2 || palat.length < 2) {
                    System.out.println("Error!");
                    continue;
                }

                // sijoitetaan vitsin/uutisen sisältävä pala rivi-nimiseen merkkijonoon
                String rivi = palat[1];
                // tarkastetaan onko kyseessä uutinen vai vitsi onkoUutinen-metodilla.
                Boolean uutinen = kokoelma.onkoUutinen(rivi);
                // jos kyseessä on uutinen, ja käsiteltävä kokoelma on uutisia
                // lisätään kokoelmaan luoRivistaUutinen-metodilla
                if (uutinen && kokoelma.kokoelmanTyyppi == "uutinen") {
                    try {
                        kokoelma.lisää(kokoelma.luoRivistaUutinen(rivi));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error!");
                    }

                }
                // jos kyseessä on vitsi, ja käsiteltävä kokoelma on vitsi-tyyppinen
                // lisätään kokoelmaan luoRivistaVitsi-metodilla
                else if (!uutinen && kokoelma.kokoelmanTyyppi == "vitsi") {
                    try {
                        kokoelma.lisää(kokoelma.luoRivistaVitsi(rivi));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error!");
                    }
                }
                // muussa tapauksessa jotain on pielessä, joten heitetään error.
                else {
                    System.out.println("Error!");
                }

            }
            //jos komento on etsi, kutsutaan kokoelma-luokan hakuSanoilla-metodia.
            else if (komento.contains(ETSI)) {
                kokoelma.hakuSanoilla(komento, kokoelma);
            }
            //jos komento on poista, kutsutaan kokoelma-luokan poistaSana-metodia. 
            else if (komento.contains(POISTA)) {
                kokoelma.poistaSana(komento, kokoelma);

            }
            //jos komento on siisti, kutsutaan kokoelma-luokan siisti-metodia. 
            else if (komento.contains(SIISTI)) {
                String[] komennonpalat = komento.split(" ");
                if (komennonpalat.length == 1 || komennonpalat.length > 2) {
                    System.out.println("Error!");
                    continue;
                }
                kokoelma.siisti(komento, kokoelma, sulkusanat);
                
            }
            //jos komento on alusta, kutsutaan kokoelma-luokan lataaTiedosto-metodia.
            else if (komento.equals(ALUSTA)) {
                // Lataa dokumenttitiedoston uudelleen ja poistaa aiemmin tehdyt muutokset.
                // Jos komennolle annetaan parametrejä, tulostetaan virheilmoitus.
                kokoelma = new Kokoelma();
                kokoelma.lataaTiedosto(tiedostonnimi, kokoelma);
            }
            //muuten voimme olettaa, että jotain meni pieleen.
            else {
                System.out.println("Error!");
            }
    }


    }
}

