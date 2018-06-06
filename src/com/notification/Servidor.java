package com.notification;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//Créditos à caelum, link do artigo: https://www.caelum.com.br/apostila-java-orientacao-objetos/apendice-sockets/#soluo-do-sistema-de-chat
//Algumas mudanças foram feitas no código original para trabalhar com um sistema interno de multicast
public class Servidor {

    public static void main(String[] args) throws IOException {
        new Servidor(12345).executa();
    }

    private int porta;
    private List<ChatNotification> clientes;

    public Servidor (int porta) {
        this.porta = porta;
        this.clientes = new ArrayList<ChatNotification>();
    }

    public void executa () throws IOException {
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta 12345 aberta!");

        while (true) {
            Socket cliente = servidor.accept();
            System.out.println("Nova conexão com o cliente " +     
                cliente.getInetAddress().getHostAddress()
            );

            ChatNotification notification = new ChatNotification(cliente);
            this.clientes.add(notification);

            TrataCliente tc = 
                    new TrataCliente(cliente, this);
            new Thread(tc).start();
        }

    }

    public void distribuiMensagem(String msg, int idChat) {
        for (ChatNotification cliente : this.clientes) {
        	if(cliente.getIdChat() == idChat) {
        		cliente.getPs().println(msg);
        	}           
        }
    }
    
    public void identificaCliente(Socket client, int idChat) {
        for (ChatNotification cliente : this.clientes) {
            if(client == cliente.getCliente()) {
            	cliente.setIdChat(idChat);
            }
        }
    }
}
