package pages;

import connection.LoggedUser;
import controller.MainController;
import model.Activity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// clasa pentru pagina de login
public class LoginPage extends JFrame {

    private JPanel panel;
    private JLabel userLabel, passwordLabel;
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private EmptyBorder border;

    public LoginPage() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Login Page");
        setSize(300, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridLayout(3, 2));
        border = new EmptyBorder(0, 10, 0, 10);

        initUser();
        initPassword();
        initLoginButton();
        initRegisterButton();

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void initUser() {
        userLabel = new JLabel("user/e-mail:");
        userLabel.setBorder(border);
        panel.add(userLabel);

        userField = new JTextField();
        panel.add(userField);
    }

    private void initPassword() {
        passwordLabel = new JLabel("password:");
        passwordLabel.setBorder(border);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);
    }

    private void initLoginButton() {
        loginButton = new JButton("Login");
        panel.add(loginButton);
        loginButton.addActionListener(e -> {
            // verific daca utilizatorul care vrea sa se logheze e in baza de date
            if (valid(userField.getText(), String.valueOf(passwordField.getPassword()))) {
                // retin in clasa LoggedUser utilizatorul curent logat
                MainController mainController = new MainController();
                LoggedUser.setUser(mainController.loggedUser(userField.getText(), String.valueOf(passwordField.getPassword())));
                // marchez activitatea in tabela
                Activity activity = new Activity(0, "Logged in at",
                        java.util.Calendar.getInstance().getTime().toString(),
                        LoggedUser.getUser().getUsername(), LoggedUser.getUser().getMail());
                mainController.addActivity(activity);
                // intru in pagina principala
                MainPage mainPage = new MainPage();
                mainPage.setVisible(true);
                dispose();
            } else {
                showMessage("Wrong credentials", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // am optiunea sa merg la inregistrare
    private void initRegisterButton() {
        registerButton = new JButton("Register");
        panel.add(registerButton);
        registerButton.addActionListener(e -> {
            RegisterPage registerPage = new RegisterPage();
            registerPage.setVisible(true);
            dispose();
        });
    }

    private boolean valid(String name, String password) {
        MainController mainController = new MainController();
        return mainController.validLogin(name, password);
    }

    private static void showMessage(String msg, int messageType) {
        JOptionPane.showMessageDialog(null, msg, "Login pop-up", messageType);
    }

}
