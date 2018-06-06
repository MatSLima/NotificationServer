package com.notification;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONObject;

//Créditos à caelum, link do artigo: https://www.caelum.com.br/apostila-java-orientacao-objetos/apendice-sockets/#soluo-do-sistema-de-chat
//Algumas mudanças foram feitas no código original para trabalhar com um sistema interno de multicast
public class TrataCliente implements Runnable {

    private Socket cliente;
    private Servidor servidor;

    public TrataCliente(Socket cliente, Servidor servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    public void run() {
        Scanner s;
		try {
			s = new Scanner(this.cliente.getInputStream());
			while (s.hasNextLine()) {
	        	String message = s.nextLine();
	        	String json = message.toString();
	        	JSONObject obj = new JSONObject(message);
	            int id = (Integer) obj.get("id");
	            try {
	            	message = (String) obj.get("message");
	            }catch(Exception e) {
	            	message= null;
	            }	        	
	        	System.out.println("Notificação do cliente: " + message);
	        	System.out.println("Json da notificação: " + json);
	        	if(message == null) {
	        		servidor.identificaCliente(this.cliente, id);
	        	} else {
	        		servidor.distribuiMensagem(json, id);
	        	}         
	        }
	        s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }

	public Socket getCliente() {
		return cliente;
	}

	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}
}