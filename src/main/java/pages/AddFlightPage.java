package pages;

import connection.LoggedUser;
import controller.MainController;
import model.Activity;
import model.Flight;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

// clasa pentru pagina de adaugare a zborului
public class AddFlightPage extends JFrame {

    private JPanel panel;
    private JLabel sourceLabel, destinationLabel, departureLabel, durationLabel, daysLabel, priceLabel;
    private JTextField sourceField, destinationField, departureField, durationField, priceField;
    private JCheckBox moBox, tuBox, weBox, thBox, frBox, saBox, suBox;
    private JButton cancelButton, addButton;
    private EmptyBorder border;

    public AddFlightPage() {
        setTitle("Add Flight Page");
        setSize(800, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridLayout(7, 2));
        border = new EmptyBorder(0, 10, 0, 0);

        add(panel);
        initSource();
        initDestination();
        initDeparture();
        initDuration();
        initDays();
        initPrice();
        initAddButton();
        initCancelButton();

        setVisible(true);
    }

    private void initSource() {
        sourceLabel = new JLabel("Source");
        sourceLabel.setBorder(border);
        panel.add(sourceLabel);

        sourceField = new JTextField();
        panel.add(sourceField);
    }

    private void initDestination() {
        destinationLabel = new JLabel("Destination");
        destinationLabel.setBorder(border);
        panel.add(destinationLabel);

        destinationField = new JTextField();
        panel.add(destinationField);
    }

    private void initDeparture() {
        departureLabel = new JLabel("Departure");
        departureLabel.setBorder(border);
        panel.add(departureLabel);

        departureField = new JTextField();
        panel.add(departureField);
    }

    private void initDuration() {
        durationLabel = new JLabel("Duration");
        durationLabel.setBorder(border);
        panel.add(durationLabel);

        durationField = new JTextField();
        panel.add(durationField);
    }

    // checkbox pentru alegere multipla
    private void initDays() {
        daysLabel = new JLabel("Days");
        daysLabel.setBorder(border);
        panel.add(daysLabel);

        moBox = new JCheckBox("Mon");
        tuBox = new JCheckBox("Tue");
        weBox = new JCheckBox("Wed");
        thBox = new JCheckBox("Thu");
        frBox = new JCheckBox("Fri");
        saBox = new JCheckBox("Sat");
        suBox = new JCheckBox("Sun");

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.add(moBox);
        checkBoxPanel.add(tuBox);
        checkBoxPanel.add(weBox);
        checkBoxPanel.add(thBox);
        checkBoxPanel.add(frBox);
        checkBoxPanel.add(saBox);
        checkBoxPanel.add(suBox);
        panel.add(checkBoxPanel);
    }

    private void initPrice() {
        priceLabel = new JLabel("Price");
        priceLabel.setBorder(border);
        panel.add(priceLabel);

        priceField = new JTextField();
        panel.add(priceField);
    }

    // functie pentru toate validarila
    private boolean validFlight() {
        if (sourceField.getText().length() < 3) {
            showMessage("Invalid source format. Minimum 3 chars required", JOptionPane.ERROR_MESSAGE);
            sourceField.requestFocus();
            return false;
        }
        if (destinationField.getText().length() < 3) {
            showMessage("Invalid destiantion format. Minimum 3 chars required", JOptionPane.ERROR_MESSAGE);
            destinationField.requestFocus();
            return false;
        }
        if (sourceField.getText().equals(destinationField.getText())) {
            showMessage("Source and destination need to be different", JOptionPane.ERROR_MESSAGE);
            destinationField.requestFocus();
            return false;
        }
        MainController mainController = new MainController();
        if (mainController.hasRoute(sourceField.getText(), destinationField.getText())) {
            showMessage("Route already exists", JOptionPane.ERROR_MESSAGE);
            destinationField.requestFocus();
            return false;
        }
        if (!validTime(departureField.getText())) {
            showMessage("Time need to be in Hours:MM format and less than 24 hours", JOptionPane.ERROR_MESSAGE);
            departureField.requestFocus();
            return false;
        }
        if (!validDuration(durationField.getText())) {
            showMessage("Time need to be in Hours:MM format", JOptionPane.ERROR_MESSAGE);
            departureField.requestFocus();
            return false;
        }
        if (getDays().equals("")) {
            showMessage("No days selected", JOptionPane.ERROR_MESSAGE);
            daysLabel.requestFocus();
            return false;
        }
        if (!validPrice()) {
            showMessage("Number has to be a positive integer", JOptionPane.ERROR_MESSAGE);
            priceField.requestFocus();
            return false;
        }
        return true;
    }

