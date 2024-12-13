package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * The type Main cliente.
 * @author pix4ke
 * @version 1.0
 */
public class MainCliente {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        // Crear panel para agrupar los campos de entrada
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField hostField = new JTextField();
        JTextField puertoField = new JTextField();
        JTextField nombreUsuarioField = new JTextField();

        panel.add(new JLabel("Ingrese la dirección IP del servidor:"));
        panel.add(hostField);
        panel.add(new JLabel("Ingrese el puerto del servidor:"));
        panel.add(puertoField);
        panel.add(new JLabel("Ingrese su nombre de usuario:"));
        panel.add(nombreUsuarioField);

        // Mostrar el panel en un JOptionPane
        int result = JOptionPane.showConfirmDialog(null, panel, "Configuración del Cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String host = hostField.getText();
            int puerto = Integer.parseInt(puertoField.getText());
            String nombreUsuario = nombreUsuarioField.getText();

            // Crear una instancia del cliente con los datos proporcionados
            new Cliente(host, puerto, nombreUsuario);
        } else {
            System.out.println("Operación cancelada por el usuario.");
        }
    }
}
