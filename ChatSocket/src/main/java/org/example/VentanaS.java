package org.example;

import javax.swing.*;
import java.awt.*;

public class VentanaS extends JFrame {
    private JTextArea logArea;

    public VentanaS() {
        setTitle("Servidor de Chat");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void agregarLog(String mensaje) {
        logArea.append(mensaje + "\n");
    }
}
