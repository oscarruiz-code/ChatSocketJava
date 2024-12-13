package org.example;

import java.io.*;
import java.net.Socket;

/**
 * The type Hilo cliente.
 * @author pix4ke
 * @version 1.0
 */
public class HiloCliente implements Runnable {

    private Socket socket;
    private Servidor servidor;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String nombreUsuario;

    /**
     * Instantiates a new Hilo cliente.
     *
     * @param socket   the socket
     * @param servidor the servidor
     */
    public HiloCliente(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
        try {
            salida = new PrintWriter(socket.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nombreUsuario = entrada.readLine(); // Leer el nombre de usuario
            servidor.notificarConexion("El cliente " + nombreUsuario + " se ha unido al chat.");
        } catch (IOException e) {
            servidor.agregarLog("Error al crear HiloCliente: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        String mensaje;
        try {
            while ((mensaje = entrada.readLine()) != null) {
                if (mensaje.startsWith("MENSAJE|")) {
                    String[] partes = mensaje.split("\\|");
                    String remitente = partes[1];
                    String destinatario = partes[2];
                    String contenido = partes[3];
                    servidor.enviarMensajeA(destinatario, remitente + ": " + contenido);
                } else {
                    servidor.enviarMensajeATodos(mensaje);
                }
            }
        } catch (IOException e) {
            servidor.agregarLog("Error en HiloCliente: " + e.getMessage());
        } finally {
            servidor.removerCliente(this);
            cerrar();
        }
    }

    /**
     * Enviar mensaje.
     *
     * @param mensaje the mensaje
     */
    public void enviarMensaje(String mensaje) {
        salida.println(mensaje);
    }

    /**
     * Cerrar.
     */
    public void cerrar() {
        try {
            socket.close();
        } catch (IOException e) {
            servidor.agregarLog("Error al cerrar socket: " + e.getMessage());
        }
    }

    /**
     * Gets nombre usuario.
     *
     * @return the nombre usuario
     */
    public String getNombreUsuario() {
        return this.nombreUsuario;
    }

    /**
     * Gets socket.
     *
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }
}
