package Servidor;

import CuentaBancaria.CuentaBancaria;
import java.io.IOException;
import java.net.*;

/**
 *
 * @author Alvaro Merino
 */
public class HiloServidor extends Thread {
    private DatagramSocket socket;
    private DatagramPacket paquete, paquete2;
    private byte[] buffer, buffer2;
    private int puerto, longitud, saldo;
    private InetAddress dir;
    private String mensaje;
    private CuentaBancaria cuenta;

    //Constructor
    public HiloServidor(DatagramPacket paquete, CuentaBancaria cb, DatagramSocket socket) throws SocketException {
        this.socket = socket;
        this.cuenta = cb;
        this.paquete = paquete;
        this.buffer = new byte[256];
        this.buffer2 = new byte[256];
    }

    //métodos
    
    @Override
    public void run() {
        try {
            buffer = paquete.getData(); // datos
            puerto = paquete.getPort(); // puerto origen
            dir = paquete.getAddress(); // dirección IP
            longitud = paquete.getLength(); //longitud del mensaje
            mensaje = new String(buffer, 0, longitud); //texto del mensaje

            System.out.println("Eco recibido:" + dir + ":" + puerto + ", mensaje:  " + mensaje);
            int opc = Integer.parseInt(mensaje);

            switch (opc) {
                case 1: // Ingresar
                    //Lanzo acuse de recibo
                    mensaje = "¿De cuánto va a realizar el ingreso?";
                    mandarMensaje(mensaje);
                    //espero respuesta
                    socket.receive(paquete);
                    buffer = paquete.getData(); //obtengo datos
                    puerto = paquete.getPort(); //obtengo puerto origen
                    dir = paquete.getAddress(); //obtengo dir IP
                    longitud = paquete.getLength(); //longitud del mensaje
                    mensaje = new String(buffer, 0, longitud); //texto del mensaje

                    System.out.println("Eco recibido:" + dir + ":" + puerto + ", mensaje:  " + mensaje);
                    saldo = Integer.parseInt(mensaje);

                    cuenta.ingresar(saldo);
                    System.out.println("Su saldo es de " + cuenta.getSaldo() + " €");

                    //Lanzo acuse de recibo
                    mensaje = "Ingreso realizado correctamente. Saldo: " + cuenta.getSaldo();
                    mandarMensaje(mensaje);

                    break;

                case 2: // Retirar
                    //Lanzo acuse de recibo
                    mensaje = "¿De cuánto va a realizar el ingreso?";
                    mandarMensaje(mensaje);
                    //espero respuesta
                    socket.receive(paquete);
                    buffer = paquete.getData(); //obtengo datos
                    puerto = paquete.getPort(); //obtengo puerto origen
                    dir = paquete.getAddress(); //obtengo dir IP
                    longitud = paquete.getLength(); //longitud del mensaje
                    mensaje = new String(buffer, 0, longitud); //texto del mensaje

                    System.out.println("Eco recibido:" + dir + ":" + puerto + ", mensaje:  " + mensaje);
                    saldo = Integer.parseInt(mensaje);

                    if (cuenta.getSaldo() > saldo) {
                        cuenta.retirar(saldo);
                        System.out.println("Su saldo es de " + cuenta.getSaldo() + " €");

                        //Lanzo acuse de recibo
                        mensaje = "Retirada realizada correctamente. Saldo: " + cuenta.getSaldo();
                        mandarMensaje(mensaje);
                    } else {
                        System.out.println("No tiene Saldo suficiente: " + cuenta.getSaldo() + " €");
                    }

                    break;

                case 3: // Consultar
                    //Lanzo acuse de recibo
                    mensaje = "Su saldo es de " + cuenta.consultar() + " €";
                    mandarMensaje(mensaje);

                    break;

            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * 
     * @param mensaje
     * @throws UnknownHostException
     * @throws IOException 
     */
    private void mandarMensaje(String mensaje) throws UnknownHostException, IOException {
        // lo convierte a vector de bytes
        buffer2 = mensaje.getBytes();
        //construimos el paquete, y especificamos el destino.
        //Creamos el datagrama que será enviado por el socket.
        paquete2 = new DatagramPacket(buffer2, mensaje.length(), dir, puerto);

        System.out.println("Lanzamos al cliente " + dir.getHostAddress() + " una confirmación.");
        socket.send(paquete2); //envio datagrama
    }
}
