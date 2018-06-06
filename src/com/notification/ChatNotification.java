package com.notification;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ChatNotification {
	
	private PrintStream ps;
	private Socket cliente;
	private int idChat;
	
	public ChatNotification(Socket cliente) {
		try {
			this.cliente = cliente;
			ps = new PrintStream(cliente.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PrintStream getPs() {
		return ps;
	}

	public void setPs(PrintStream ps) {
		this.ps = ps;
	}

	public int getIdChat() {
		return idChat;
	}

	public void setIdChat(int idChat) {
		this.idChat = idChat;
	}

	public Socket getCliente() {
		return cliente;
	}

	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}
}
