/* eero.visuri@tuni.fi
 * OOPE2 - Olio-ohjelmoinnin perusteet 2
 * Harjoitustyö
 */

package harjoitustyo;

import java.util.Scanner;
import java.io.File;
import harjoitustyo.kokoelma.*;
import harjoitustyo.dokumentit.Dokumentti;


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
    
    public static Boolean lataaTiedosto(String tiedostonNimi) {
        /*
         * Metodi, joka lataa parametrinaan saamaan tiedoston nimen ohjelmaan sisään.
         */
        
        Scanner tiedostonlukija = null;
        
        File tiedosto = new File(tiedostonNimi);
        
        try {
            tiedostonlukija = new Scanner(tiedosto);
        } catch (Exception e) {
            System.out.println("Missing file!");
            return false;
        }
        
        //to-do käy annettu filu läpi ja lisää oikeantyyppiseen kokoelmaan rivit sieltä
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
        
        String tiedostonNimi = args[0];
        String sulkusanat = args[1];
        
        lataaTiedosto(tiedostonNimi);
        

        
        
        

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
            else if (komento.contains("reset")) {
                //Lataa dokumenttitiedoston uudelleen ja poistaa aiemmin tehdyt muutokset.
                //Jos komennolle annetaan parametrejä, tulostetaan virheilmoitus.
            }

            
        }
        


    }//main
}//class
