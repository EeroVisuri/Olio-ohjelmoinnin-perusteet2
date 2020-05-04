/* eero.visuri@tuni.fi
 * OOPE2 - Olio-ohjelmoinnin perusteet 2
 * Harjoitustyö
 */

package harjoitustyo;

import java.util.Scanner;
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
        if(args.length < 2) {
          System.out.println("Wrong number of command-line arguments!");
          return false;

        }
        //jos parametreja on liikaa, tulostetaan virheviesti
        if (args.length > 2) {
          //jos komentoriviparametreja ei ole annettu, komentoriviparametreja on
          //enemmän kuin kaksi tai kun toinen komentori-viparametri  ei  ole echo
          //tulostetaan virheilmoitus
          System.out.println("Wrong number of command-line arguments!");
          System.out.println("Bye, see you soon.");
          return false;
        }
        
        
        //muussa tapauksessa palautetaan true jotta pääohjelma voi tarkistaa loput speksit
        else {
          return true;
        }
        
      }
    
    
    public static void main(String[] args) {

        boolean suoritetaan = true;
        boolean kaiutetaan = false;
        
        
        System.out.println("Welcome to L.O.T.");
        
        
        if (!tarkistaArgumentit(args)) {
            suoritetaan = false;
            return;
            
        }
        
        //Kokoelma.lisää(args[1]);
        
        
        

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
