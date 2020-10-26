package controller;

import connection.DatabaseConnection;
import dao.ActivitiesDao;
import dao.FlightsDao;
import dao.UsersDao;
import model.Activity;
import model.Flight;
import model.User;

import java.sql.Connection;
import java.util.List;

public class MainController {

    private UsersDao usersDao;
    private FlightsDao flightsDao;
    private ActivitiesDao activitiesDao;

    public MainController() {
        Connection connection = DatabaseConnection.getConnection();
        usersDao = new UsersDao(connection);
        flightsDao = new FlightsDao(connection);
        activitiesDao = new ActivitiesDao(connection);
    }

    public boolean addUser(User user) {
        return usersDao.insert(user);
    }

    // obtin utilizator dupa nume/email
    public List<User> getUser(String name) {
        return usersDao.findByName(name);
    }

    // verific daca logarea este corecta
    public boolean validLogin(String name, String password) {
        return (usersDao.findByCredential(name, password).size() != 0);
    }

    // obtin utilizatorul logat, daca exista
    public User loggedUser(String name, String password) {
        if (validLogin(name, password)) {
            return usersDao.findByCredential(name, password).get(0);
        }
        return null;
    }

    public void updateUsername(String name, String mail) {
        usersDao.updateUsername(name, mail);
    }

    public void updateMail(String mail, String username) {
        usersDao.updateMail(mail, username);
    }

    public void updatePassword(String password, String username) {
        usersDao.updatePassword(password, username);
    }

    public boolean addFlight(Flight flight) {
        return flightsDao.insert(flight);
    }

    // verific daca exista deja ruta in tabela
    public boolean hasRoute(String source, String destination) {
        return flightsDao.findByRoute(source, destination).size() != 0;
    }

    public int getNumberOfFlights() {
        return flightsDao.getAllFlights().size();
    }

    public List<Flight> getFlights() {
        return flightsDao.getAllFlights();
    }

    public boolean removeFlight(String source, String destination) {
        return flightsDao.deleteByRoute(source, destination);
    }

    public boolean addActivity(Activity activity) {
        return activitiesDao.insert(activity);
    }

    public List<Activity> getActivitiesByOwner(String name) {
        return activitiesDao.findByName(name);
    }

    // modifc nume si mail, acolo unde e cazul, in tabela de activitati
    public void updateActivityUsername(String username, String mail) {
        activitiesDao.updateUsername(username, mail);
    }

    public void updateActivityMail(String mail, String username) {
        activitiesDao.updateMail(mail, username);
    }
}
