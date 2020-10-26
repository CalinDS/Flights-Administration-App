package dao;

import model.Flight;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlightsDao {

    Connection connection;

    PreparedStatement insertQuerry;
    PreparedStatement selectQuerry;
    PreparedStatement flightsQuerry;
    PreparedStatement deleteQuerry;

    public FlightsDao(Connection connection) {
        this.connection = connection;
        try {
            insertQuerry = connection.prepareStatement("INSERT INTO flights VALUES (null, ?, ?, ?, ? , ?, ?)");
            selectQuerry = connection.prepareStatement("SELECT * FROM flights WHERE source = ? AND destination = ?");
            flightsQuerry = connection.prepareStatement("SELECT * FROM flights");
            deleteQuerry = connection.prepareStatement("DELETE FROM flights WHERE source = ? AND destination = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // adaug zbor in tabela
    public boolean insert(Flight flight) {
        try {
            insertQuerry.setString(1, flight.getSource());
            insertQuerry.setString(2, flight.getDestination());
            insertQuerry.setString(3, flight.getDeparture());
            insertQuerry.setString(4, flight.getArrival());
            insertQuerry.setString(5, flight.getDays());
            insertQuerry.setInt(6, flight.getPrice());
            return insertQuerry.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // functie care ma va ajuta sa ma asigura ca nu se introduce aceeasi pereche sursa destinatie
    // de doua ori
    public List<Flight> findByRoute (String source, String destination) {
        try {
            selectQuerry.setString(1, source);
            selectQuerry.setString(2, destination);
            ResultSet result = selectQuerry.executeQuery();

            List<Flight> flights = new ArrayList<>();
            while (result.next()) {
                Flight flight = new Flight(
                        result.getInt("id"),
                        result.getString("source"),
                        result.getString("destination"),
                        result.getString("departure"),
                        result.getString("arrival"),
                        result.getString("days"),
                        result.getInt("prize"));
                flights.add(flight);
            }
            return flights;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    // obtin cu un SELECT toate zborurile
    public List<Flight> getAllFlights() {
        try {
            ResultSet result = flightsQuerry.executeQuery();

            List<Flight> flights = new ArrayList<>();
            while (result.next()) {
                Flight flight = new Flight(
                        result.getInt("id"),
                        result.getString("source"),
                        result.getString("destination"),
                        result.getString("departure"),
                        result.getString("arrival"),
                        result.getString("days"),
                        result.getInt("prize"));
                flights.add(flight);
            }
            return flights;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    // sterg un zbor in functie de sursa si destinatie
    public boolean deleteByRoute(String source, String destination) {
        try {
            deleteQuerry.setString(1, source);
            deleteQuerry.setString(2, destination);
            return deleteQuerry.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
