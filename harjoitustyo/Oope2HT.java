/* eero.visuri@tuni.fi
 * OOPE2 - Olio-ohjelmoinnin perusteet 2
 * Harjoitustyö
 */

package harjoitustyo;

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
          System.out.println("Program terminated.");
          return false;
        }
        
        //muussa tapauksessa palautetaan true jotta pääohjelma voi tarkistaa loput speksit
        else {
          return true;
        }
        
      }
    
    public static Boolean lataaTiedosto(String tiedostonnimi) {
        /*
         * Metodi, joka lataa parametrinaan saamaan tiedoston nimen ohjelmaan sisään.
         */
        
        Scanner tiedostonlukija = null;
        
        File tiedosto = new File(tiedostonnimi);
        
        try {
            tiedostonlukija = new Scanner(tiedosto);
        } catch (Exception e) {
            System.out.println("Missing file!");
            return false;
        }
        
        //luodaan uusi kokoelma, johon vitsejä/uutisia ruvetaan tunkemaan
        Kokoelma kokoelma = new Kokoelma();
        
        //Tarkastetaan tiedostonnimestä onko kyseessä vitsi vai uutinen
        
        //Jos kyseessä on vitsi, luetaan tiedosto rivi kerrallaan läpi, pilkotaan rivi osiin ja passataan
        //osat vitsi-luokan rakentajalle
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
        
        if (tiedostonnimi.contains("news")) {
            while (tiedostonlukija.hasNext()) {
                DateTimeFormatter pvmformaatti = DateTimeFormatter.ofPattern("d/MM/yyyy");
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
        
        
        
        //Dokumentti.lataa(tiedosto);
        tiedostonlukija.close();
        return true;
    }
    

    
    public static void main(String[] args) {
        
        
        

        
        boolean suoritetaan = true;
        boolean kaiutetaan = false;

        
        System.out.println("Welcome to L.O.T.");
        
        
        if (!tarkistaArgumentit(args)) {
            suoritetaan = false;
            return;
            
        }
        
        String tiedostonnimi = args[0];
        String sulkusanat = args[1];
        
        lataaTiedosto(tiedostonnimi);
        

        
        
        

        while (suoritetaan) {
            String komento = LUKIJA.nextLine();
            if(kaiutetaan) {
                System.out.println(komento);
              }
            System.out.println("Please, enter a command:");
            
            if (komento.equals("echo")) {
                kaiutetaan = !kaiutetaan;
            }
            else if (komento.equals("quit")) {
                System.out.println("Program terminated.");
                suoritetaan = false;
                return;
            }
            else if (komento.contains("print")) {
                //Tee printtausfunktio jossa virheentarkistus
            }
            else if (komento.contains("add")) {
                //Tee lisäystoiminto virheentarkistuksella
                
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
                lataaTiedosto(tiedostonnimi);
                
            }

            
        }
        


    }//main
}//class
