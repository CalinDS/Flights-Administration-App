package pages;

import connection.LoggedUser;
import controller.MainController;
import model.Activity;

import javax.swing.*;
import java.awt.*;

// pagina pentru confirmarea stergerii unui zbor
public class ConfirmDeletePage extends JFrame {
    private JPanel textPanel, buttonPanel;
    private JLabel textLabel = new JLabel("Are you sure you want to delete the flight?");
    private JButton yesButton = new JButton("YES");
    private JButton noButton = new JButton("NO");

    public ConfirmDeletePage(String source, String destination, JFrame parent) {
        setTitle("Confirm pop-up");
        setSize(300, 100);
        setLocationRelativeTo(null);

        textPanel = new JPanel();
        add(textPanel, BorderLayout.NORTH);
        textPanel.add(textLabel);

        buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.CENTER);
        noButton.addActionListener(e -> dispose());
        yesButton.addActionListener(e -> {
            // daca decid sa sterg, scot zborul din tabela din baza de date
            MainController mainController = new MainController();
            mainController.removeFlight(source, destination);

            // mentionez ca activitate
            Activity activity = new Activity(0, "Deleted flight at",
                    java.util.Calendar.getInstance().getTime().toString(),
                    LoggedUser.getUser().getUsername(), LoggedUser.getUser().getMail());
            mainController.addActivity(activity);

            // inchid aceasta pagina de pop-up, inchid vechea pagina cu zboruri si deschid una
            // noua, in care zborul sters nu mai exista
            dispose();
            parent.dispose();
            MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
        });
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        setVisible(true);

    }
}
