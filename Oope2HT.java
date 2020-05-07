/* eero.visuri@tuni.fi
 * OOPE2 - Olio-ohjelmoinnin perusteet 2
 * Harjoitustyö
 */

package harjoitustyo;
import java.io.*; 
import java.util.LinkedList; 
import java.util.Scanner;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import harjoitustyo.kokoelma.*;
import harjoitustyo.dokumentit.Dokumentti;
import harjoitustyo.dokumentit.Uutinen;
import harjoitustyo.dokumentit.Vitsi;


public class Oope2HT {

    // importataan lukija
    public static final Scanner LUKIJA = new Scanner(System.in);

    public static Boolean tarkistaArgumentit(String[] args) {
        /*Tämä metodi tarkastaa saamansa käynnistysparametrit ja palauttaa true, jos ei löydä virhettä
         *ja false jos löytää niistä virheen.
         *
         */

        //jos parametreja on alle kaksi, tulostetaan virheviesti
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
         * Metodi, joka lataa parametrinaan saamaan tiedoston nimen ohjelmaan sisään. Metodi
         * palauttaa true onnistumisesta, ja false jos jotain meni pieleen.
         */
        
        Scanner tiedostonlukija = null;

        
        File tiedosto = new File(tiedostonnimi);

        
        try {
            tiedostonlukija = new Scanner(tiedosto);

        } catch (Exception e) {
            System.out.println("Missing file!");
            return false;
        }
        
        
        
        

        //Tarkastetaan tiedostonnimestä onko kyseessä vitsi vai uutinen, jos ei kumpikaan, 
        //palautetaan false.
        if (!tiedostonnimi.contains("jokes") && !tiedostonnimi.contains("news")) {
            tiedostonlukija.close();
            return false;
        }

        //Jos kyseessä on vitsi, luetaan tiedosto rivi kerrallaan läpi, pilkotaan rivi osiin 
        //ja passataan osat vitsi-luokan rakentajalle
        
        if (tiedostonnimi.contains("jokes")) {
            while (tiedostonlukija.hasNext()) {
                String rivi = (tiedostonlukija.nextLine());
                String[] vitsinpalat = rivi.split("///");
                int vitsintunniste = Integer.parseInt(vitsinpalat[0]);
                String vitsinlaji = vitsinpalat[1];
                String itsevitsi = vitsinpalat[2];
                Vitsi uusiVitsi = new Vitsi(vitsintunniste, vitsinlaji, itsevitsi);
                kokoelma.lisää(uusiVitsi);
            }
            
        }
        
        //Jos kyseesä uutinen, suoritetaan samat toiminnot kuin vitsin kanssa, paitsi konvertoidaan
        //uutisessa esiintyvä päivämäärä muotoon LocalDate.
        if (tiedostonnimi.contains("news")) {
            while (tiedostonlukija.hasNext()) {
                DateTimeFormatter pvmformaatti = DateTimeFormatter.ofPattern("d.M.yyyy");
                String rivi = (tiedostonlukija.nextLine());
                String[] uutisenpalat = rivi.split("///");
                int uutisentunniste = Integer.parseInt(uutisenpalat[0]);
                String uutisenpvmmerkit = uutisenpalat[1];
                LocalDate uutisenpvm = LocalDate.parse(uutisenpvmmerkit, pvmformaatti);
                String itseuutinen = uutisenpalat[2];
                Uutinen uusiUutinen = new Uutinen(uutisentunniste, uutisenpvm, itseuutinen);
                kokoelma.lisää(uusiUutinen);
            }
        }
        
        //suljetaan lukija ja palautetaan true jos kaikki onnistui odotetusti
        tiedostonlukija.close();
        return true;
    }
    
