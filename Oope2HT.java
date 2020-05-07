/* eero.visuri@tuni.fi
 * OOPE2 - Olio-ohjelmoinnin perusteet 2
 * Harjoitustyö
 */

package harjoitustyo;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.ParseException;

import harjoitustyo.kokoelma.*;
import harjoitustyo.dokumentit.Dokumentti;
import harjoitustyo.dokumentit.Uutinen;
import harjoitustyo.dokumentit.Vitsi;

public class Oope2HT {

    // importataan lukija
    public static final Scanner LUKIJA = new Scanner(System.in);
    // muuttuja johon tallennetaan käsiteltävän kokoelman tyyppi, jottei lisätä
    // vitsejä uutisiiin.
    public static String kokoelmanTyyppi = new String();

    public static Boolean tarkistaArgumentit(String[] args) {
        /*
         * Tämä metodi tarkastaa saamansa käynnistysparametrit ja palauttaa true, jos ei
         * löydä virhettä ja false jos löytää niistä virheen.
         *
         */

        // jos parametreja on alle kaksi, tulostetaan virheviesti
        if (args.length != 2) {
            System.out.println("Wrong number of command-line arguments!");
            return false;
        }

        else if (args.length == 2) {
            String tiedostonnimi = args[0];
            String sulkusanatiedosto = args[1];
            File tmptiedosto = new File(tiedostonnimi);
            File tmpsulkusanat = new File(sulkusanatiedosto);
            boolean filuon = tmpsulkusanat.exists() && tmptiedosto.exists();
            if (filuon) {
                return true;
            }
        }
        System.out.println("Missing file!");
        return false;
    }

    public static Boolean lataaTiedosto(String tiedostonnimi, Kokoelma kokoelma) {
        /*
         * Metodi, joka lataa parametrinaan saamaan tiedoston nimen ohjelmaan sisään
         * käyttäen joko lataaVitsi tai lataaUutinen-metodeja. Metodi palauttaa true
         * onnistumisesta, ja false jos jotain meni pieleen.
         */

        Scanner tiedostonlukija = null;

        File tiedosto = new File(tiedostonnimi);

        try {
            tiedostonlukija = new Scanner(tiedosto);

        } catch (Exception e) {
            System.out.println("Missing file!");
            return false;
        }

        // Tarkastetaan tiedostonnimestä onko kyseessä vitsi vai uutinen, jos ei
        // kumpikaan,
        // palautetaan false.
        if (!tiedostonnimi.contains("jokes") && !tiedostonnimi.contains("news")) {
            tiedostonlukija.close();
            return false;
        }
        // jos tiedostonnimessä on "jokes" niin se sisältää vitsejä
        else if (tiedostonnimi.contains("jokes")) {
            // niin kauan kun lukijalla on rivejä, lisätään vitsejä kokoelmaan
            kokoelmanTyyppi = "vitsi";
            while (tiedostonlukija.hasNext()) {
                String rivi = (tiedostonlukija.nextLine());
                Vitsi uusiVitsi = luoRivistaVitsi(rivi);
                kokoelma.lisää(uusiVitsi);
            }
            // jos tiedoston nimessä on "news" niin se sisältää uutisia
        } else if (tiedostonnimi.contains("news")) {
            kokoelmanTyyppi = "uutinen";
            // niin kauan kun lukijalla on rivejä, lisätään uutisia
            while (tiedostonlukija.hasNext()) {
                String rivi = (tiedostonlukija.nextLine());
                Uutinen uusiUutinen = luoRivistaUutinen(rivi);
                kokoelma.lisää(uusiUutinen);
            }
        }

        // suljetaan lukija ja palautetaan true jos kaikki onnistui odotetusti
        tiedostonlukija.close();
        return true;
    }

    public static Vitsi luoRivistaVitsi(String rivi) {
        /*
         * Metodi ottaa parametrinaan merkkijonoja. Metodi pilkkoo merkkijonon osiin ja
         * passaa osat vitsi-luokan rakentajalle ja palauttaa uusiVitsi-Vitsin.
         *
         */;
        String[] vitsinpalat = rivi.split("///");
        int vitsintunniste = Integer.parseInt(vitsinpalat[0]);
        String vitsinlaji = vitsinpalat[1];
        String itsevitsi = vitsinpalat[2];
        Vitsi uusiVitsi = new Vitsi(vitsintunniste, vitsinlaji, itsevitsi);
        return uusiVitsi;
    }

