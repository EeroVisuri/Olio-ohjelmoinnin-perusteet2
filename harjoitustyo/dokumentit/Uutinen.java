package harjoitustyo.dokumentit;
import java.time.*;
import java.util.LinkedList;


public class Uutinen extends Dokumentti {
	private LocalDate päivämäärä;
	
	public LocalDate päivämäärä() {
		return päivämäärä;
	}
	
	public void päivämäärä(LocalDate uusipäivämäärä) {
		if (uusipäivämäärä != null) {
			päivämäärä = uusipäivämäärä;
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
	
	
	//@Override
	//public String toString() {
	//String original = super.toString();
	//return original.replace("///", "///" + päivämäärä + "///");
	//}
}
