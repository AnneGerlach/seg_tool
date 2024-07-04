package classes;

import java.util.ArrayList;

public class Frame {
	private double intensity;
	private ArrayList<Formant> formants;						//formants ist eine Variable des Klassentyps Formant und ein Array
	
	
	public Frame() {
		this.formants = new ArrayList<Formant>();			//Das ist der Konstruktor , Variable formants hat jetzt Platz im Array f�r nFormants, der Platz selbst ist aber noch nicht belegt
	}														//Leerer Konstruktur, weil wir lesen die Datei durch und sehen bei frames, dass da was kommt, aber wir wissen noch nicht was, finden dann intensity und der setter setzt und die dann in daten_aus in Filereader, wir sagen auch dass das Objekt das leere �ber eine ArrayList verf�gt
	
	
	public double getIntensity() {							
		return(intensity);
	}
	
	public void setIntensity(double intensity) {							//setter um variable zu �berschreiben (ich geben vor welchen Wert sie hat)
		this.intensity = intensity;			
	}
	
	//Getter f�r die Formanten
	public Formant getFormant(int formantstelle) {						//wir bekommlen vom starter eine formantstelle (z.B. F2)
		if((formantstelle < formants.size())&&(formantstelle >= 0)) {	//s.u.
			return(formants.get(formantstelle));								//und geben die Liste des Formanten [an der Stelle] zur�ck -> die Liste kommt aus der Klasse Formant und enth�lt fr/bd
		}else {
			return(null);												//ist beim getter gefragt, weil ich nichtsbekomme und wenn es nichts gibt, muss es so zur�ckgegeben werden
		}
	}
	
	//Add f�r die Formant
	public void addFormant(Formant formant_new) {	//void weil nichts zur�ckgegeben wird
		formants.add(formant_new);					//Wichtig zum Aufbau der Struktur, f�gt die WErte in die Liste (der Getter sp�ter f�r Rechnen wichtig)
	}
	
	
	//Getter f�r Formantenanzahl
	
	public int getFormantsize() {
		return(formants.size());
	}
	
	
	
}
