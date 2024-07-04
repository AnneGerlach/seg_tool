package segmentation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import classes.Datensatz;
import classes.Formant;
import classes.Frame;
import classes.Loesungssatz;
import classes.Trennstelle;

//KLASSE ANALYZER
public class Analyzer {
	
	
	private static final int INDEX_FREQBAND = 0;
	private static final int INDEX_INTENSITY = 1;
	private static final int INDEX_FORMANT1_FREQ = 2;
	private static final int INDEX_FORMANT2_FREQ = 3;
	private static final int INDEX_FORMANT3_FREQ = 4;
	private static final int INDEX_FORMANT4_FREQ = 5;
	private static final int INDEX_FORMANT5_FREQ = 6;
	private static final int INDEX_FORMANT1_BAND = 7;
	private static final int INDEX_FORMANT2_BAND = 8;
	private static final int INDEX_FORMANT3_BAND = 9;
	private static final int INDEX_FORMANT4_BAND = 10;
	private static final int INDEX_FORMANT5_BAND = 11;
	private static final int INDEX_KONNR = 12;

	//DATEI EINLESEN
	//statische Methode; hier muss kein Objekt erzeugt werden
	
	public static Datensatz readfile(File file_new) {						//file_new verweist auf die Datei wo die Daten drinnen stehen
		if((file_new.exists())&&(file_new.getName().endsWith(".txt"))) {  //Überprüfung ob Datei existiert und überprüft den namen und überprüft ob txt m ende steht
			
			//Reader x = new FileReader(file_new); 							//erst Reader importieren, dann filereader (hier will er nicht nur ein Objekt der Klasse Reader sondern untergeordnete Klasse FileReader ist spezifischer
			
			ArrayList<String> content = new ArrayList<String>();			//Wir erzeugen dann eine Arraylist in die wir Strings packen können (das gesamte Dokument erst einlesen)
			
			try {
				
				BufferedReader buff_in = new BufferedReader(new FileReader(file_new)); //Erzeugung von Buffered Reader erfordert definierteren Reader 				//Wir nehmen die Klasse Buffered Reader von Java, um die Datei die wir jetzt haben auszulesen, dafür brauchen wir erstmal ein Objekt der Klasse
				
				String n_Zeile = "";			//String auf leeren Wert setzen
				
				n_Zeile = buff_in.readLine();		//lies die erste Zeile und schreibe sie in n_Zeile rein
				
				while(n_Zeile != null) {
					
					if(!n_Zeile.equals("")) {				//bei string geht kein == deshalb equals, hier wird überprüft ob ein leerer String/Zeile vorkommt; ! um zu negieren
						content.add(n_Zeile.replace(" ", "")); 	//alle Leerzeichen werden zusätzlich rausgenommen
					}
					n_Zeile = buff_in.readLine();			// sobald man readline aufruft, springt er eine Zeile weiter
				}
				
				buff_in.close();
				
			}catch(Exception e) {			
				return(null);
			}
			
//DATEI AUFBEREITEN
			Datensatz daten_aus = new Datensatz();		//Klasse Objekt = new Konstruktor der Klasse();
			String zeile_temp = "";
			Frame frame_temp = null;					//auf null setzen, weil noch nicht direkt initalisiert (weil erst wird wie unten case intensity reingeschrieben) danach erst wird in dem sinne instanziiert und Platz geschaffen für die Arraylist (unter Konstruktur Frame) und intensity steht schon drinnen
			Formant formant_temp = null;
			
			for(int i = 0; i < content.size(); i++) {
				zeile_temp = content.get(i); 				//aktuelle Zeile die wir uns ansehen
				
				String[] zeile_split = zeile_temp.split("=");  		//array für split
				
				if (zeile_split.length > 1) {						//wenn zwei Teile in zeile temp stehen (also pre und suff)
					
					String prefix = zeile_temp.split("=")[0]; 			// wir deklarieren einen String (prefix), der mit der Methode Split auf unsere aktuelle Zeile temp zugreift und diese an der Stelle = (wenn ein Gleichzeichen vorhanden) aufsplittet und beide Teile (also der erste vor dem Gleichzeichen der zweite danach) in ein Array schreibt. In den eckigen Klammern steht dann das Array mit den beiden Teilen und wir rufen hier den Teil an stelle 0 (also den ersten) auf
					String suffix = zeile_temp.split("=")[1];
					
					switch(prefix) {
					case "xmin":												
						daten_aus.setXmin(Double.parseDouble(suffix));	//parsedouble nimmt string und baut daraus ein Objekt vom Typ double
						break;
					case "xmax":												
						daten_aus.setXmax(Double.parseDouble(suffix));	//parsedouble nimmt string und baut daraus ein Objekt vom Typ double
						break;
					case "nx":												
						daten_aus.setNx(Integer.parseInt(suffix));	//parsedouble nimmt string und baut daraus ein Objekt vom Typ double
						break;
					case "dx":												
						daten_aus.setDx(Double.parseDouble(suffix));	//parsedouble nimmt string und baut daraus ein Objekt vom Typ double
						break;
					case "x1":												
						daten_aus.setX1(Double.parseDouble(suffix));	//parsedouble nimmt string und baut daraus ein Objekt vom Typ double
						break;
					case "intensity":
						frame_temp.setIntensity(Double.parseDouble(zeile_temp.replace("intensity=", ""))); //von innen nach außen: ich ersetze im Dok intensity = durch nichts, es bleibt nur der intensity wert. Der wird druch parse dobule von einem string zu einem double gewandelt und in frame_temp gesettet
						break;											//wichtig, dass er nicht einfach weiter läuft
					case "frequency":
						formant_temp.setFrequency(Double.parseDouble(zeile_temp.replace("frequency=", ""))); //zeile_temp.replace("frequency=", "") ist das gleiche wie (suffix) bzw. bezieht sich auf den gleichen wert an der gleichen stelle
						break;
					case "bandwidth":
						formant_temp.setBandwidth(Double.parseDouble(suffix));
						break;
					} 
					
				}else {
					if((zeile_temp.startsWith("frames"))&&(zeile_temp.length() > 9)) {	//im Quellcode steht erst intensity, aber das Programm stößt erst auf frames, und eshalb wird frames schon instanziiert bevor intensity eingelesen wird. dh wenn intensity auftaucht im Dokument, gibt es das Objekt bereits, in welches intensity geschrieben werden kann
						
						if (formant_temp != null) {					//hierdurch wird der letzte Formant eines Frames noch in Frame geschrieben, bevor wieder mit Frame gestartet wird (wichtig auch, dass formant_temp auf null gesetzt wird, damit er nicht doppelt eingetragen wird
							frame_temp.addFormant(formant_temp);
							formant_temp = null;
						} 
						
						if(frame_temp != null) {
							daten_aus.addFrame(frame_temp);
						}
						
						frame_temp = new Frame();
						
					}else if((zeile_temp.startsWith("formant"))&&(zeile_temp.length() > 10)){ //wenn mit formants beginnt und mehr als 10 Zeichen hat (schließt formants[] ohne Zahl aus
						
						if(formant_temp != null) {
							frame_temp.addFormant(formant_temp);
						}
						
						formant_temp = new Formant();
						
					}
				}
			} 
			
			if (formant_temp != null) {					//die letzten beiden ifs sind dafür, wenn das Programm am Ende angekommen ist und bei kein neues Wort "frame" mehr findet. dann hat er den letzten Block gelesen, aber nicht mehr in die Objekte geschoben. Deshalb noch die letzten werte nach der for schleife einordnen mit if bed.
				frame_temp.addFormant(formant_temp);
			} 
			
			if (frame_temp != null) {
				daten_aus.addFrame(frame_temp);
			}
			
			
			

				
			
			
			
			
		/*	//Zählschleife ansehen bei StringMethoden(starts with und indexof (...)
				for Schleife: Zeile für Zeile aus der Array list auslesen
				sagen: Arraylist wir wollen einen Eintrag an der Zählvariablen (an dieser Stelle ) haben 
				for schleife kommt hinter die while schleife
				länge der Liste ermitteln, vllt durch length array list? und das dann festlegen in der forschleife
				9512 - aber bei jedem Dokument ist das unterschiedlich!
				
				for (i=1, i >=9512, i++)
				
					getString (x)
					startswith frequency
					endswith blalba
				
			//getter einer Array list
				Array list sagen: gib mir das 5. Element: er heißt nur get und dan eine Nummer
				und ich soll die Methoden start with und index of deklarieren
				starts with und index of vom typ string 
				string deklarieren: zb String name = Anne
				un dann name. (dann schlägt der schon starts with und endswith vor)
				java.docs angucken dazu 
				wisen, was übergebe ich in der Klammer und was bekomme ich zurück
				Dokumentation dazu ankommen
				ich möchte eine Frame nummer, mit den Formanten + intensity, mit den WErten Bandbreite und Frequenz
			
			
			public static Datensatz processfile() {
				
				ArrayList<String> content = new ArrayList<String>();
				
				while(n_Zeile.equals(""))
				
	
					public ArrayList<String> getStringList() {
					  return stringList;
					  }
				
					public ArrayList<String> getString() {
					  return new ArrayList<String>(string);
						}
					}
	
						
			}
			
			
			
		  	
			*/

			
			
			/*
			Frame myFrame;
		    myFrame = new Frame(333.3201432, 5);						//new ruft hier Frame aus (der Konstruktor ist gemeint) und der verlangt intensity und nFormants
		    															//wenn ich 5 habe- brauche ich mind. 5
		    
		    Formant myFormant1;
		   
		    myFormant1 = new Formant(323, 2323);
		    
		    Formant myFormant2 = new Formant(232, 1421);				//weitere Formanten die ins Frame geschrieben werden
		    Formant myFormant3 = new Formant(243, 1441);
		    Formant myFormant4 = new Formant(277, 1451);
		    Formant myFormant5 = new Formant(276, 1481);
		    
		   
		    myFrame.setFormant(0, myFormant1);							//formant_new = der Formant den wir neu haben also hier my_Formant1
		    myFrame.setFormant(1, myFormant2);							// wir fügen sozusagen den Formanten dessen Bandr/Fr. wir eingetragen haben dem Frame hinzu
		    myFrame.setFormant(2, myFormant3);							//myFormant3 an die STelle 2(bzw. 3) setzen
		    myFrame.setFormant(3, myFormant4);
		    myFrame.setFormant(4, myFormant5);
		    
		    if(myFrame.getFormant(2) != null) {								// if != null bedeutet, wenn kein Wert im Formanten vorkommt also null, wird kein error erscheinen durch syso, sondern nichts oder die Zahl (wir umgehen den error der das Programm beendet)
		    	System.out.println(myFrame.getFormant(2).getBandwidth()); 	//hier greife ich auf das Frame zu und durch den getter muss ich nicht jedes Objekt einzeln aufrufen
		    }															//ich greife auf myFrame zu(möchte darüber was wissen) - diese verfügt über den getter getFormant(nFormants), ich sage ich will den zweiten haben und gehe damit in die Klasse Formant mit den gettern und settern. hier kann ich mir dann mit dem getter die bandbreite und frequenz ausgeben lassen 
		    														//ich bekomme nicht Formant 2 ausgegeben, sondern den Formanten an stelle 2 = also Formant3!!!!
		
		    
		   
		    
		    daten_aus = new Datensatz(2, 22, 33, 11, 33);     // hier wird ein Objekt erzeugt was Infos für die Klasse Datensatz enthält
		    
		    daten_aus.addFrame(myFrame);								//hier wird myFrame in die ArrayListe des Objektes d im Datzensatz klasse geschrieben
		    daten_aus.addFrame(myFrame);								//myFrame enthält intensity und 5 Formanten, die wiederum freuqnz und bandbreite enthalten
		    daten_aus.addFrame(myFrame);
		    daten_aus.addFrame(myFrame);
		    daten_aus.addFrame(myFrame);
		    daten_aus.addFrame(myFrame);
		    daten_aus.addFrame(myFrame);
			
			*/
			
			return(daten_aus);
		}else{
			return(null);		
		}
	}
	
//DATEN ANALYSIEREN
	
