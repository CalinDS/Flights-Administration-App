package pages;

import javax.swing.*;
import java.awt.*;

// pagina de start, cu butoanele de login si register
public class StartPage extends JFrame {

    private JPanel buttonPanel;
    private JButton registerButton, loginButton;

    public StartPage() {
        setTitle("Start Page");
        setSize(100, 125);
        setLocationRelativeTo(null);

        initComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        buttonPanel = new JPanel();
        LayoutManager layoutManager = new FlowLayout();
        buttonPanel.setLayout(layoutManager);

        registerButton = new JButton("Register");
        loginButton = new JButton("Login");

        // fiecare buton redirectioneaza la pagina respectiva
        registerButton.addActionListener(e -> {
            RegisterPage registerPage = new RegisterPage();
            registerPage.setVisible(true);
            dispose();
        });

        loginButton.addActionListener(e -> {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
            dispose();
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);

        add(buttonPanel);

    }

}
