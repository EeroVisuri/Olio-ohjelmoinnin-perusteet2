package harjoitustyo.dokumentit;

//import java.lang.reflect.Constructor;
import java.util.LinkedList;

public class Vitsi extends Dokumentti {
    

    // attribuutti
    private String laji;

    // aksessori
    public String laji() {
        return laji;
    }

    // laji ei saa olla null tai tyhjä merkkijono
    public void laji(String laji) {
        if (laji == null || laji.length() <= 0) {
            this.laji = laji;
        }
        else {
            throw new IllegalArgumentException("Vitsin laji virheellinen");
        }
    }
    
    /*
     * Konstruktori
     */
    
    public Vitsi(int tunniste, String teksti, String laji) throws IllegalArgumentException {
        super(tunniste, teksti);
        laji(laji);
    }

    
    

    @Override
    public void siivoa(LinkedList<String> sulkusanat, String välimerkit) throws IllegalArgumentException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean sanatTäsmäävät(LinkedList<String> hakusanat) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return false;
    }

    // korvaa toString ketjuttaen superia käyttäen vissiin
    //

}