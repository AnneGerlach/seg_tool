package classes;

public class Trennstelle {
	
	private int position;
	
	private double[] kennwerte; 		//kennwerte array ist wie werte_past aufgebaut bspw. also alle Berechungen  für ein Frame
	
	public Trennstelle(int position, double[] kennwerte) {
		this.position = position;
		this.kennwerte = kennwerte;
	}
	
	public int getPosition() {
		return(position);
	}
	
	public double[] getKennwerte() {
		return(kennwerte);
	}
}
