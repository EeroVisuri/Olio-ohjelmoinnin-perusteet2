package harjoitustyo;

/* eero.visuri@tuni.fi
 * OOPE2 - Olio-ohjelmoinnin perusteet 2
 * Harjoitustyö
 */


import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.ParseException;

import harjoitustyo.kokoelma.*;
import harjoitustyo.dokumentit.Dokumentti;
import harjoitustyo.dokumentit.Uutinen;
import harjoitustyo.dokumentit.Vitsi;

/**
 * 
 * Ajoluokka, tarkastaa käynnistysargumentit ja ajelee käyttöliittymää.
 * Ainoa metodi on tarkistaArgumentit.
 *
 */


public class Oope2HT {

    /**
     * Tämä metodi tarkastaa saamansa käynnistysparametrit ja palauttaa true, jos ei
     * löydä virhettä ja false jos löytää niistä virheen.
     * @param String[] args ottaa käynnistysparametrit
     * @return boolean true jos onnistui, false jos löytyi virhe.
     * 
     */
    public static Boolean tarkistaArgumentit(String[] args) {


        // jos parametreja on alle kaksi, tulostetaan virheviesti
        if (args.length != 2) {
            System.out.println("Wrong number of command-line arguments!");
            return false;
        }

        else if (args.length == 2) {
            String tiedostonnimi = args[0];
            String sulkusanatiedosto = args[1];
            File tmptiedosto = new File(tiedostonnimi);
            File tmpsulkusanat = new File(sulkusanatiedosto);
            boolean filuon = tmpsulkusanat.exists() && tmptiedosto.exists();
            if (filuon) {
                return true;
            }
        }
        System.out.println("Missing file!");
        return false;
    }
    
    /**
     * 
     * @param args ottaa käynnistysriviparametrit
     * 
     * main tarkistaa käynnistysparametrit ja ajaa käyttöliittymän.
     */
    

    public static void main(String[] args) {
        // tarkastetaan komentorivin käynnistysparametrit
        if (!tarkistaArgumentit(args)) {
            System.out.println("Program terminated.");
            System.exit(0);
            
        }
        //
        String tiedostonnimi = args[0];
        String sulkusanat = args[1];
        UI liittyma = new UI();
        liittyma.aloita(tiedostonnimi, sulkusanat);


    }// main

}// class
