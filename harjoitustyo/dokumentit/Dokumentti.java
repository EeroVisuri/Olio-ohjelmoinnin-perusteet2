package harjoitustyo.dokumentit;


import harjoitustyo.apulaiset.*;
import java.util.LinkedList;
import java.util.*;
import java.time.*;

public abstract class Dokumentti implements Comparable <Dokumentti>, Tietoinen <Dokumentti> {
	
	/*
	 * Attribuutit
	 */
	
	private int tunniste;
	
	private String teksti;
	
	/*
	 * Rakentaja
	 */
	
	public Dokumentti() {
		
	}
	
	//parametrillinen rakentaja
	public Dokumentti(int tunniste, String teksti) throws IllegalArgumentException {
		if (tunniste <= 0 || teksti == null || teksti.length() <= 0) {
			throw new IllegalArgumentException();
		}
		this.teksti = teksti;
		this.tunniste = tunniste;
	}
	
	/*
	 * Aksessorit
	 */
	
	public int tunnite() {
		return tunniste;
	}
	
	public String teksti() {
		return teksti;
	}
	
	public void tunniste(int tunniste) {
		if (tunniste > 0) {
			this.tunniste = tunniste;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	public void teksti (String teksti) {
		if (teksti != null ) {
			this.teksti = teksti; 
		}
		throw new IllegalArgumentException();
	}
	
	@Override
	public int compareTo(Dokumentti T) {
		if (tunniste == T.tunniste) {
			return 0;
		}
		else if (tunniste >  T.tunniste) {
			return 1;
		}
		else {
			return -1;
		}
	}
	

	
	
	
	public boolean sanatTäsmäävät(LinkedList<String> hakusanat, String teksti) throws IllegalArgumentException {
		
		if (hakusanat == null || hakusanat.size() <= 0) {
			throw new IllegalArgumentException();
		}
		
		boolean sanatsamat = false;
		
		for (int i = 0; i < hakusanat.size(); i++) {
			if (teksti.contains(hakusanat.get(i))) {
				sanatsamat = true;
				continue;
			}
			else {
				return sanatsamat;
			}
		}
		return sanatsamat;
	}
	
}
