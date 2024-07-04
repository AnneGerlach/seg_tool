package classes;

import java.util.ArrayList;

public class Datensatz {							//oberste Ebene, hier kommt das rein, was im txt ganz links steht (5 var + frames bzw. deren Anzahl)
	private double xmin;								//unter Frame findet man dann das was drinnen steht, also intensity + Fromanten - darin wiederum liegen dann in der Klasse Formant die Bandbreite/Frequenz
	private double xmax;
	private int nx;
	private double dx;
	private double x1;
	//private Frame[] frames;					//Frame array - frames ist Variable des Typs Array in Frame
	private ArrayList<Frame> frames;			//ArrayList - eine Klasse, die bereits vordefiniert ist - Variable des Typs ArrayList, die speziell den Datentyp Frame abspeichert
	
	public Datensatz() {			
		this.frames = new ArrayList<Frame>();			
	}
	
	public Datensatz(int xmin, double xmax, int nx, double dx, double x1) {
		this.xmin = xmin;
		this.xmax = xmax;
		this.nx = nx;
		this.dx = dx;
		this.x1 = x1;
		//this.frames = new Frame[nx];				//erzeugen eine Objekts die eine Liste Frame mit Platz für nx (Anzahl aller Frames einer Datei) enthält und schreibt die in die private Liste Frames
		this.frames = new ArrayList<Frame>();			//hier Konstruktor im Konstruktor (AL ist eigener Kons)
	}
	
	
	public void addFrame(Frame frame_new){ 				//Methode, die ein Objekt der Klasse Frame bekommt und frame_new ist der Platzhalter für das was aus dem starter kommt
		frames.add(frame_new); 							//Holen uns von starter einen frame und fügen ihn der Liste hinzu
	}													//quasi setter aber fügt nur hinzu und überschreibt nicht das was in der Variable steht (unsere Liste ist leer)
														//es wird nicht überschrieben sondern nach und nach aufgefüllt in der liste
	
	public Frame getFrame(int nFrame) {					//getter, damit wir im starter mit den frames (die in der Liste bereits liegen) rechnen können				
		return(frames.get(nFrame));
	}													//ArrayList braucht keinen setter (der die STelle sagt) sondern weiß wo was hinkommt deshalb reicht ein adder
	
	public int getFramesize() {
		return(frames.size());
	}
	
	//GETTER
	
	public double getXmin(){							//getter holt die Var. aus der private var. und gibt sie uns in die Main Methode
		return(xmin);
	}
	public double getXmax(){
		return(xmax);
	}
	public int getNx(){
		return(nx);
	}
	public double getDx(){
		return(dx);
	}
	public double getX1(){
		return(x1);
	}
	
	//SETTER
	
	public void setXmin(double xmin){				//setter gibt uns variable aus der main methode in ()
		this.xmin = xmin;						// wir schreiben sie in this.xmin (oben die private var)
	}
	public void setXmax(double xmax){
		this.xmax = xmax;
	}
	public void setNx(int nx) {
		this.nx = nx;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}
	public void setX1(double x1) {
		this.x1 = x1;
	}
	
	/*public void setNFrame(int nFrame) {
		this.nFrame = nFrame;
	}
	*/
	
	//Zu Array Frame: Brauchen wir wieder getter/setter mit Frage nach Nummer eines Frames und Zeitstempel?
	
	
	
	//Brauche ich hier unten wieder ein array? also ts[an der Stelle nFrame]?
	
	public double getTimestamp(int nFrame){
		if ((nFrame >= 0)&&(nFrame < nx)){
			double ts = this.x1 + (this.dx * (nFrame));
			return(ts);
		}else if(nFrame == nx) {
			return(xmax);
		}else {
			return(xmin);
		}
	} 
		
	
	
	
	

}