    public static void tulosta(String komento, Kokoelma kokoelma) {
        
        /*
         * Tämä metodi saa parametrinaan komennon (joka on print tai print ja kokonaisluku)
         * ja tulostettavan kokoelman. Metodi tulostaa joko koko kokoelman jos parametri oli
         * pelkkä print, ja tunnisteen perusteella tietyn dokumentin jos toinen parametri
         * oli kokonaisluku joka vastaa kokoelmasta löytyvää tunnistetta.
         */
        
        //pilkotaan komento paloihin
        String[] komennonpalat = komento.split(" ");
        //jos kokoelma on tyhjä, heitetään virheviesti      
        if (kokoelma == null) {
            System.out.println("Error!");
            return; 
        }
        
        //jos komentona on pelkkä "print" tulostetaan koko kokoelma ulos
        if (komennonpalat.length == 1 && komento.equals("print")) {
            for (int i = 0; i < kokoelma.dokumentit().size(); i++) {
                System.out.println(kokoelma.dokumentit().get(i));
            }
            return;
        }

        //jos komennossa on kaksi osaa, toinen osista on haettavan dokumentin tunniste
        //try-catch jossa käsitellään virhe, jos toinen osa komentoa ei ole kokonaisluku
        //ja käsitellään Out of Bounds-virhe.
        try {
            if (komennonpalat.length == 2) {
                int tulostettavatunniste = Integer.parseInt(komennonpalat[1]);
                if (kokoelma.hae(tulostettavatunniste) != null) {
                    System.out.println(kokoelma.hae(tulostettavatunniste));
                }
                else {
                    System.out.println("Error!");
                }
                        
            }
            else {
                System.out.println("Error!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error!");
        }
        
    }
    
    public static void lisaakokoelmaan(String komento, String tiedostonnimi, Kokoelma kokoelma) {
        /*
         * Tämä metod saa parametreinaan kaksiosaisen komennon, jossa ensimmäinen osa on "add" ja
         * toinen osa on lisättävä dokumentti merkkijonona, jonka muoto on sama kuin tiedostossa.
         * Lisäys epäonnistuu jos dokumentti on jo dokumentti samalla tunnisteella, tai dokumenttia
         * yritetään lisätä väärään kategoriaan. Lisääminen kosahtaa myös jos parametrit ovat 
         * virheelliset(param > 1) tai niitä ei ole annettu.
         * 
         */
        String[] komennonpalat = komento.split(" ");
        if (!komennonpalat[0].equals("add") || komento.length() > 2) {
            
        } 
        
    }
        
        
        
        
    public static void main(String[] args) {
        
        
        
        
        //lippumuuttujat ohjelman suorittamiselle ja komentojen kaiuttamiselle
        boolean suoritetaan = true;
        boolean kaiutetaan = false;

        //tulostetaan tervehdys
        System.out.println("Welcome to L.O.T.");
        
        //tarkastetaan komentorivin käynnistysparametrit
        if (!tarkistaArgumentit(args)) {
            System.out.println("Program terminated.");
            suoritetaan = false;
            System.exit(0);
            
        }
        //
        String tiedostonnimi = args[0];
        String sulkusanat = args[1];
        
        //luodaan uusi kokoelma, johon vitsejä/uutisia ruvetaan tunkemaan
        Kokoelma kokoelma = new Kokoelma();
        
        //testataan onnistuuko tiedoston lataaminen 
        
        if (!lataaTiedosto(tiedostonnimi, kokoelma)) {
            System.out.println("Program terminated.");
            suoritetaan = false;
        }
        
        

        
        
        
        //pääasiallinen käyttöliittymän while-looppi
        while (suoritetaan) {
            
            //kysytään komento
            
            System.out.println("Please, enter a command:");
            
            //sijoitetaan komento Stringiin
        
            String komento = LUKIJA.nextLine();

            
            

            //Komentojen kaiutuksen lippumuuttujan vaihtaminen jos komentona on "echo".
            if (komento.equals("echo")) {
                kaiutetaan = !kaiutetaan;
                if (kaiutetaan == true) {
                    System.out.println("echo");
                }
                continue;
            }
            //komentojen kaiutus
            if(kaiutetaan) {
                System.out.println(komento);  
            }
            
            //jos komento on "quit", suljetaan ohjelma
            if (komento.equals("quit")) {
                System.out.println("Program terminated.");
                suoritetaan = false;
                System.exit(0);
            }
            //jos komennossa on "print", annetaan se tulosta-metodille. Virheentarkistus metodissa.
            else if (komento.contains("print")) {
                tulosta(komento, kokoelma);
            }
            //jos komennossa on "add", annetaan se lisää-metodille. Virheentarkistus metodissa.
            else if (komento.contains("add")) {
                lisaakokoelmaan(komento, tiedostonnimi, kokoelma);
                
            }
            
            else if (komento.contains("find")) {
                //Tee hakutoiminto virheentarkistuksella
            }
            
            else if (komento.contains("remove")) {
                //Tee dokkarin poistotoiminto virheentarkistuksella
                
            }
            
            else if (komento.contains("polish")) {
                //Tee esikäsittelytoiminto ja parametrien virheentarkistus
            }
            
            else if (komento.equals("reset")) {
                //Lataa dokumenttitiedoston uudelleen ja poistaa aiemmin tehdyt muutokset.
                //Jos komennolle annetaan parametrejä, tulostetaan virheilmoitus.
                lataaTiedosto(tiedostonnimi, kokoelma);   
            }
            
            else {
                System.out.println("Error!");
            }


            
        }
        


    }//main
}//class
