/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CuentaBancaria;

/**
 *
 * @author Alvaro Merino
 */
public class CuentaBancaria {
    private int saldo;

    public CuentaBancaria(int saldo) {
        this.saldo = saldo;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
    
    public synchronized void ingresar(int cantidad){
        this.saldo=saldo+cantidad;
    }
    public synchronized void retirar(int cantidad){
        this.saldo=saldo-cantidad;
    }
    public synchronized int consultar(){
        return saldo;
    }
    
    
    
}
