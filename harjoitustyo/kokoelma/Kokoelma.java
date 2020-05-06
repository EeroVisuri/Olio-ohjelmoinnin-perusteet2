/* eero.visuri@tuni.fi
 * OOPE2 - Olio-ohjelmoinnin perusteet 2
 * Harjoitustyö
 */

package harjoitustyo.kokoelma;

import harjoitustyo.apulaiset.*;
import harjoitustyo.dokumentit.*;
import harjoitustyo.omalista.*;

public class Kokoelma implements Kokoava<Dokumentti> {

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
