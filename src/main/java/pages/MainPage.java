package pages;

import connection.LoggedUser;
import controller.MainController;
import model.Activity;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

// clasa pentru pagina principala, cu zboruri
public class MainPage extends JFrame {

    private JPanel timePanel, buttonPanel, tablePanel;
    private JLabel timeLabel;
    private JButton addFlightButton, accountButton, auditButton;

    public MainPage() {
        setTitle("Main Page");
        setSize(1000, 300);
        setLocationRelativeTo(null);

        initTime();
        initTable();
        initButtons();
        Utils.initMenu(this);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // panel pentru afisarea timpului curent
    private void initTime() {
        timePanel = new JPanel();
        Date date = java.util.Calendar.getInstance().getTime();
        timeLabel = new JLabel(date.toString());
        add(timePanel, BorderLayout.NORTH);
        timePanel.add(timeLabel);
    }

    // un tabel in care se vor afisa toate zborurile extrase din baza de date
    private void initTable() {
        MainController mainController = new MainController();
        int n = mainController.getNumberOfFlights();
        tablePanel = new JPanel(new GridLayout(n + 1,7, 10, 10));
        add(tablePanel);
        tablePanel.add(new JLabel("Source"));
        tablePanel.add(new JLabel("Destination"));
        tablePanel.add(new JLabel("Departure Time"));
        tablePanel.add(new JLabel("Arrival Time"));
        tablePanel.add(new JLabel("Days"));
        tablePanel.add(new JLabel("Price"));
        tablePanel.add(new JLabel(""));
        for (int i = 0; i < n; i++) {
            if (mainController.getNumberOfFlights() != n) {
                MainPage mainPage = new MainPage();
                mainPage.setVisible(true);
                dispose();
            }
            tablePanel.add(new JLabel(mainController.getFlights().get(i).getSource()));
            tablePanel.add(new JLabel(mainController.getFlights().get(i).getDestination()));
            tablePanel.add(new JLabel(mainController.getFlights().get(i).getDeparture()));
            tablePanel.add(new JLabel(mainController.getFlights().get(i).getArrival()));
            tablePanel.add(new JLabel(mainController.getFlights().get(i).getDays()));
            tablePanel.add(new JLabel("" + mainController.getFlights().get(i).getPrice()));

            // butonul de delete, cu panel special
            JPanel deletePanel = new JPanel(new BorderLayout());
            JButton deleteButton = new JButton("");
            String source = mainController.getFlights().get(i).getSource();
            String destination = mainController.getFlights().get(i).getDestination();
            deleteButton.addActionListener(e -> {
                // pagina de pop-up pentru confirmare
                ConfirmDeletePage confirmDeletePage = new ConfirmDeletePage(source, destination, this);
                confirmDeletePage.setVisible(true);
            });
            deleteButton.setBackground(Color.RED);
            deleteButton.setPreferredSize(new Dimension(20, 20));
            deletePanel.add(deleteButton, BorderLayout.LINE_START);

            tablePanel.add(deletePanel);
        }
    }

    private void initButtons() {
        buttonPanel = new JPanel();

        // butonul pentru adaugarea zborului
        addFlightButton = new JButton("Add Flight");
        addFlightButton.addActionListener(e -> {
            MainController mainController = new MainController();
            // se agauga ca activitate in tabela din baza de date
            Activity activity = new Activity(0, "Accesed Add Flight Page at",
                    java.util.Calendar.getInstance().getTime().toString(),
                    LoggedUser.getUser().getUsername(), LoggedUser.getUser().getMail());
            mainController.addActivity(activity);

            // se deschide noua fereastra
            AddFlightPage flightPage = new AddFlightPage();
            flightPage.setVisible(true);
            dispose();
        });

        // buton pentru a ajunge in pagina de account
        accountButton = new JButton("My Account");
        accountButton.addActionListener(e -> {
            MainController mainController = new MainController();
            Activity activity = new Activity(0, "Accesed My Account Page at",
                    java.util.Calendar.getInstance().getTime().toString(),
                    LoggedUser.getUser().getUsername(), LoggedUser.getUser().getMail());
            mainController.addActivity(activity);

            MyAccountPage accountPage = new MyAccountPage();
            accountPage.setVisible(true);
            dispose();
        });

        // buton pentru a accesa istoricul actiunilor
        auditButton = new JButton("Account History");
        auditButton.addActionListener(e -> {
            HistoryPage historyPage = new HistoryPage();
            historyPage.setVisible(true);
            dispose();
        });

        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(addFlightButton);
        buttonPanel.add(accountButton);
        buttonPanel.add(auditButton);
    }

}
