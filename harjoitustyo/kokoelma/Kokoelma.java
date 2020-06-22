/* eero.visuri@tuni.fi
 * OOPE2 - Olio-ohjelmoinnin perusteet 2
 * Harjoitustyö
 */

package harjoitustyo.kokoelma;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

import harjoitustyo.apulaiset.*;
import harjoitustyo.dokumentit.*;
import harjoitustyo.omalista.*;

public class Kokoelma implements Kokoava<Dokumentti> {
    
    // muuttuja johon tallennetaan käsiteltävän kokoelman tyyppi, jottei lisätä
    // vitsejä uutisiiin.
    public static String kokoelmanTyyppi = new String();

    /*
     * Kätketty OmaLista<Dokumentti>-tyyppinen attribuutti
     */

    private OmaLista<Dokumentti> dokumentit;

    // getteri

    public OmaLista<Dokumentti> dokumentit() {
        return dokumentit;
    }

    // setteri

    public void dokumentit(OmaLista<Dokumentti> dokumentit) {
        this.dokumentit = dokumentit;
    }

    public Kokoelma() {
        dokumentit = new OmaLista<Dokumentti>();
    }

    /*
     * Lisää kokoelmaan uuden dokumentin. Kutsuu OmaListan lisää-metodia. Heittää
     * virheen, jos lisättä dokumentti on null tai sitä ei voi vertailla
     * Comparable-rajapinnan kautta.
     */

    
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
        if (komennonpalat.length != 2) {
            System.out.println("Error!");
            return;
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
            return;
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
        
        
        //kaivetaan komennosta siivottavat merkit merkkijonoksi talteen
        String siivottavat = komento.substring(7);
        
        
        try {
            //luodaan filu
            File sulkufilu = new File (sulkusanat);
            sulkusanalukija = new Scanner(sulkufilu);
            //luodaan LinkedList
            LinkedList<String> sulkusanaLista = new LinkedList<>();
            //luetaan tiedostosta rivit sulkusanalistaan.
            while (sulkusanalukija.hasNext()) {
                String rivi = sulkusanalukija.nextLine();
                sulkusanaLista.add(rivi);
            }
            //Siivotaan kokoelmasta dokumentti-luokan siivoa-metodilla sulkusanaListalla olevat
            //sanat ja parametrina saadut siivottavat pois.
            for (Dokumentti dokumentti : kokoelma.dokumentit()) {
                dokumentti.siivoa(sulkusanaLista, siivottavat);
            }
            
        } catch (Exception e) {
            System.out.println("Error! polishin sisältä");
        }
    }
    
    
    
    @Override
    public void lisää(Dokumentti uusi) throws IllegalArgumentException {
        if (uusi == null || !(uusi instanceof Comparable<?>)) {
            throw new IllegalArgumentException("Kokoelman dokumentti virhellinen!");
        }
        for (int i = 0; i < dokumentit.size(); i++) {
            if (uusi.equals(dokumentit.get(i))) {
                throw new IllegalArgumentException("Dokumentti löytyy jo kokoelmasta!");
            }
        }
        dokumentit.lisää(uusi);
    }

    /*
     * Hakee kokoelmasta dokumentin, jolla on sama tunniste kuin metodin parametrina
     * saatu kokonaisluku. Jos dokumenttia ei löydy, paluuarvo on null.
     */

    @Override
    public Dokumentti hae(int tunniste) {
        for (int i = 0; i < dokumentit.size(); i++) {
            Dokumentti dokumentti = dokumentit.get(i);
            String[] dokumenttistring = dokumentti.toString().split("///");
            int tunnari = Integer.parseInt(dokumenttistring[0]);
            if (tunnari == tunniste) {
                return dokumentti;
            }
        }
        return null;
    }
}
