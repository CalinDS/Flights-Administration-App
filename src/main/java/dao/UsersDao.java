package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersDao {

    Connection connection;

    PreparedStatement insertQuerry;
    PreparedStatement selectQuerry;
    PreparedStatement loginQuerry;
    PreparedStatement updateUserQuerry;
    PreparedStatement updateMailQuerry;
    PreparedStatement updatePassQuerry;

    public UsersDao(Connection connection) {
        this.connection = connection;
        try {
            insertQuerry = connection.prepareStatement("INSERT INTO userss VALUES (null, ?, ?, ?)");
            selectQuerry = connection.prepareStatement("SELECT * FROM userss WHERE username = ? OR mail = ?");
            loginQuerry = connection.prepareStatement("SELECT * FROM userss WHERE (username = ? OR mail = ?) AND password = ?");
            updateUserQuerry = connection.prepareStatement("UPDATE userss SET username = ? where mail = ?");
            updateMailQuerry = connection.prepareStatement("UPDATE userss SET mail = ? where username = ?");
            updatePassQuerry = connection.prepareStatement("UPDATE userss SET password = ? where username = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // inserez in tabela pe baza unui obiect de tip utilizator
    public boolean insert(User user) {
        try {
            insertQuerry.setString(1, user.getUsername());
            insertQuerry.setString(2, user.getPassword());
            insertQuerry.setString(3, user.getMail());
            return insertQuerry.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // obtin toti utilizatorii care au fie numele fie mailul conform parametrului
    // nu ar trebui niciodata sa fie 2
    public List<User> findByName(String name) {
        try {
            selectQuerry.setString(1, name);
            selectQuerry.setString(2, name);
            ResultSet result = selectQuerry.executeQuery();

            List<User> users = new ArrayList<>();
            while (result.next()) {
                User user = new User(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("mail"));
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    // tot cu un querry de SELECT, obtin utilizatorul care este introdus la login
    // si verific daca exista in tabela
    public List<User> findByCredential(String name, String password) {
        try {
            loginQuerry.setString(1, name);
            loginQuerry.setString(2, name);
            loginQuerry.setString(3, password);
            ResultSet result = loginQuerry.executeQuery();

            List<User> users = new ArrayList<>();
            while (result.next()) {
                User user = new User(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("mail"));
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    // modific numele de utilizator, in functie de email (stiu ca sunt ale utilizatorului logat)
    public void updateUsername(String username, String mail) {
        try {
            updateUserQuerry.setString(1, username);
            updateUserQuerry.setString(2, mail);
            updateUserQuerry.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // operatiunea inversa
    public void updateMail(String mail, String username) {
        try {
            updateMailQuerry.setString(1, mail);
            updateMailQuerry.setString(2, username);
            updateMailQuerry.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // si cu parola
    public void updatePassword(String password, String username) {
        try {
            updatePassQuerry.setString(1, password);
            updatePassQuerry.setString(2, username);
            updatePassQuerry.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
