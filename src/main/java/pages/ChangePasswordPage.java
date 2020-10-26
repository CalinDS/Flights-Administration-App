package pages;

import connection.LoggedUser;
import controller.MainController;
import model.Activity;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

// clasa pentru pagina de schimbare a parolei
public class ChangePasswordPage extends JFrame {

    JPanel panel = new JPanel(new GridLayout(3, 2));
    JLabel passwordLabel = new JLabel("Password");
    JLabel confirmLabel = new JLabel("Confirm password");
    JPasswordField passwordField = new JPasswordField();
    JPasswordField confirmField = new JPasswordField();
    JButton changeButton = new JButton("Confirm");
    JButton cancelButton = new JButton("Cancel");

    public ChangePasswordPage() {
        setTitle("Change Password Page");
        setSize(300, 150);
        setLocationRelativeTo(null);

        initComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        add(panel);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmLabel);
        panel.add(confirmField);
        panel.add(changeButton);
        panel.add(cancelButton);
        changeButton.addActionListener(e -> {
            if (validChange()) {
                // daca se realizeaza schimbarea, modific in baza de date,
                // marchez ca activitate si fac logout
                MainController mainController = new MainController();
                mainController.updatePassword(String.valueOf(passwordField.getPassword()),
                        LoggedUser.getUser().getUsername());

                Activity activity = new Activity(0, "Modified password at",
                        java.util.Calendar.getInstance().getTime().toString(),
                        LoggedUser.getUser().getUsername(), LoggedUser.getUser().getMail());
                mainController.addActivity(activity);

                LoggedUser.getUser().setPassword(String.valueOf(passwordField.getPassword()));
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
                dispose();
            }
        });
        cancelButton.addActionListener(e -> {
            MyAccountPage myAccountPage = new MyAccountPage();
            myAccountPage.setVisible(true);
            dispose();
        });
    }

    private boolean validChange() {
        if (!Utils.securePassword(String.valueOf(passwordField.getPassword()))) {
            showMessage("Password not secure", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return false;
        }
        if (!(String.valueOf(passwordField.getPassword())).
                equals(String.valueOf(confirmField.getPassword()))) {
            showMessage("Confirmed password is different", JOptionPane.ERROR_MESSAGE);
            confirmField.requestFocus();
            return false;
        }
        return true;
    }

    private static void showMessage(String msg, int messageType) {
        JOptionPane.showMessageDialog(null, msg, "Change password pop-up", messageType);
    }

}
