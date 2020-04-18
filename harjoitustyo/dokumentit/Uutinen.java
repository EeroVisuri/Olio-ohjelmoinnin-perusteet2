/* eero.visuri@tuni.fi
 * OOPE2 - Olio-ohjelmoinnin perusteet 2
 * Harjoitustyö
 */


package harjoitustyo.dokumentit;

import java.time.*;
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
        if (päivämäärä != null) {
            this.päivämäärä = päivämäärä;
        }
        else {
            throw new IllegalArgumentException("Uutisen pvm virheellinen.");
        }
    }
    //rakentaja, virheentarkistus yliluokan rakentajassa ja setterissä.
    public Uutinen (int tunniste, LocalDate päivämäärä, String teksti) {
        super(tunniste, teksti);
        päivämäärä(päivämäärä);
    }

 


    //korvattu toString()-metodi
    @Override
    public String toString() {
        String s = super.toString();
        return s.replace("///", "///" + päivämäärä + "///");
    }
}
