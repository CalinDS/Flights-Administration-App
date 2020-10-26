package dao;

import model.Activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivitiesDao {

    Connection connection;

    PreparedStatement insertQuerry;
    PreparedStatement selectQuerry;
    PreparedStatement updateUserQuerry;
    PreparedStatement updateMailQuerry;

    public ActivitiesDao(Connection connection) {
        this.connection = connection;

        try {
            insertQuerry = connection.prepareStatement("INSERT INTO activities VALUES (null, ?, ?, ?, ?)");
            selectQuerry = connection.prepareStatement("SELECT * FROM activities WHERE username = ? OR mail = ?");
            updateUserQuerry = connection.prepareStatement("UPDATE activities SET username = ? where mail = ?");
            updateMailQuerry = connection.prepareStatement("UPDATE activities SET mail = ? where username = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // adaug activitate in tabela
    public boolean insert(Activity activity) {
        try {
            insertQuerry.setString(1, activity.getActivity());
            insertQuerry.setString(2, activity.getTime());
            insertQuerry.setString(3, activity.getUsername());
            insertQuerry.setString(4, activity.getMail());
            return insertQuerry.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // obtin din tabela activitatile unui anumit utilizator (cel logat), fie dupa
    // nume, fie dupa mail
    public List<Activity> findByName(String name) {
        try {
            selectQuerry.setString(1, name);
            selectQuerry.setString(2, name);
            ResultSet result = selectQuerry.executeQuery();

            List<Activity> activities = new ArrayList<>();
            while (result.next()) {
                Activity activity = new Activity(
                        result.getInt("id"),
                        result.getString("activity"),
                        result.getString("time"),
                        result.getString("username"),
                        result.getString("mail"));
                activities.add(activity);
            }
            return activities;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    // daca se modifica numele utilizatorului, il modific si in tabela, uitandu-ma dupa mail
    public void updateUsername(String username, String mail) {
        try {
            updateUserQuerry.setString(1, username);
            updateUserQuerry.setString(2, mail);
            updateUserQuerry.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // si invers
    public void updateMail(String mail, String username) {
        try {
            updateMailQuerry.setString(1, mail);
            updateMailQuerry.setString(2, username);
            updateMailQuerry.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
