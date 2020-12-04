package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;

/**
	Este hilo representa la conexi�n del servidor con el cliente 
*/
public class ConnectionChatSer extends Thread{
	
	private int index;
	private Server server;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private BigInteger keyp;

	/**
	 *Constructor del hilo. Empieza el Diffie-Hellman
	 */
	public ConnectionChatSer(Server server, Socket socket, int key, int index) throws Exception {

		this.server = server;
		this.socket = socket;
		out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out.println(key);
		keyp = new BigInteger(in.readLine());

	}
	
	/**
	 * @Method M�todo responsable de devolver la llave con la primera potencia
	 * @Return: BigInteger llave
	 */
	public BigInteger getKey() {
		return keyp;
	}
	
	/**
	 * @Method M�todo responsable de devolver el n�mero de conexi�n 
	 * @Return: int index de conexi�n del cliente
	 */
	public int getIndex() {
		return index;
	}
	
	
	/**
	 * @Method Run del hilo. Es responsable de leer los mensajes del cliente
	 */
	@Override
	public synchronized void run() {
		
		
		while (socket.isConnected()) {

			String data = "";
			try {
				while (in.ready()) {
					data = in.readLine();
					System.out.println(data);
					server.processChat(data);
					
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		
	}
	
	/**
	 * @Method M�todo responsable de escribir los mensajes al cliente
	 */
	public void write(String message) {
		out.println(message);
	}

}
