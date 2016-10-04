package gui;

import javax.swing.*;

public class MainWindow extends JFrame {


    public void initGUI() {
        setTitle("Car-rental");
        setSize(400, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JButton testConnectionButton = new JButton("Test connection");
        panel.add(testConnectionButton);
        add(panel);
        setVisible(true);
    }



}
