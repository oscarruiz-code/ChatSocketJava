package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * The type Main servidor.
 *
 * @author pix4ke
 * @version 1.0
 */
public class MainServidor {

    public static void main(String[] args) {

        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField hostserver = new JTextField();

        panel.add(new JLabel("Ingrese la dirección IP del servidor:"));
        panel.add(hostserver);

        int result = JOptionPane.showConfirmDialog(null, panel, "Configuración del Servidor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {

                int puerto = Integer.parseInt(hostserver.getText());
                VentanaS ventanaS = new VentanaS();
                Servidor servidor = new Servidor(puerto, ventanaS);
                new Thread(servidor).start();

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(null, "El puerto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
