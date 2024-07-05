package starter;

import java.io.File;

import javax.swing.JFileChooser;

import classes.Datensatz;
import classes.Formant;
import classes.Frame;
import classes.Loesungssatz;
import segmentation.Analyzer;

public class Starter {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		/*Formant myFormant; 								//Objekt Typ Formant ,Strg+Leerzeichen wenn item nicht bekannt
		myFormant = new Formant(370, 200);				//neues Objekt mit den Werten der Klasse Formant
	    double x = myFormant.getBandwidth();
	    System.out.println(myFormant);
		
	    Formant[] array = new Formant[11];				//Array mit 11 Plätzen erzeugt und an der Stelle 4 der Formanten werden die Frequ. und Bandbreite 33 23 eingetragen
	    array[4] = new Formant(33, 23);
	    System.out.println(array[4].getBandwidth()); */
	   
		/* so wird überprüft ob ein Objekt wie hier x, die Merkmale der Konstante RADZAHL teilt
	   int x = 4;
	   if(x == Formant.RADZAHL) {
		   
	   } */
		
		
		File source = null;									//hier kommt Datei von choose rein
															//gleich null, weil sonst unten nach dem if ein else nötig wäre, der in jedem fall source einen wert braucht, oder hier eben schon dem source direkt denn null wert geben (eben alterniv sonst else source = null)
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("path_for_file/Konstanten_Berechnung"));     //um Ort auszuwählen wo datei liegt (hier aber erstmal raus)
		
		//die nächsten 2 Zeilen: nur zur Modifikation des Fensters, hier wird noch nichts ausgewählt
		chooser.setDialogTitle("Bitte Quelldatei wählen (txt)"); //wir sagen dem Objekt was es kann, zb hier dass es den Quelltext so undso hat
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); //damit keine Ordner gezeigt werden, sondern Dateien
																//Methode die Chooser sagt, zeig Dialogfenster
		//bei showOpenDialog wird erst geöffnet UND auch angenommen was gewählt wird (beides gleichzeitig)
		if(chooser.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) {  	//Approve und files only sind Konstanten der Klasse JChooser und wir überprüfen ob unser Objekt mit dem übereinstimmt
		
			source = chooser.getSelectedFile();	
		}
		
		
		//Reader wird aufgerufen 
		Datensatz meine_daten = Analyzer.readfile(source);		//hier wird alles was aus klasse Filereader kommt reingeschrieben, dh die eingelesenen und strukturierten Daten
		
		//was vom Reader kommt wird hier verarbeitet
		Loesungssatz meine_loesung = Analyzer.analyze(meine_daten);
		
		//Lösung wird in neue Datei gepackt
		String ziel_pfad = source.getAbsolutePath();
		
		ziel_pfad = ziel_pfad.replace("_Formantenanalyse.", ".");
		
		ziel_pfad = ziel_pfad.replace(".", "_Segmentgrenzen.");
		
		File file_txt = new File(ziel_pfad.replace(".txt", ".csv"));
		
		File file_TextGrid = new File(ziel_pfad.replace(".txt", ".TextGrid"));
		
		Analyzer.write_txt(file_txt,  meine_loesung, meine_daten);
		
		Analyzer.write_TextGrid(file_TextGrid, meine_loesung, meine_daten);
		
		
	
		
		//meine_daten.getFrame(23).getFormant(3).getBandwidth();
		/*									//Exkurs, wie funktioniert ein try catch (Fehler im try bedeutet nicht ende des programms, sonder er wird gecatched und läuft ohne fehler durch 
		int bla = 6; 
		
		try{
			bla = 8;
			int[] test = new int[4];
			test[4] = 7;
		}catch(Exception e) {
			
			int bla2 = 2;		
		} */
		
		
	    /*
	    File parent = null;
	    
	    JFileChooser chooser = new JFileChooser();
		try {
			chooser.setCurrentDirectory(new File(Starter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile());
		}catch(Exception e1) {}
		chooser.setDialogTitle("Bitte E-Mailverzeichnis wählen");
		chooser.setAcceptAllFileFilterUsed(false);
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			parent = chooser.getSelectedFile();
			System.out.println("Durchsuche " + parent.getAbsolutePath());
		}else {
			System.out.println("Kein Verzeichnis ausgewählt");
		}
	    
		
	    Datensatz d = Filereader.readfile(parent);  */
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    //double versuch;													//Differenz der Frequenz der jeweils 3. Formanten von Frame 2 und 3 
	   /* double freq1 =  d.getFrame(1).getFormant(2).getFrequency();
	    double freq2 = d.getFrame(2).getFormant(2).getFrequency();
	    System.out.println(freq1);
	    System.out.println(freq2);
	    
	    versuch = freq1 - freq2;
	    System.out.println(versuch); */
	    
	    
	}														
	
	

}
