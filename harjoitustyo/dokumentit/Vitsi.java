package harjoitustyo.dokumentit;



import java.lang.reflect.Constructor;
import java.util.LinkedList;

public class Vitsi extends Dokumentti {
	private String laji;
	
	public String laji() {
		return laji;
	}
	
	public void laji (String uusilaji) {
		if (uusilaji != null || uusilaji.length() <= 0) {
			laji = uusilaji;
		}
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
	
	
	
	//korvaa toString ketjuttaen superia käyttäen vissiin
	//
	
}