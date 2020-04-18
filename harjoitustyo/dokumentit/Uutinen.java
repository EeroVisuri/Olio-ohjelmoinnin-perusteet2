/* eero.visuri@tuni.fi
 * OOPE2 - Olio-ohjelmoinnin perusteet 2
 * Harjoitustyö
 */


package harjoitustyo.dokumentit;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Uutinen extends Dokumentti {
    //kätketty attribuutti
    private LocalDate päivämäärä;
    //getteri
    public LocalDate päivämäärä() {
        return päivämäärä;
    }
    //setteri
    public void päivämäärä(LocalDate päivämäärä) {
        if (päivämäärä == null) {
            throw new IllegalArgumentException("Uutisen pvm virheellinen.");
            
        }
        this.päivämäärä = päivämäärä;
    }
    //rakentaja, virheentarkistus yliluokan rakentajassa ja setterissä.
    public Uutinen (int tunniste, LocalDate päivämäärä, String teksti) throws
        IllegalArgumentException {
        super(tunniste, teksti);
        päivämäärä(päivämäärä);
    }

 


    //korvattu toString()-metodi
    @Override
    public String toString() {
        //muutetaan päivämäärä jenkkityylistä tutumpaan muotoon
        String pvm  = päivämäärä.format(DateTimeFormatter.ofPattern("d.M.yyyy"));
        String s = super.toString();
        return s.replace("///", "///" + pvm + "///");
    }
}
