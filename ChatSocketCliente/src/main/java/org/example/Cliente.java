package org.example;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The type Cliente.
 * @author pix4ke
 * @version 1.0
 */
public class Cliente implements Runnable {

    private String host;
    private int puerto;
    private String nombreUsuario;
    private VentanaC ventanaC;
    private PrintWriter salida;
    private BufferedReader entrada;

    /**
     * Instantiates a new Cliente.
     *
     * @param host          the host
     * @param puerto        the puerto
     * @param nombreUsuario the nombre usuario
     */
    public Cliente(String host, int puerto, String nombreUsuario) {
        this.host = host;
        this.puerto = puerto;
        this.nombreUsuario = nombreUsuario;
        this.ventanaC = new VentanaC(this);

        try {
            Socket socket = new Socket(host, puerto);
            salida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            salida.println(nombreUsuario); // Enviar nombre de usuario al servidor

            new Thread(this).start();
            ventanaC.agregarMensaje("Conectado al servidor.");
        } catch (UnknownHostException e) {
            mostrarAlerta("Error: Dirección IP desconocida.");
        } catch (IOException e) {
            mostrarAlerta("Error al conectar al servidor: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        String mensaje;
        try {
            while ((mensaje = entrada.readLine()) != null) {
                if (mensaje.startsWith("USUARIOS_CONECTADOS:")) {
                    String[] usuarios = mensaje.substring("USUARIOS_CONECTADOS:".length()).split(",");
                    ventanaC.actualizarUsuariosConectados(usuarios);
                } else {
                    ventanaC.agregarMensaje(mensaje);
                }
            }
        } catch (IOException e) {
            ventanaC.agregarMensaje("Error en la comunicación con el servidor: " + e.getMessage());
        }
    }

    /**
     * Enviar mensaje.
     *
     * @param destinatario the destinatario
     * @param mensaje      the mensaje
     */
    public void enviarMensaje(String destinatario, String mensaje) {
        if (ventanaC.numeroUsuariosConectados() <= 1) {
            ventanaC.mostrarAlerta("No hay otros clientes conectados a quienes enviar mensajes.");
        } else {
            salida.println("MENSAJE|" + nombreUsuario + "|" + destinatario + "|" + mensaje);
            ventanaC.agregarMensaje("Yo a " + destinatario + ": " + mensaje);
        }
    }

    /**
     * Gets nombre usuario.
     *
     * @return the nombre usuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    private void mostrarAlerta(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
