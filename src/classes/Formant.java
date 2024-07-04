package classes;

public class Formant {
	private double frequency, bandwidth;
	
	//Exkurs Konstanten
	public static final int RADZAHL = 2;					//Konstante: gibt eine Eigenschaft einer Klasse an (die f�r alle Objekte dann gilt) - konstant = final 
	//static: weil wir kein Objekt erzeugen wollen, sondern direkt drauf zugreifen wollen = deshalb auch public
	
	public Formant() {										//Leerer Konstruktur zum Einf�gen der Daten nach dem Einlesen in Daten_aus (Filereader); wir wissen wir haben einen neuen Formanten eingelesen, wissen aber noch nicht was drinnen steht, deshalb ist der Konstruktur unten drunter nicht zu gebrauchen weil er schon was fodert
		
	}
	
	public Formant(double frequency, double bandwidth) {	//Konstruktor, der die Werte nimmt
		this.frequency = frequency;							//von rechts nach links
		this.bandwidth = bandwidth;
	//	RADZAHL = 3;   										//einem finalen Wert kann nichts anderes zugewiesen werden
	}
	
	public double getFrequency() {							//greift auf die Werte in blau zu
		return(frequency);
	}
	
	public double getBandwidth() {							//getter um variable anzusehen/zu bekommen
		return(bandwidth);
	}
	
	public void setFrequency(double frequency) {							//setter um variable zu �berschreiben (ich geben vor welchen Wert sie hat)
		this.frequency = frequency;							//hier private nur f�r jetzt, damit er nicht st�rt - eigentlich public um Variablen die private sind zu �berschreiben
	}
	
	public void setBandwidth(double bandwidth) {			//durch die Methoden k�nnen wir auf alles zugreifen
		this.bandwidth = bandwidth;
	}
	
}
