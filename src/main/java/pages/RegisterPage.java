package pages;

import controller.MainController;
import model.Activity;
import model.User;
import utils.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// clasa pentru pagina de register
public class RegisterPage extends JFrame {

    private JPanel panel;
    private JLabel usernameLabel, passwordLabel, confirmPasswordLabel, mailLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField mailField;
    private JButton registerButton, resetButton;
    private EmptyBorder border;

    public RegisterPage() {

        setTitle("Register Page");
        setSize(400, 300);

        initComponents();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        panel = new JPanel(new GridLayout(5, 2));
        border = new EmptyBorder(0, 10, 0, 0);

        initUsername();
        initPassword();
        initConfirmPassword();
        initMail();

        initRegisterButton();
        initResetButton();

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    // creez fiecare camp de completat pentru a crea un cont de utilizator
    private void initUsername() {
        usernameLabel = new JLabel("Username:");
        usernameLabel.setBorder(border);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);
    }

    private void initPassword() {
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBorder(border);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);
    }

    private void initConfirmPassword() {
        confirmPasswordLabel = new JLabel("Confirm password:");
        confirmPasswordLabel.setBorder(border);
        panel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        panel.add(confirmPasswordField);
    }

    private void initMail() {
        mailLabel = new JLabel("E-mail:");
        mailLabel.setBorder(border);
        panel.add(mailLabel);

        mailField = new JTextField();
        panel.add(mailField);
    }

    // butonul cu care fac inregistrarea
    private void initRegisterButton() {
        registerButton = new JButton("Register");
        panel.add(registerButton);
        // daca toate campurile sunt valide, adaug noul utilizator in baza de date
        // si activitatea de inregistrarea in tabela de activitati
        registerButton.addActionListener(e -> {
            if (valid()) {
                MainController mainController = new MainController();
                User user = new User(0, usernameField.getText(),
                        String.valueOf(passwordField.getPassword()), mailField.getText());
                mainController.addUser(user);

                Activity activity = new Activity(0, "Account created on",
                        java.util.Calendar.getInstance().getTime().toString(),
                        usernameField.getText(), mailField.getText());
                mainController.addActivity(activity);

                // sunt redirectionat la login
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
                showMessage("Registration succeded", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
    }

    private void initResetButton() {
        resetButton = new JButton("Reset");
        panel.add(resetButton);
        resetButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
            mailField.setText("");
            showMessage("Fields reseted!", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    // functie de validare pentru fiecare camp
    private boolean valid() {
        MainController mainController = new MainController();
        if (mainController.getUser(usernameField.getText()).size() != 0) {
            showMessage("Username taken", JOptionPane.ERROR_MESSAGE);
            usernameField.requestFocus();
            return false;
        }
        // desi nu era specificat in enunt, am considerat ca era necesar, ca si la username,
        // sa nu existe doi utilizatori cu acelasi email, pentru ca asta ar fi creat probleme
        // la login
        if(mainController.getUser(mailField.getText()).size() != 0) {
            showMessage("E-mail taken", JOptionPane.ERROR_MESSAGE);
            mailField.requestFocus();
            return false;
        }
        if (usernameField.getText().equals("")) {
            showMessage("Invalid username", JOptionPane.ERROR_MESSAGE);
            usernameField.requestFocus();
            return false;
        } else if (!Utils.securePassword(String.valueOf(passwordField.getPassword()))) {
            showMessage("Password not secure", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return false;
        } else if (!(String.valueOf(passwordField.getPassword())).
                equals(String.valueOf(confirmPasswordField.getPassword()))) {
            showMessage("Confirmed password is different", JOptionPane.ERROR_MESSAGE);
            confirmPasswordField.requestFocus();
            return false;
        } else if (!Utils.validMail(mailField.getText())) {
            showMessage("Invalid e-mail address", JOptionPane.ERROR_MESSAGE);
            mailField.requestFocus();
            return false;
        }
        return true;
    }

    // pentru pop-up-uri de eroare sau de informare
    private static void showMessage(String msg, int messageType) {
        JOptionPane.showMessageDialog(null, msg, "Register pop-up", messageType);
    }

}
