package Servidor;

import CuentaBancaria.CuentaBancaria;
import java.io.IOException;
import java.net.*;
/**
 *
 * @author Alvaro Merino
 */
public class ServidorBanco {
    public static void main(String[]args) throws IOException{
        ServidorBanco servidorBanco = new ServidorBanco();
        servidorBanco.ejecutarServidor();
    }
    
    private CuentaBancaria cuenta;
    private HiloServidor hiloServidor;

    public ServidorBanco() {
        this.cuenta = new CuentaBancaria(1000);
    }
    void ejecutarServidor() throws IOException{
        try{
            byte[] buffer = new byte[256];
            DatagramSocket socket = new DatagramSocket(50000); // puerto de eco
            DatagramPacket paquete;
            System.out.println("EMPEZAMOS CON EL SERVIDOR BANCARIO");
            while (true) {
                System.out.println("SU SALDO ES DE  " + cuenta.getSaldo() + "€");
                paquete = new DatagramPacket(buffer, 256);
                socket.receive(paquete); //me quedo esperando
                hiloServidor = new HiloServidor(paquete, cuenta, socket);
                hiloServidor.start();
                try {
                    Thread.sleep(500);
                }catch (InterruptedException ex) {
                }
            }            
        }catch (SocketException soe) {
            System.out.println("Error: "+soe.getMessage());
        }
    }   
}