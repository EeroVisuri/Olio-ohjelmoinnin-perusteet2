package harjoitustyo.dokumentit;

import java.time.*;
import java.util.LinkedList;

public class Uutinen extends Dokumentti {
    private LocalDate päivämäärä;

    public LocalDate päivämäärä() {
        return päivämäärä;
    }

    public void päivämäärä(LocalDate päivämäärä) {
        if (päivämäärä != null) {
            this.päivämäärä = päivämäärä;
        }
        else {
            throw new IllegalArgumentException("Uutisen pvm virheellinen.");
        }
    }
    
    public Uutinen (int tunniste, LocalDate päivämäärä, String teksti) {
        super(tunniste, teksti);
        päivämäärä(päivämäärä);
    }

    @Override
    public boolean sanatTäsmäävät(LinkedList<String> hakusanat) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void siivoa(LinkedList<String> sulkusanat, String välimerkit) throws IllegalArgumentException {
        // TODO Auto-generated method stub

    }

    // @Override
    // public String toString() {
    // String original = super.toString();
    // return original.replace("///", "///" + päivämäärä + "///");
    // }
}
