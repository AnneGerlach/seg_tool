package classes;

import java.util.ArrayList;

public class Frame {
	private double intensity;
	private ArrayList<Formant> formants;						//formants ist eine Variable des Klassentyps Formant und ein Array
	
	
	public Frame() {
		this.formants = new ArrayList<Formant>();			//Das ist der Konstruktor , Variable formants hat jetzt Platz im Array für nFormants, der Platz selbst ist aber noch nicht belegt
	}														//Leerer Konstruktur, weil wir lesen die Datei durch und sehen bei frames, dass da was kommt, aber wir wissen noch nicht was, finden dann intensity und der setter setzt und die dann in daten_aus in Filereader, wir sagen auch dass das Objekt das leere über eine ArrayList verfügt
	
	
	public double getIntensity() {							
		return(intensity);
	}
	
	public void setIntensity(double intensity) {							//setter um variable zu überschreiben (ich geben vor welchen Wert sie hat)
		this.intensity = intensity;			
	}
	
	//Getter für die Formanten
	public Formant getFormant(int formantstelle) {						//wir bekommlen vom starter eine formantstelle (z.B. F2)
		if((formantstelle < formants.size())&&(formantstelle >= 0)) {	//s.u.
			return(formants.get(formantstelle));								//und geben die Liste des Formanten [an der Stelle] zurück -> die Liste kommt aus der Klasse Formant und enthält fr/bd
		}else {
			return(null);												//ist beim getter gefragt, weil ich nichtsbekomme und wenn es nichts gibt, muss es so zurückgegeben werden
		}
	}
	
	//Add für die Formant
	public void addFormant(Formant formant_new) {	//void weil nichts zurückgegeben wird
		formants.add(formant_new);					//Wichtig zum Aufbau der Struktur, fügt die WErte in die Liste (der Getter später für Rechnen wichtig)
	}
	
	
	//Getter für Formantenanzahl
	
	public int getFormantsize() {
		return(formants.size());
	}
	
	
	
}
