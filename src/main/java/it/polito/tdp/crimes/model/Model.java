package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph<Distretto,DefaultWeightedEdge> grafo;
	
	public Model() {
		dao = new EventsDao();
	}
	
	public String creaGrafo(Integer anno) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		List<Distretto> distretti = this.dao.getDistrettiLatLong(anno);
		
		// AGGIUNTA VERTICI
		Graphs.addAllVertices(this.grafo, distretti);
		
		// AGGIUNTA ARCHI
		for(Distretto d1: this.grafo.vertexSet())
			for(Distretto d2: this.grafo.vertexSet())
				if(!d1.equals(d2) && d1.getId() < d2.getId()) {
					LatLng point1 = new LatLng(d1.getAvgLat(), d1.getAvgLon());
					LatLng point2 = new LatLng(d2.getAvgLat(), d2.getAvgLon());
					double distanzaKm = LatLngTool.distance(point1, point2, LengthUnit.KILOMETER);
					
					Graphs.addEdgeWithVertices(this.grafo, d1, d2, distanzaKm);
				}
		
		String msg = String.format("Grafo creato con %d vertici e %d archi", 
					this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
		return msg;
	}
	
	public List<DistrettoDistanza> getDistanze(Distretto d) {
		List<DistrettoDistanza> distanze = new ArrayList<DistrettoDistanza>();
		
		for(Distretto vicino: Graphs.neighborListOf(this.grafo, d)) {
			DefaultWeightedEdge edge = this.grafo.getEdge(vicino, d);
			distanze.add(new DistrettoDistanza(vicino, this.grafo.getEdgeWeight(edge)));
		}
		
		return distanze;
	}
	
	public List<Distretto> getVertici() {
		List<Distretto> distretti = new ArrayList<Distretto>(this.grafo.vertexSet());
		Collections.sort(distretti);
		return distretti;
	}
	
	public List<Integer> getAllYears() {
		return dao.getAllYears();
	}
}
