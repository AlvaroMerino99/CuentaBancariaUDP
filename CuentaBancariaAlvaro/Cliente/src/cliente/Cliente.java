/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author Alvaro Merino
 */
public class Cliente {
    private String mensaje;
    private BufferedReader Teclado = new BufferedReader(new InputStreamReader(System.in));
    private DatagramSocket socket;
    private DatagramPacket paquete, paquete2;
    private byte[] buffer2 = new byte[256];
    private int opcion, dinero;
    private boolean correcto;
    private final Scanner teclado = new Scanner(System.in);
    
/**
 * 
 */
    public void ejecutarCliente() {
        int puerto, longitud;
        InetAddress direccion;
        paquete2 = new DatagramPacket(buffer2, 256);
        do {
            try {
                //puerto origen del socket: el que encuentre libre.
                socket = new DatagramSocket();

                System.out.println("Puerto de origen reservado por el cliente: " + socket.getLocalPort());

                System.out.println("¿Qué desea hacer?");
                System.out.println("\t 1. INGRESAR.");
                System.out.println("\t 2. RETIRAR.");
                System.out.println("\t 3. CONSULTAR.");
                System.out.println("\t 4. SALIR.");
                System.out.println("Introduzca una opción: ");
                opcion = teclado.nextInt();
                switch (opcion) {
                    case 1:
                        mandarMensaje(String.valueOf(opcion));

                        //Acuse de recibo
                        socket.receive(paquete2);
                        buffer2 = paquete2.getData(); //obtengo datos
                        puerto = paquete2.getPort(); //obtengo puerto origen
                        direccion = paquete2.getAddress(); //obtengo direccion IP
                        longitud = paquete2.getLength(); //longitud del mensaje
                        mensaje = new String(buffer2, 0, longitud); //texto del mensaje
                        System.out.println("El servidor:" + direccion + ":" + puerto + " >  ha confirmado el mensaje anterior: " + mensaje);

                        correcto = false;
                        do {
                            mensaje = Teclado.readLine();

                            try {
                                dinero = Integer.parseInt(mensaje);
                                correcto = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Introduzca un número");
                            }

                        } while (!correcto);

                        mandarMensaje(String.valueOf(dinero));

                        //Acuse de recibo
                        socket.receive(paquete2);
                        buffer2 = paquete2.getData(); //obtengo datos
                        puerto = paquete2.getPort(); //obtengo puerto origen
                        direccion = paquete2.getAddress(); //obtengo direccion IP
                        longitud = paquete2.getLength(); //longitud del mensaje
                        mensaje = new String(buffer2, 0, longitud); //texto del mensaje
                        System.out.println("El servidor:" + direccion + ":" + puerto + " >  ha confirmado el mensaje anterior: " + mensaje);

                        break;

                    case 2:
                        mandarMensaje(String.valueOf(opcion));

                        //Acuse de recibo
                        socket.receive(paquete2);
                        buffer2 = paquete2.getData(); //obtengo datos
                        puerto = paquete2.getPort(); //obtengo puerto origen
                        direccion = paquete2.getAddress(); //obtengo direccion IP
                        longitud = paquete2.getLength(); //longitud del mensaje
                        mensaje = new String(buffer2, 0, longitud); //texto del mensaje
                        System.out.println("El servidor:" + direccion + ":" + puerto + " >  ha confirmado el mensaje anterior: " + mensaje);

                        correcto = false;
                        do {
                            mensaje = teclado.nextLine();
                            try {
                                dinero = Integer.parseInt(mensaje);
                                correcto = true;
                            }catch (NumberFormatException e) {
                                System.out.println("Introduzca un número");
                            }
                        } while (!correcto);

                        mandarMensaje(String.valueOf(dinero));
                        //Acuse de recibo
                        paquete2 = new DatagramPacket(buffer2, 256);
                        socket.receive(paquete2);
                        buffer2 = paquete2.getData(); //obtengo datos
                        puerto = paquete2.getPort(); //obtengo puerto origen
                        direccion = paquete2.getAddress(); //obtengo direccion IP
                        longitud = paquete2.getLength(); //longitud del mensaje
                        mensaje = new String(buffer2, 0, longitud); //texto del mensaje
                        System.out.println("El servidor:" + direccion + ":" + puerto + " >  ha confirmado el mensaje anterior: " + mensaje);
                        break;

                    case 3:
                        mandarMensaje(String.valueOf(opcion));
                        //Acuse de recibo
                        socket.receive(paquete2);
                        buffer2 = paquete2.getData(); //obtengo datos
                        puerto = paquete2.getPort(); //obtengo puerto origen
                        direccion = paquete2.getAddress(); //obtengo direccion IP
                        longitud = paquete2.getLength(); //longitud del mensaje
                        mensaje = new String(buffer2, 0, longitud); //texto del mensaje
                        System.out.println("El servidor:" + direccion + ":" + puerto + " >  ha confirmado el mensaje anterior: " + mensaje);
                        break;

                    case 4:
                        System.out.println("ADIOS...");
                        break;
                    default:
                        System.out.println("Introduzca una opción correcta: ");
                        break;
                }
            } catch (SocketException se) {
            } catch (IOException ioe) {
            }
        } while (opcion != 4);
        System.out.println("Fin del programa...");
    }
/**
 * 
 * @param mensaje
 * @throws UnknownHostException
 * @throws IOException 
 */
    private void mandarMensaje(String mensaje) throws UnknownHostException, IOException {
        // lo convierte a vector de bytes
        byte[] buffer = mensaje.getBytes();
        //ahora construyo el paquete, especifico destino
        //Ahora creamos el datagrama que será enviado por el socket 'socket'.
        paquete = new DatagramPacket(buffer, mensaje.length(), InetAddress.getLocalHost(), 50000);
        socket.send(paquete); //envio datagrama
    }
}