	public static Loesungssatz analyze (Datensatz daten_ein) {
		
		
		Loesungssatz loesungs_aus = new Loesungssatz();
		
		ArrayList<Double[]> liste_diff = new ArrayList<Double[]>(); 	// Arraylist mit Arrays
		
		
		Double[] werte_past = new Double[INDEX_KONNR];
		
	
		Double[] daten_max = new Double[INDEX_KONNR];
		
		for(int i = 0; i < daten_max.length; i++) {
			
			werte_past[i] = null;
			
			daten_max[i] = 0.0;
		}
		
	
		
		for(int i = 0; i < daten_ein.getFramesize(); i++) {
			
			
			Frame frame_akt = daten_ein.getFrame(i);
			
			//
			Double[] kennwerte = new Double[INDEX_KONNR];	//in das Array kommen die vorrechnungen zu den einzelnen Index, heißt: die Intensity werte, die gewichteten Frequenzwerte etc.. Erst im nächsten SChritt werden die Differenzen berechnet
			
			//FREQBAND_NEU BERECHNEN
			double [] bands_akt = new double [frame_akt.getFormantsize()]; //Array so lang, wie es Formanten gibt
			double [] freqs_akt = new double [frame_akt.getFormantsize()];
			double band_summe = 0;	
			for(int j = 0; j < bands_akt.length; j++) {			//alternativ auch freqsakt möglich, geht nur um die länge des arrays und man muss nicht wie oben get Formantsize aufwendig schreiben	
				bands_akt[j] = frame_akt.getFormant(j).getBandwidth();
				freqs_akt[j] = frame_akt.getFormant(j).getFrequency();
				band_summe = band_summe + bands_akt[j];	
			}
			double freqband_neu = 0;
			for(int j = 0; j < bands_akt.length; j++) {
				freqband_neu += freqs_akt[j] * (band_summe - bands_akt[j]); //das += macht, dass jedesmal der ursprünglichen wert addiert wird	
			}
			
			//1. Schritt: Alle Werte besorgen (FREQBAND, INTENSITY und FORMANTEN)
			kennwerte[INDEX_FREQBAND] = freqband_neu / (2* band_summe);
			
			kennwerte[INDEX_INTENSITY] = frame_akt.getIntensity();
			
			
			for(int z = INDEX_FORMANT1_FREQ; z <= INDEX_FORMANT5_BAND; z++) {
				
				kennwerte[z] = null;
				
			}
			/*
			kennwerte[INDEX_FORMANT5_FREQ] = null;
			kennwerte[INDEX_FORMANT4_FREQ] = null;
			kennwerte[INDEX_FORMANT3_FREQ] = null;
			kennwerte[INDEX_FORMANT2_FREQ] = null;
			kennwerte[INDEX_FORMANT1_FREQ] = null;
			kennwerte[INDEX_FORMANT5_BAND] = null;
			kennwerte[INDEX_FORMANT4_BAND] = null;
			kennwerte[INDEX_FORMANT3_BAND] = null;
			kennwerte[INDEX_FORMANT2_BAND] = null;
			kennwerte[INDEX_FORMANT1_BAND] = null;*/
			
			switch(frame_akt.getFormantsize()){
		
			case 5: 
				kennwerte[INDEX_FORMANT5_FREQ] = frame_akt.getFormant(4).getFrequency();
				kennwerte[INDEX_FORMANT5_BAND] = frame_akt.getFormant(4).getBandwidth();
			case 4:
				kennwerte[INDEX_FORMANT4_FREQ] = frame_akt.getFormant(3).getFrequency();
				kennwerte[INDEX_FORMANT4_BAND] = frame_akt.getFormant(3).getBandwidth();
			case 3:
				kennwerte[INDEX_FORMANT3_FREQ] = frame_akt.getFormant(2).getFrequency();
				kennwerte[INDEX_FORMANT3_BAND] = frame_akt.getFormant(2).getBandwidth();
			case 2:
				kennwerte[INDEX_FORMANT2_FREQ] = frame_akt.getFormant(1).getFrequency();
				kennwerte[INDEX_FORMANT2_BAND] = frame_akt.getFormant(1).getBandwidth();
			case 1: 
				kennwerte[INDEX_FORMANT1_FREQ] = frame_akt.getFormant(0).getFrequency();	//Bandbreite später
				kennwerte[INDEX_FORMANT1_BAND] = frame_akt.getFormant(0).getBandwidth();
				break;

			}
			
			
			//2. Schritt: DIFFERENZEN BERECHNEN 
			

			
			Double[] werte_diff = new Double[INDEX_KONNR];
			
			for(int j = 0; j < werte_diff.length; j++) {			//Im Array werden 7 Plätze freigehalten und auf 0.0 gesetzt
						
				if((werte_past[j] != null) && (kennwerte[j] != null)) {
					
					werte_diff[j] = Math.abs(kennwerte[j] - werte_past[j]);
					
					daten_max[j] = Math.max(werte_diff[j], daten_max[j]);
					
					
				}else {
					werte_diff[j] = 0.0;
				}
			}
			
			werte_past = kennwerte;
			
			liste_diff.add(werte_diff);
			
			
			/*
			Double freqband_diff = 0.0;
	
			if(freqband_past != null) {
				
				freqband_diff = Math.abs(freqband_neu - freqband_past); //durch Math.abs hat man keine negative Werten(klappt die Ausschläge nach oben sozusagen)
				
				daten_max[INDEX_FREQBAND] = Math.max(freqband_diff, daten_max[INDEX_FREQBAND]);
				
			}
			
			freqband_past = freqband_neu;
			
			//INTENSITY
			
			double intensity_neu = frame_akt.getIntensity();
			
			Double intensity_diff = 0.0;
			
			if(intensity_past != null) {
				
				intensity_diff =  Math.abs(intensity_neu - intensity_past);
				
				daten_max[INDEX_INTENSITY] = Math.max(intensity_diff, daten_max[INDEX_INTENSITY]);
			}
			
			intensity_past = intensity_neu;
			
			//Double[] probe = new Double[2];									//das Array hier ist das Array in der Arrayliste. Das Array ist nur temporär heißt: jeweils pro frame werden ein freqbanddiff und intensitydiff eingetragen aus dem Array in die darüber leigende Arrayliste gepackt und das Array wird wieder geleert für die nächsten beiden WErte, deshalb muss man nicht die ganze Liste (561) festleen, sondern nur die beiden Platzhalter. Das Array hat nie mehr als 2 Werte zu einer Zeit
			*/
			
		//	liste_diff.add(new Double[] {freqband_diff, intensity_diff});  		//anstatt das neue Array oben unter der Arraylist zu deklarieren, wird das erst hier gemacht, da direkt klar ist, dass er zwei WErte aufnehmen kann und man in dem sinne keinen platzhalter braucht
		}
		
		
		
		
		double[] konstante = new double[INDEX_KONNR];
		
		
		konstante[INDEX_FREQBAND] = 0.000776;
		konstante[INDEX_INTENSITY] = 0;
		konstante[INDEX_FORMANT1_FREQ] = 0.0000969;
		konstante[INDEX_FORMANT2_FREQ] = 0.000749;
		konstante[INDEX_FORMANT3_FREQ] = 0.000417;
		konstante[INDEX_FORMANT4_FREQ] = 0;
		konstante[INDEX_FORMANT5_FREQ] = 0;
		konstante[INDEX_FORMANT1_BAND] = 0.000226;
		konstante[INDEX_FORMANT2_BAND] = 0.001834725;
		konstante[INDEX_FORMANT3_BAND] = 0.000267;
		konstante[INDEX_FORMANT4_BAND] = 0;
		konstante[INDEX_FORMANT5_BAND] = 0;
		
		int grenz_treffer = 11;
		
		for(int i = 0; i < liste_diff.size(); i++) {			//hier wird in liste diff die Zeile durchgegangen und nicht die Spaltenwerte, heißt: wir verrechnen die Frames mit den Konstanten und nicht die einzelnen WErte
			
			//die Daten von liste_diff mit daten_max in bezug setzen
			
			double[] erg_konstante = new double[INDEX_KONNR];
			
			for(int k = 0; k < erg_konstante.length; k++) {		//k verrechnet die einzelnen Spalten werte in den jew. Zeilen
				
				erg_konstante[k] = (liste_diff.get(i)[k] / daten_max[k]);		
				
			}
			
		
			
			boolean ist_trennstelle = false; 
			
			//if((erg_konstante[INDEX_FREQBAND] > konstante[INDEX_FREQBAND]) || (erg_konstante[INDEX_INTENSITY] > konstante[INDEX_INTENSITY])){
			//	ist_trennstelle = true;
			//} 
			
			
			//if((erg_konstante(liste_diff.get(i))[INDEX_FORMANT1_BAND]) < 0.01) && (erg_konstante(i+1)[INDEX_FORMANT1_BAND] > 0.01)){
				//ist_trennstelle = true;
				//}
			
			/*if(((erg_konstante[INDEX_FORMANT1_BAND] < konstante[INDEX_FORMANT1_BAND]) && (erg_konstante[INDEX_FORMANT2_BAND] < konstante[INDEX_FORMANT2_BAND]))
					|| ((erg_konstante[INDEX_FORMANT4_BAND] < konstante[INDEX_FORMANT4_BAND]) && (erg_konstante[INDEX_FORMANT5_BAND] < konstante[INDEX_FORMANT5_BAND])) ){
				ist_trennstelle = true;
			} */
			
			/*if(((erg_konstante[INDEX_FORMANT1_BAND]) > konstante[INDEX_FORMANT1_BAND]) && (erg_konstante[INDEX_FREQBAND] > konstante[INDEX_FREQBAND])){
				ist_trennstelle = true;
			} */
			
			int treffer_aktuell = 0;
			
			for(int t = 0; t < erg_konstante.length; t++) {
				
				if(erg_konstante[t] > konstante[t])
					
					treffer_aktuell = treffer_aktuell + 1;
			}
			
			
			if (treffer_aktuell >= grenz_treffer) {
				
				loesungs_aus.addTrennstelle(new Trennstelle(i, erg_konstante));
			}
			
		}	
		
		return loesungs_aus;
		
	}
		
	
//ERGEBNISSE IN DATEI SCHREIBEN
	
			
	public static void write_txt (File file_ziel, Loesungssatz loesungs_ein, Datensatz daten_ein) {

		
		String[] ausgabe_dok_fix = new String[3];
		String[] ausgabe_dok_dyn = new String[INDEX_KONNR];
		
		
		//XXX Array
		ausgabe_dok_fix[0] = "Zeit(s)";
		ausgabe_dok_fix[1] = "Framenummer";
		ausgabe_dok_fix[2] = "Trennstelle";
		
		ausgabe_dok_dyn[INDEX_FREQBAND] = "Rel. Anteil FreqBand";
		ausgabe_dok_dyn[INDEX_INTENSITY] = "Rel. Anteil Intensity";
		ausgabe_dok_dyn[INDEX_FORMANT1_FREQ] = "RelFormant1Freq";
		ausgabe_dok_dyn[INDEX_FORMANT2_FREQ] = "RelFormant2Freq";
		ausgabe_dok_dyn[INDEX_FORMANT3_FREQ] = "RelFormant3Freq";
		ausgabe_dok_dyn[INDEX_FORMANT4_FREQ] = "RelFormant4Freq";
		ausgabe_dok_dyn[INDEX_FORMANT5_FREQ] = "RelFormant5Freq";
		ausgabe_dok_dyn[INDEX_FORMANT1_BAND] = "RelFormant1Band";
		ausgabe_dok_dyn[INDEX_FORMANT2_BAND] = "RelFormant2Band";
		ausgabe_dok_dyn[INDEX_FORMANT3_BAND] = "RelFormant3Band";
		ausgabe_dok_dyn[INDEX_FORMANT4_BAND] = "RelFormant4Band";
		ausgabe_dok_dyn[INDEX_FORMANT5_BAND] = "RelFormant5Band";
		
		for(int lauf_frame = 0; lauf_frame < daten_ein.getFramesize(); lauf_frame++) {
			
			ausgabe_dok_fix[0] += ";" + daten_ein.getTimestamp(lauf_frame);
			ausgabe_dok_fix[1] = ausgabe_dok_fix[1] + ";" + lauf_frame;
			
		
			Trennstelle trennstelle_treffer = null;
			
			
			
			for(int lauf_treffer = 0; lauf_treffer < loesungs_ein.getSize(); lauf_treffer++) {
				
				if(lauf_frame == loesungs_ein.getTrennstelle(lauf_treffer).getPosition()){   	//Vergleich von laufframe (Stelle im ges. Dok) mit Trennstelle aus loesungsaus (stelle aus dem errechneten Dokument) und mit get Position (Trennstelle im Objekt)
					trennstelle_treffer = loesungs_ein.getTrennstelle(lauf_treffer);	
				}			
			}
			//XXX
			if(trennstelle_treffer != null) {									//neu: ist der Wert existent, also ungleich null (vorher gleich null)	//steht außerhalb der for Schleife, weil sonst jedesmal für false (also leeres feld) ein Semikolon angefügt würde, so wird "gebündelt" für alle einmal auf false gesetzt
				ausgabe_dok_fix[2] += ";" + "1";							 //lauftreffer ist nur die position und laufframe ist der WErt = deshalb hier laufframe
				
				for(int s = 0; s < ausgabe_dok_dyn.length; s++) {
					
					String ablage_temp = ";" + trennstelle_treffer.getKennwerte()[s];
					ausgabe_dok_dyn[s] += ablage_temp.replace(".", ",");
				}
				
				/* s.o. dynamischer
				ausgabe_dok[3] += ";" + trennstelle_treffer.getKennwerte()[INDEX_FREQBAND];
				ausgabe_dok[4] += ";" + trennstelle_treffer.getKennwerte()[INDEX_INTENSITY];
				ausgabe_dok[5] += ";" + trennstelle_treffer.getKennwerte()[INDEX_FORMANT1];
				ausgabe_dok[6] += ";" + trennstelle_treffer.getKennwerte()[INDEX_FORMANT2];
				ausgabe_dok[7] += ";" + trennstelle_treffer.getKennwerte()[INDEX_FORMANT3];
				ausgabe_dok[8] += ";" + trennstelle_treffer.getKennwerte()[INDEX_FORMANT4];
				ausgabe_dok[9] += ";" + trennstelle_treffer.getKennwerte()[INDEX_FORMANT5]; */
			}else {	
				
				ausgabe_dok_fix[2] += ";" + "0";
				
				for(int t = 0; t < ausgabe_dok_dyn.length; t++) {
					ausgabe_dok_dyn[t] += ";";
				}
				
				/*drüber dynamischer
				ausgabe_dok[3] += ";";
				ausgabe_dok[4] += ";";
				ausgabe_dok[5] += ";";
				ausgabe_dok[6] += ";";
				ausgabe_dok[7] += ";";
				ausgabe_dok[8] += ";";
				ausgabe_dok[9] += ";";*/
			}	
			
		}
		
		/*s. 478/79 in der if bed.
		for(int u = 0; u < ausgabe_dok_dyn.length; u++) {
			ausgabe_dok_dyn[u] = ausgabe_dok_dyn[u].replace(".", ",");
		}*/
		/*drüber dynamischer
		ausgabe_dok[3] = ausgabe_dok[3].replace(".", ",");
		ausgabe_dok[4] = ausgabe_dok[4].replace(".", ",");
		ausgabe_dok[5] = ausgabe_dok[5].replace(".", ",");
		ausgabe_dok[6] = ausgabe_dok[6].replace(".", ",");
		ausgabe_dok[7] = ausgabe_dok[7].replace(".", ",");
		ausgabe_dok[8] = ausgabe_dok[8].replace(".", ",");
		ausgabe_dok[9] = ausgabe_dok[9].replace(".", ",");*/
		
	//WRITER FÜR TXT
		
		try {
			
			BufferedWriter buff_out = new BufferedWriter(new FileWriter(file_ziel));
			
			
			for(int i = 0; i < ausgabe_dok_fix.length; i++) {
				buff_out.write(ausgabe_dok_fix[i]);
				buff_out.newLine();	
			}
			for(int i = 0; i < ausgabe_dok_dyn.length; i++) {
				buff_out.write(ausgabe_dok_dyn[i]);
				buff_out.newLine();	
			}
				
			buff_out.flush();
			buff_out.close();
		}
		
		catch(Exception e) {
			
		}
			
		
	} 
	
