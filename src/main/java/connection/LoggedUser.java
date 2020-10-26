package connection;

import model.User;

// clasa care are un camp static de utilizator, in care se va retine utilizatorul logat
// nu am nevoie de instante, pentru ca o sa am un singur utilizator logat

public class LoggedUser {

    private static User user;

    public static void setUser(User user) {
        LoggedUser.user = user;
    }

    public static User getUser() {
        return LoggedUser.user;
    }

}
