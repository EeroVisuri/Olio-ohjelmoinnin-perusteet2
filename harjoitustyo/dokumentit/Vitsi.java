/* eero.visuri@tuni.fi
 * OOPE2 - Olio-ohjelmoinnin perusteet 2
 * Harjoitustyö
 */

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

    // laji ei saa olla null tai tyhjä merkkijono, jos on, heitetään error.
    public void laji(String laji) {
        if (laji == null || laji.length() <= 0) {
            throw new IllegalArgumentException("Vitsin laji virheellinen");
        }
            this.laji = laji;  
    }
    
    
    /*
     * Rakentaja, joka kutsuu yläluokan rakentajaa ja Vitsi-luokan setteriä. Molemmissa 
     * virheentarkistus isessään.
     */
        
    public Vitsi(int tunniste, String laji, String teksti) throws IllegalArgumentException {
        super(tunniste, teksti);
        laji(laji);
    }
    
    
    //korvattu toString()-metodi.
    @Override
    public String toString() {
        String s = super.toString();
        return s.replace("///", "///" + laji + "///");
    }

}