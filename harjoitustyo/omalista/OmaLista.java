/* eero.visuri@tuni.fi
 * OOPE2 - Olio-ohjelmoinnin perusteet 2
 * Harjoitustyö
 */

package harjoitustyo.omalista;

import java.util.LinkedList;
import harjoitustyo.apulaiset.Ooperoiva;
import harjoitustyo.dokumentit.Dokumentti;

public class OmaLista<E> extends LinkedList<E> implements Ooperoiva<E> {

    // Hiljennetään
    @SuppressWarnings("unchecked")

    /*
     * Lisätään oliot listaan compareTo-metodilla vertaillen, jos paluuarvo on 0,
     * vertailussa on olleet samat oliot, jolloin jatketaan. Jos paluuarvo on < 0,
     * lisätään alkio indeksiin i.
     */

    @Override
    public void lisää(E uusi) throws IllegalArgumentException {
        // jos alkio on null tai sillä ei ole Comparable-rajapintaa toteutettu,
        // heitetään error.
        if (uusi == null || !(uusi instanceof Comparable<?>)) {
            throw new IllegalArgumentException("OmaListan alkio null tai ei comparable");
        }

        /*
         * compareTo-metodi toiminnallisuus = jos tunnisteet ovat samat, se palauttaa 0,
         * jos tunniste on > T.tunniste, se palauttaa 1, muuten -1.
         */

        // for-silmukka jossa käydään läpi lista
        for (int i = 0; i < this.size(); i++) {
            Comparable nykyinen = (Comparable) get(i);

            if (nykyinen.compareTo(uusi) > 0) {
                add(i, uusi);
                return;
            } else if (i == this.size() - 1) {
                add(uusi);
                return;
            }

        }

        // jos lista on tyhjä, pusketaan sinne uusi-olio.
        if (this.size() == 0) {
            this.push(uusi);
        }

    }

}
