package utils;

import connection.LoggedUser;
import controller.MainController;
import model.Activity;
import pages.LoginPage;
import pages.MainPage;
import pages.MyAccountPage;

import javax.swing.*;
import java.util.regex.Pattern;

public class Utils {

    // functie pentru a introduce menu-bar-ul intr-o pagina
    public static void initMenu(JFrame frame) {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem home, account, logout;

        menuBar = new JMenuBar();
        menu = new JMenu("Options");
        home = new JMenuItem("Main Page");
        account = new JMenuItem("My Account Page");
        logout = new JMenuItem("Logout");

        home.addActionListener(e -> {
            // daca se schimba pagina, se adauga activitatea in baza de date
            MainController mainController = new MainController();
            Activity activity = new Activity(0, "Accesed Main Page at",
                    java.util.Calendar.getInstance().getTime().toString(),
                    LoggedUser.getUser().getUsername(), LoggedUser.getUser().getMail());
            mainController.addActivity(activity);
            // se deschide noua pagina, iar cea actuala se inchide
            MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
            frame.dispose();
        });
        account.addActionListener(e -> {
            MainController mainController = new MainController();
            Activity activity = new Activity(0, "Accesed My Account Page at",
                    java.util.Calendar.getInstance().getTime().toString(),
                    LoggedUser.getUser().getUsername(), LoggedUser.getUser().getMail());
            mainController.addActivity(activity);

            MyAccountPage myAccountPage = new MyAccountPage();
            myAccountPage.setVisible(true);
            frame.dispose();
        });
        logout.addActionListener(e -> {
            MainController mainController = new MainController();
            Activity activity = new Activity(0, "Logged out at",
                    java.util.Calendar.getInstance().getTime().toString(),
                    LoggedUser.getUser().getUsername(), LoggedUser.getUser().getMail());
            mainController.addActivity(activity);

            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
            frame.dispose();
        });
        menu.add(home);
        menu.add(account);
        menu.add(logout);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    // functie care se asigura ca parola este conform formatului cerut
    public static boolean securePassword(String pass) {
        if (pass.length() < 6) {
            return false;
        }
        return (pass.matches(".*\\d.*")
                && pass.matches(".*[A-Z].*")
                && pass.matches(".*[a-z].*"));
    }

    // functie care se asigura ca email-ul este in formatul cerut
    public static boolean validMail(String mail) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (mail == null) {
            return false;
        }
        return pat.matcher(mail).matches();
    }

}