	//TEXTGRID GENERIEREN
	
			
	public static void write_TextGrid (File file_ziel, Loesungssatz loesungs_ein, Datensatz daten_ein){
	
		
		ArrayList<String> ausgabe_dokneu = new ArrayList<String>();
	

		String ausgabe_dok0 = "File type = \"ooTextFile\" \r\nObject class = \"TextGrid\"";
		String ausgabe_dok1 = "xmin = " + (daten_ein.getXmin());
		String ausgabe_dok2 = "xmax = " + daten_ein.getXmax();
		String ausgabe_dok3 = "tiers? <exists>";
		String ausgabe_dok4 = "size = 1\r\nitem []: \r\nitem [1]: \r\nclass = \"IntervalTier\" \r\nname = \"trennstellen\""; 					//size = wie viele tiers (also ich brauche nur 1)
		String ausgabe_dok5 = "xmin = " + daten_ein.getXmin();
		String ausgabe_dok6 = "xmax = " + daten_ein.getXmax();
		String ausgabe_dok7 = "intervals: size = " + loesungs_ein.getSize();
		
		
		ausgabe_dokneu.add(ausgabe_dok0);
		ausgabe_dokneu.add(ausgabe_dok1);
		ausgabe_dokneu.add(ausgabe_dok2);
		ausgabe_dokneu.add(ausgabe_dok3);
		ausgabe_dokneu.add(ausgabe_dok4);
		ausgabe_dokneu.add(ausgabe_dok5);
		ausgabe_dokneu.add(ausgabe_dok6);
		ausgabe_dokneu.add(ausgabe_dok7);
		
		 
		for(int lauf_treffer = 0; lauf_treffer < loesungs_ein.getSize(); lauf_treffer++){
			
			
			String ausgabe_dok8 = "intervals [" + (lauf_treffer + 1) + "]:";
			ausgabe_dokneu.add(ausgabe_dok8);
			
			int frame_start = loesungs_ein.getTrennstelle(lauf_treffer).getPosition();
			
			String ausgabe_dok9 = "xmin = " + daten_ein.getTimestamp(frame_start);
			ausgabe_dokneu.add(ausgabe_dok9);
			
			int frame_ende = -1;
			
			if (lauf_treffer < loesungs_ein.getSize()-1) {
				frame_ende = loesungs_ein.getTrennstelle(lauf_treffer + 1).getPosition();
			}
			
			String ausgabe_dok10 = "xmax = ";
			
			if(frame_ende == -1) {
				ausgabe_dok10 += daten_ein.getXmax();
			}else {
				ausgabe_dok10 += daten_ein.getTimestamp(frame_ende);
			}
			
			ausgabe_dokneu.add(ausgabe_dok10);
			
			
				
			String ausgabe_dok11 = "text = \"\"";
			ausgabe_dokneu.add(ausgabe_dok11);
			
		}
		
		/*
		//double neuneu = daten_ein.getXmax();
		//String neuneuneu = ausgabe_dokneu.toString().add(-2, neuneu);
		
		ausgabe_dokneu.toString();
		CharSequence neu1 = ausgabe_dokneu.size()-2;
	
		Double neu2 = daten_ein.getXmax();
		
		String neu4 = Double.toString(neu2);
	
		String neu3 = ((String) ausgabe_dokneu).replace(neu1, neu4);
		
		System.out.println(neuneu); */
		
		
		
		//System.out.println(ausgabe_dokneu);
		

	
		
		try {
			
			BufferedWriter buff_out = new BufferedWriter(new FileWriter(file_ziel));
			
			for(int i = 0; i < ausgabe_dokneu.size(); i++) {
				
				
				buff_out.write(ausgabe_dokneu.get(i));
				buff_out.newLine();
				
			}
				
			buff_out.flush();
			buff_out.close();
		}
		
		catch(Exception e) {
			
		}
	
		
		
		
			
		
	}


}
