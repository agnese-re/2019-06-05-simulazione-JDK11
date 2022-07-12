package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.Distretto;
import it.polito.tdp.crimes.model.Event;

public class EventsDao {
	
	public List<Integer> getAllYears() {
		String sql = "SELECT DISTINCT(YEAR(events.reported_date)) as anno "
				+ "FROM events "
				+ "ORDER BY YEAR(events.reported_date)";
		List<Integer> result = new ArrayList<Integer>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) 
				result.add(res.getInt("anno"));		// (YEAR(events.reported_date))
			
			conn.close();
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Distretto> getDistrettiLatLong(Integer anno) {
		String sql = "SELECT events.district_id, AVG(events.geo_lat) AS lat, AVG(events.geo_lon) AS lon "
				+ "FROM events "
				+ "WHERE YEAR(events.reported_date) = ? "
				+ "GROUP BY events.district_id";
		List<Distretto> result = new ArrayList<Distretto>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) 
				result.add(new Distretto(res.getInt("district_id"), res.getDouble("lat"), res.getDouble("lon")));
			
			conn.close();
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

}
