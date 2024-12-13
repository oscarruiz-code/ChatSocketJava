package org.example;

import java.io.*;
import java.net.*;
import java.util.*;


/**
 * The type Servidor.
 * @author pix4ke
 * @version 1.0
 */
public class Servidor implements Runnable {
    private int puerto;
    private Set<HiloCliente> clientes = new HashSet<>();
    private VentanaS ventanaS;

    public Servidor(int puerto, VentanaS ventanaS) {
        this.puerto = puerto;
        this.ventanaS = ventanaS;
    }

    @Override
    public void run() {
        iniciar();
    }

    /**
     * Iniciar.
     */
    public void iniciar() {
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            ventanaS.agregarLog("Inicializando Servidor ... [OK]");

            while (true) {
                Socket socket = servidor.accept();
                HiloCliente cliente = new HiloCliente(socket, this);
                clientes.add(cliente);
                new Thread(cliente).start();
                enviarListaUsuarios();
            }
        } catch (IOException e) {
            agregarLog("Error en el servidor: " + e.getMessage());
        }
    }

    /**
     * Enviar mensaje a todos.
     *
     * @param mensaje the mensaje
     */
    public void enviarMensajeATodos(String mensaje) {
        for (HiloCliente cliente : clientes) {
            cliente.enviarMensaje(mensaje);
        }
    }

    /**
     * Enviar mensaje a.
     *
     * @param destinatario the destinatario
     * @param mensaje      the mensaje
     */
    public void enviarMensajeA(String destinatario, String mensaje) {
        for (HiloCliente cliente : clientes) {
            if (cliente.getNombreUsuario().equals(destinatario)) {
                cliente.enviarMensaje(mensaje);
                break;
            }
        }
    }

    /**
     * Notificar conexion.
     *
     * @param mensaje the mensaje
     */
    public void notificarConexion(String mensaje) {
        ventanaS.agregarLog(mensaje);
    }

    /**
     * Remover cliente.
     *
     * @param cliente the cliente
     */
    public void removerCliente(HiloCliente cliente) {
        clientes.remove(cliente);
        notificarConexion("El cliente " + cliente.getNombreUsuario() + " ha abandonado el chat.");
        enviarListaUsuarios();
    }

    /**
     * Enviar lista usuarios.
     */
    public void enviarListaUsuarios() {
        StringBuilder usuarios = new StringBuilder("USUARIOS_CONECTADOS:");
        for (HiloCliente cliente : clientes) {
            usuarios.append(cliente.getNombreUsuario()).append(",");
        }
        enviarMensajeATodos(usuarios.toString());
    }

    /**
     * Agregar log.
     *
     * @param mensaje the mensaje
     */
    public void agregarLog(String mensaje) {
        ventanaS.agregarLog(mensaje);
    }

    /**
     * Numero clientes conectados int.
     *
     * @return the int
     */
    public int numeroClientesConectados() {
        return clientes.size();
    }
}
