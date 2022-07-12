package it.polito.tdp.crimes.model;

public class Distretto implements Comparable<Distretto> {

	private int id;
	private double avgLat;
	private double avgLon;
	
	public Distretto(int id, double avgLat, double avgLon) {
		super();
		this.id = id;
		this.avgLat = avgLat;
		this.avgLon = avgLon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAvgLat() {
		return avgLat;
	}

	public void setAvgLat(double avgLat) {
		this.avgLat = avgLat;
	}

	public double getAvgLon() {
		return avgLon;
	}

	public void setAvgLon(double avgLon) {
		this.avgLon = avgLon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Distretto other = (Distretto) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Distretto o) {
		return this.getId() - o.getId();
	}
	
}