    public static Uutinen luoRivistaUutinen(String rivi) {
        /*
         * Metodi ottaa parametrinaan merkkijonoja, suorittaa samat toiminnot kuin
         * vitsin kanssa, paitsi konvertoi uutisessa esiintyvän päivämäärän muotoon
         * LocalDate.
         */

        DateTimeFormatter pvmformaatti = DateTimeFormatter.ofPattern("d.M.yyyy");
        String[] uutisenpalat = rivi.split("///");
        int uutisentunniste = Integer.parseInt(uutisenpalat[0]);
        String uutisenpvmmerkit = uutisenpalat[1];
        LocalDate uutisenpvm = LocalDate.parse(uutisenpvmmerkit, pvmformaatti);
        String itseuutinen = uutisenpalat[2];
        Uutinen uusiUutinen = new Uutinen(uutisentunniste, uutisenpvm, itseuutinen);
        return uusiUutinen;
    }

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

    public static Boolean onkoUutinen(String rivi) {
        /*
         * Tämä metodi saa parametrinaan merkkijonon, ja se tarkistaa löytyykö
         * merkkijonosta päivämäärä. Jos päivämäärä löytyy, voidaan merkkijonoa pitää
         * uutisena ja palautetaan true, muuten palautetaan false.
         * 
         */
        DateTimeFormatter pvmformaatti = DateTimeFormatter.ofPattern("d.M.yyyy");
        try {
            String[] uutisenpalat = rivi.split("///");
            String uutisenpvmmerkit = uutisenpalat[1];
            LocalDate uutisenpvm = LocalDate.parse(uutisenpvmmerkit, pvmformaatti);
        } catch (Exception ex) {
            return false;
        }
        return true;

    }

    public static void hakuSanoilla(String komento, Kokoelma kokoelma) {
        /*
         * Tämä metodi saa parametreinaan merkkijonon, jossa on hakusanoja ja kokoelman,
         * josta hakusanat etsitään. Metodi käy läpi kokoelman, ja tulostaa niiden
         * dokumenttien tunnisteet, joista se löysi hakusanoja vastaavia.
         */

        // jos komennossa on liian vähän parametrejä, heitetään virhe
        if (komento.length() < 5) {
            System.out.println("Error!");
            return;
        }

        // pilkotaan parametrina saatu merkkijono sanoja listaksi
        String haettavat = komento.substring(5);
        String[] haettavatsanat = haettavat.split(" ");
        // lista jossa haetut sanat
        LinkedList<String> haetutsanat = new LinkedList<String>(Arrays.asList(haettavatsanat));

        // for-silmukka jossa käydään kokoelma läpi ja etsitään hakusanoja.
        for (Dokumentti dokumentti : kokoelma.dokumentit()) {
            if (dokumentti.sanatTäsmäävät(haetutsanat)) {
                System.out.println(dokumentti.tunniste());
            }
        }
        return;

    }

    public static void poistaSana(String komento, Kokoelma kokoelma) {
        /*
         * Metodi saa parametreinaan merkkijonona dokumentin tunnisteen ja kokoelman,
         * muuntaa sen merkkijonosta tunnisteen kokonaisluvuksi ja poistaa kokoelmasta
         * saadulla tunnisteella olevan dokumentin. Jos tunnisteella ei löydy
         * dokumenttia tai parametriarvoja ei ole annettu, parametrejä on enemmän kuin
         * yksi tai parametri on jotenkin viallinen, niin poistaminen epäonnistuu.
         */

        // tarkistetaan parametrit
        String[]komennonpalat = komento.split(" ");
        if (komennonpalat.length < 2) {
            System.out.println("Error!");
        }
        try {
            int poistettavatunniste = Integer.parseInt(komennonpalat[1]);
            for (int i = 0; i < kokoelma.dokumentit().size(); i++) {
                if (kokoelma.dokumentit().get(i).tunniste() == poistettavatunniste) {
                    kokoelma.dokumentit().remove(kokoelma.dokumentit().get(i));
                    return;
                }

            }
            System.out.println("Error!");
        } catch (Exception ParseException) {
            System.out.println("Error!");
        }

    }
    