    // functie care verifica daca un string reprezinta de fapt un numar
    private boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    // functie care verifica ca timpul (reprezentat ca String) este valid, in format
    // de zi cu 24 de ore
    private boolean validTime(String time) {
        if ((time.length() == 4 || time.length() == 5) &&
                (time.indexOf(':') == 1 || time.indexOf(':') == 2)) {
            String [] numbers = time.split(":");
            if (numbers.length == 2) {
                if (isNumeric(numbers[0]) && isNumeric(numbers[1])) {
                    if (Integer.parseInt(numbers[0]) < 24 &&
                            Integer.parseInt(numbers[1]) < 60
                        && Integer.parseInt(numbers[0]) >= 0 &&
                            Integer.parseInt(numbers[1]) >= 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // functie care verifica ca durata este valida. Nu mai tine cont de 24 de ore
    private boolean validDuration(String time) {
        // ma asigur totusi ca nu se poate sa am un zbor cu durata de peste 1000 de ore
        if ((time.length() == 4 || time.length() == 5) &&
                (time.indexOf(':') == 1 || time.indexOf(':') == 2)
                || time.indexOf(':') == 3) {
            String [] numbers = time.split(":");
            if (numbers.length == 2) {
                if (isNumeric(numbers[0]) && isNumeric(numbers[1])) {
                    if (Integer.parseInt(numbers[1]) < 60 &&
                            Integer.parseInt(numbers[0]) >= 0 &&
                            Integer.parseInt(numbers[1]) >= 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // construiesc un string in care retin zilele selectate
    private String getDays() {
        String days = "";
        if (moBox.isSelected()) {
            days += "Mon, ";
        }
        if (tuBox.isSelected()) {
            days += "Tue, ";
        }
        if (weBox.isSelected()) {
            days += "Wed, ";
        }
        if (thBox.isSelected()) {
            days += "Thu, ";
        }
        if (frBox.isSelected()) {
            days += "Fri, ";
        }
        if (saBox.isSelected()) {
            days += "Sat, ";
        }
        if (suBox.isSelected()) {
            days += "Sun, ";
        }
        if (days.equals("")){
            return days;
        } else {
            return days.substring(0, days.length() - 2);
        }
    }

    private boolean validPrice() {
        return (isNumeric(priceField.getText()) && Integer.parseInt(priceField.getText()) > 0);
    }

    // functie care scoate minutele dintr-un string de tip ORA:MINUT
    private static int getMinutes(String time) {
        String [] numbers = time.split(":");
        return Integer.parseInt(numbers[0]) * 60 + Integer.parseInt(numbers[1]);
    }

    // functie care imi obtine, sub forma de string, ora sosirii unui zbor, in functie
    // de plecare si durata zborului
    private String getArrival(String initTime, String duration) {
        String arrival = "";
        Date dat = Time.valueOf(initTime + ":00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dat);
        calendar.add(Calendar.MINUTE, getMinutes(duration));
        String [] time = calendar.getTime().toString().split(" " );
        arrival = time[3].substring(0, time[3].length() - 3);
        return arrival;
    }

    // butonul cu care se adauga un zbor
    private void initAddButton() {
        addButton = new JButton("Add Flight");
        addButton.addActionListener(e -> {
            if (validFlight()) {
                MainController mainController = new MainController();

                // marchez ca activitate
                Activity activity = new Activity(0, "Added new flight at",
                        java.util.Calendar.getInstance().getTime().toString(),
                        LoggedUser.getUser().getUsername(), LoggedUser.getUser().getMail());
                mainController.addActivity(activity);

                String arrival = getArrival(departureField.getText(), durationField.getText());
                // creez un obiect de tip zbor pe baza input-ului si il adaug in baza de date
                Flight flight = new Flight(0, sourceField.getText(), destinationField.getText(),
                        departureField.getText(), arrival, getDays(), Integer.parseInt(priceField.getText()));
                mainController.addFlight(flight);
                MainPage mainPage = new MainPage();
                mainPage.setVisible(true);
                dispose();
            }
        });
        panel. add(addButton);
    }

    private void initCancelButton() {
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
            dispose();
        });
        panel.add(cancelButton);
    }

    private static void showMessage(String msg, int messageType) {
        JOptionPane.showMessageDialog(null, msg, "AddFlight pop-up", messageType);
    }


}
