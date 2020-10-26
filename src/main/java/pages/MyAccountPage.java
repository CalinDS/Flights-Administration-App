package pages;

import connection.LoggedUser;
import controller.MainController;
import model.Activity;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

// clasa pentru pagina de contului meu
public class MyAccountPage extends JFrame {

    private JPanel infoPanel, changePanel, passwordPanel;
    private JLabel usernameLabel, mailLabel, usernameInfoLabel, mailInfoLabel;
    private JLabel usernameChangeLabel, mailChangeLabel;
    private JTextField usernameChangeField, mailChangeField;
    private JButton usernameButton, mailButton, passwordButton;

    public  MyAccountPage() {
        setTitle("My Account Page");
        setSize(600, 200);
        setLocationRelativeTo(null);

        initInfo();
        initChange();
        initPassword();
        Utils.initMenu(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initInfo() {
        // afisez numele si mail-ul utilizatorului logat
        infoPanel = new JPanel(new GridLayout(2, 3));
        add(infoPanel, BorderLayout.NORTH);
        usernameLabel = new JLabel("Username:");
        mailLabel = new JLabel("E-mail");
        usernameInfoLabel = new JLabel(LoggedUser.getUser().getUsername());
        mailInfoLabel = new JLabel(LoggedUser.getUser().getMail());
        infoPanel.add(usernameLabel);
        infoPanel.add(usernameInfoLabel);
        infoPanel.add(mailLabel);
        infoPanel.add(mailInfoLabel);
    }

    private void initChange() {
        // creez un panel pentru schimbarea numelui si mail-ului
        changePanel = new JPanel(new GridLayout(2, 3));
        add(changePanel, BorderLayout.CENTER);
        usernameChangeLabel = new JLabel("New username:");
        mailChangeLabel = new JLabel("New e-mail:");
        usernameChangeField = new JTextField();
        mailChangeField = new JTextField();
        usernameButton = new JButton("CHANGE_USERNAME");
        usernameButton.addActionListener(e -> {
            if (validUsername()) {
                // daca schimb ceva, trebuie sa updatez atributele utilizatorului logat
                // in baza de date (tabela de users si cea de activities), in pagina de
                // MyAccount si in clasa LoggedUser. Totodata, marchez ca activitate.
                MainController mainController = new MainController();
                mainController.updateUsername(usernameChangeField.getText(), LoggedUser.getUser().getMail());
                LoggedUser.getUser().setUsername(usernameChangeField.getText());

                mainController.updateActivityUsername(usernameChangeField.getText(), LoggedUser.getUser().getMail());
                Activity activity = new Activity(0, "Modified username at",
                        java.util.Calendar.getInstance().getTime().toString(),
                        LoggedUser.getUser().getUsername(), LoggedUser.getUser().getMail());
                mainController.addActivity(activity);

                MyAccountPage myAccountPage = new MyAccountPage();
                myAccountPage.setVisible(true);
                dispose();
            }
        });
        mailButton = new JButton("CHANGE_EMAIL");
        mailButton.addActionListener(e -> {
            if (validMail()) {
                MainController mainController = new MainController();
                mainController.updateMail(mailChangeField.getText(), LoggedUser.getUser().getUsername());
                LoggedUser.getUser().setMail(mailChangeField.getText());

                mainController.updateActivityMail(mailChangeField.getText(), LoggedUser.getUser().getUsername());
                Activity activity = new Activity(0, "Modified e-mail at",
                        java.util.Calendar.getInstance().getTime().toString(),
                        LoggedUser.getUser().getUsername(), LoggedUser.getUser().getMail());
                mainController.addActivity(activity);

                MyAccountPage myAccountPage = new MyAccountPage();
                myAccountPage.setVisible(true);
                dispose();
            }
        });
        changePanel.add(usernameChangeLabel);
        changePanel.add(usernameChangeField);
        changePanel.add(usernameButton);
        changePanel.add(mailChangeLabel);
        changePanel.add(mailChangeField);
        changePanel.add(mailButton);
    }

    private void initPassword() {
        // deschid pagina separata pentru schimbarea parolei
        passwordPanel = new JPanel();
        add(passwordPanel, BorderLayout.SOUTH);
        passwordButton = new JButton("CHANGE PASSWORD");
        passwordButton.addActionListener(e -> {
            ChangePasswordPage changePasswordPage = new ChangePasswordPage();
            changePasswordPage.setVisible(true);
            dispose();
        });
        passwordPanel.add(passwordButton);
    }

    // functii de validare
    private boolean validUsername() {
        MainController mainController = new MainController();
        if (mainController.getUser(usernameChangeField.getText()).size() != 0) {
            showMessage("Username taken", JOptionPane.ERROR_MESSAGE);
            usernameChangeField.requestFocus();
            return false;
        }
        if (usernameChangeField.getText().equals("")) {
            showMessage("Invalid username", JOptionPane.ERROR_MESSAGE);
            usernameChangeField.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validMail() {
        MainController mainController = new MainController();
        if(mainController.getUser(mailChangeField.getText()).size() != 0) {
            showMessage("E-mail taken", JOptionPane.ERROR_MESSAGE);
            mailChangeField.requestFocus();
            return false;
        }
        if (!Utils.validMail(mailChangeField.getText())) {
            showMessage("Invalid e-mail address", JOptionPane.ERROR_MESSAGE);
            mailChangeField.requestFocus();
            return false;
        }
        return true;
    }

    private static void showMessage(String msg, int messageType) {
        JOptionPane.showMessageDialog(null, msg, "Change info pop-up", messageType);
    }

}
