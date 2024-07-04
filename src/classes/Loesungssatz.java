package classes;

import java.util.ArrayList;

public class Loesungssatz {
	
	private ArrayList<Trennstelle> trennstellen;		//trennstellen ist der Platz den ich im Gefrierschrank schaffe, ArrayList sind in dem Sinne die Attribute für den Platz, also wie sieht der Platz aus den ich für trennstellen schaffe (kalt wie groß etc)	
	
	public Loesungssatz() {
		this.trennstellen = new ArrayList<Trennstelle>(); // hier habe ich das Objekt Eiswürfelform new ArraList, dass ich dann in den Platz trennstellen lege, könnte auch ein objekt string bla sein, aber ich will eins Arraylist, deshalb rufe ich den Kontruktor der vorgegebenen Klasse auf
	}
	
	public void addTrennstelle(Trennstelle trennstelle_new) {
		trennstellen.add(trennstelle_new);
	}
	
	public Trennstelle getTrennstelle(int index){
		return trennstellen.get(index);		
	}
	
	public int getSize() {							//hier brauchen wir einen getter für die Länge des Arrays, bei den anderen ging das ohne getter, weil wir die Liste(bzw. das Objekt in der selben Klasse benutzt haben
		return trennstellen.size();
	}

}
