package it.polito.tdp.crimes.model;

public class DistrettoDistanza {

	private Distretto distretto;
	private double distanza;
	
	public DistrettoDistanza(Distretto distretto, double distanza) {
		super();
		this.distretto = distretto;
		this.distanza = distanza;
	}

	public Distretto getDistretto() {
		return distretto;
	}

	public void setDistretto(Distretto distretto) {
		this.distretto = distretto;
	}

	public double getDistanza() {
		return distanza;
	}

	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}

	@Override
	public String toString() {
		return this.distretto.getId() + " - " + this.distanza;
	}
	
}
