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

public class UI {
    

    // importataan lukija
    public static final Scanner LUKIJA = new Scanner(System.in);


    public static void tulosta(String komento, Kokoelma kokoelma) {

        /*
         * Tämä metodi saa parametrinaan komennon (joka on print tai print ja
         * kokonaisluku) ja tulostettavan kokoelman. Metodi tulostaa joko koko kokoelman
         * jos parametri oli pelkkä print, ja tunnisteen perusteella tietyn dokumentin
         * jos toinen parametri oli kokonaisluku joka vastaa kokoelmasta löytyvää
         * tunnistetta.
         */

        // pilkotaan komento paloihin
        String[] komennonpalat = komento.split(" ");
        // jos kokoelma on tyhjä, heitetään virheviesti
        if (kokoelma == null) {
            System.out.println("Error!");
            return;
        }

        // jos komentona on pelkkä "print" tulostetaan koko kokoelma ulos
        if (komennonpalat.length == 1 && komento.equals("print")) {
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
            if (komento.equals("echo")) {
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
            if (komento.equals("quit")) {
                System.out.println("Program terminated.");
                suoritetaan = false;
                System.exit(0);
            }
            // jos komennossa on "print", annetaan se tulosta-metodille. Virheentarkistus
            // metodissa.
            else if (komento.contains("print")) {
                tulosta(komento, kokoelma);
            }
            // jos komennossa on "add", pilkotaan komento paloihin
            // ja suoritetaan virheentarkistus parametrien oikeellisuudesta
            else if (komento.contains("add")) {
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

            else if (komento.contains("find")) {
                kokoelma.hakuSanoilla(komento, kokoelma);
            }

            else if (komento.contains("remove")) {
                kokoelma.poistaSana(komento, kokoelma);

            }

            else if (komento.contains("polish")) {
                String[] komennonpalat = komento.split(" ");
                if (komennonpalat.length == 1 || komennonpalat.length > 2) {
                    System.out.println("Error!");
                    continue;
                }
                kokoelma.siisti(komento, kokoelma, sulkusanat);
                
            }

            else if (komento.equals("reset")) {
                // Lataa dokumenttitiedoston uudelleen ja poistaa aiemmin tehdyt muutokset.
                // Jos komennolle annetaan parametrejä, tulostetaan virheilmoitus.
                kokoelma = new Kokoelma();
                kokoelma.lataaTiedosto(tiedostonnimi, kokoelma);
            }

            else {
                System.out.println("Error!");
            }
    }


    }
}

