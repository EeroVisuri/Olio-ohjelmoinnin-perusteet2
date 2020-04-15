package harjoitustyo.omalista;

import java.util.LinkedList;
import harjoitustyo.*;
import harjoitustyo.apulaiset.Ooperoiva;

public class OmaLista<E> extends LinkedList<E> implements Ooperoiva<E> {

    @Override
    public void lisää(E uusi) throws IllegalArgumentException {
        super.add(uusi);

    }

}
