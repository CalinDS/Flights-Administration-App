package pages;

import connection.LoggedUser;
import controller.MainController;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

// clasa pentru pagina cu istoricul actiunilor
public class HistoryPage extends JFrame {

    JPanel panel;

    public HistoryPage() {
        setTitle("History Page");
        setSize(500, 300);
        setLocationRelativeTo(null);

        initActivities();
        Utils.initMenu(this);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initActivities() {
        // extrag din tabela toate activitatile realizate de utilizatorul curent si le afisez
        MainController mainController = new MainController();
        int n = mainController.getActivitiesByOwner(LoggedUser.getUser().getUsername()).size();
        panel = new JPanel(new GridLayout(n, 2, 10, 10));
        add(panel);
        for (int i = n - 1; i >= 0; i--) {
            panel.add(new JLabel(" " + mainController.getActivitiesByOwner
                    (LoggedUser.getUser().getUsername()).get(i).getActivity()));
            panel.add(new JLabel(mainController.getActivitiesByOwner
                    (LoggedUser.getUser().getUsername()).get(i).getTime() + " "));
        }
    }

}
