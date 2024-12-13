package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Ventana c.
 * @author pix4ke
 * @version 1.0
 */
public class VentanaC extends JFrame {

    private JTextArea historial;
    private JComboBox<String> usuariosConectados;
    private JTextField mensajeField;
    private JButton enviarButton;
    private Cliente cliente;

    /**
     * Instantiates a new Ventana c.
     *
     * @param cliente the cliente
     */
    public VentanaC(Cliente cliente) {
        this.cliente = cliente;

        // Establecer el título de la ventana con el nombre del usuario
        setTitle("--- " + cliente.getNombreUsuario() + " ---");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel para el historial de mensajes
        historial = new JTextArea();
        historial.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historial);

        // Desplegable para los usuarios conectados
        usuariosConectados = new JComboBox<>();

        // Campo para escribir el mensaje
        mensajeField = new JTextField();

        // Botón para enviar el mensaje
        enviarButton = new JButton("Enviar");
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mensaje = mensajeField.getText();
                String destinatario = (String) usuariosConectados.getSelectedItem();
                if (destinatario != null) {
                    cliente.enviarMensaje(destinatario, mensaje);
                    mensajeField.setText("");
                } else {
                    agregarMensaje("Ningún usuario seleccionado.");
                }
            }
        });

        // Panel inferior que contiene el campo de texto y el botón
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(mensajeField, BorderLayout.CENTER);
        panelInferior.add(enviarButton, BorderLayout.EAST);

        // Añadir componentes al JFrame
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(usuariosConectados, BorderLayout.NORTH);
        getContentPane().add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Agregar mensaje.
     *
     * @param mensaje the mensaje
     */
    public void agregarMensaje(String mensaje) {
        historial.append(mensaje + "\n");
    }

    /**
     * Actualizar usuarios conectados.
     *
     * @param usuarios the usuarios
     */
    public void actualizarUsuariosConectados(String[] usuarios) {
        usuariosConectados.removeAllItems();
        for (String usuario : usuarios) {
            usuariosConectados.addItem(usuario);
        }
    }

    /**
     * Numero usuarios conectados int.
     *
     * @return the int
     */
    public int numeroUsuariosConectados() {
        return usuariosConectados.getItemCount();
    }

    /**
     * Mostrar alerta.
     *
     * @param mensaje the mensaje
     */
    public void mostrarAlerta(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