    public static void siisti(String komento, Kokoelma kokoelma, String sulkusanat) {
        /*
         * Metodi ottaa parametreina merkkijonon, kokoelman ja sulkusanojen tiedoston nimen.
         * Metodi lukee sulkusanat LinkedListiin, jotta se voi passata ne dokumentti-luokan
         * siivoa-metodille. 
         */
        
        //luodaan lukija sulkusanatiedostolle
        Scanner sulkusanalukija = null;
        //try-catch jossa luetaan sulkusanat tiedostosta LinkedListiin, ja passataan siivoa-
        //metodille
        
        
        
        String siivottavat = komento.substring(7);
        
        try {
            //lasketaan pituus ja luodaan filu
            File sulkufilu = new File (sulkusanat);
            sulkusanalukija = new Scanner(sulkusanat);
            //filun pituus muodossa long, käännetään se muotoon int.
            Long sulkufilunpituus = sulkufilu.length();
            int filunpituus = sulkufilunpituus.intValue();
            
            //luodaan LinkedList
            LinkedList<String> sulkusanaLista = new LinkedList<>();
            //luetaan tiedostosta rivit sulkusanalistaan.
            for (int i = 0; i < filunpituus; i++) {
                String rivi = sulkusanalukija.nextLine();
                sulkusanaLista.add(rivi);
            }
            //Siivotaan kokoelmasta dokumentti-luokan siivoa-metodilla sulkusanaListalla olevat
            //sanat ja parametrina saadut siivottavat pois.
            for (Dokumentti dokumentti : kokoelma.dokumentit()) {
                dokumentti.siivoa(sulkusanaLista, siivottavat);
            }
            
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    public static void main(String[] args) {

        // lippumuuttujat ohjelman suorittamiselle ja komentojen kaiuttamiselle
        boolean suoritetaan = true;
        boolean kaiutetaan = false;

        // tulostetaan tervehdys
        System.out.println("Welcome to L.O.T.");

        // tarkastetaan komentorivin käynnistysparametrit
        if (!tarkistaArgumentit(args)) {
            System.out.println("Program terminated.");
            suoritetaan = false;
            System.exit(0);

        }
        //
        String tiedostonnimi = args[0];
        String sulkusanat = args[1];

        // luodaan uusi kokoelma, johon vitsejä/uutisia ruvetaan tunkemaan
        Kokoelma kokoelma = new Kokoelma();

        // testataan onnistuuko tiedoston lataaminen

        if (!lataaTiedosto(tiedostonnimi, kokoelma)) {
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
                Boolean uutinen = onkoUutinen(rivi);
                // jos kyseessä on uutinen, ja käsiteltävä kokoelma on uutisia
                // lisätään kokoelmaan luoRivistaUutinen-metodilla
                if (uutinen && kokoelmanTyyppi == "uutinen") {
                    try {
                        kokoelma.lisää(luoRivistaUutinen(rivi));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error!");
                    }

                }
                // jos kyseessä on vitsi, ja käsiteltävä kokoelma on vitsi-tyyppinen
                // lisätään kokoelmaan luoRivistaVitsi-metodilla
                else if (!uutinen && kokoelmanTyyppi == "vitsi") {
                    try {
                        kokoelma.lisää(luoRivistaVitsi(rivi));
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
                hakuSanoilla(komento, kokoelma);
            }

            else if (komento.contains("remove")) {
                poistaSana(komento, kokoelma);

            }

            else if (komento.contains("polish")) {
                String[] komennonpalat = komento.split(" ");
                if (komennonpalat.length == 1 || komennonpalat.length > 2) {
                    System.out.println("Error!");
                    continue;
                }
                System.out.println(komennonpalat.length);
                siisti(komento, kokoelma, sulkusanat);
                
            }

            else if (komento.equals("reset")) {
                // Lataa dokumenttitiedoston uudelleen ja poistaa aiemmin tehdyt muutokset.
                // Jos komennolle annetaan parametrejä, tulostetaan virheilmoitus.
                kokoelma = new Kokoelma();
                lataaTiedosto(tiedostonnimi, kokoelma);
            }

            else {
                System.out.println("Error!");
            }

        }

    }// main

}// class
