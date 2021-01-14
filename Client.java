//kareem abdelaty
//30075331
//CPSC 441 A2

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Client {
	public static void main(String args[]) throws Exception {
		int port = Integer.parseInt(args[0]); // get port to contact Master server at
		String ip = args[1];
		boolean created = false;
		Socket s =null;
		DataOutputStream dos =null;
		DataInputStream dis =null;
		String result = "";
		String input = "";
		//loop to create socket and input/output streams for the socket 
		//keep trying until succesful
		while(!created) {
			try {
			s = new Socket(ip,port);
			s.setSoTimeout(5000);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
			created  = true;
			}catch(Exception e) {
				System.out.println(e);
				
			}
		}

		System.out.println("enter word or sentence to be transformed");
		//create buffered reader object to read input from user
		BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in)); 
		//keep trying to read input from user until succesful
		boolean correct = false;
		while(!correct) {
			try {
				input = reader.readLine();
				if(input.length() == 0) {
					throw new Exception();
				}
				correct = true;
			}catch(Exception e) {
				System.out.println("Invalid input please try again");
			}
		}
		boolean succesful = false;
		//send input from user to Master server
		//keep trying until succesfull
		while(!succesful) {
			try {
			dos.writeUTF(input);
			dos.flush();
			succesful =true;
			}catch(Exception e) {
				
			}
		}
		
		System.out.println("enter required transformations as number: 1 for echo, 2 for reverse, 3 for upper \n "
				+ "4 for Lower, 5 for ceasar , 6 for mystery \n 7 to obtain result and quit.");
		boolean terminate  = false;
		String transform ="";
		//read transformations input form user
		//keep prompting user for input until user gives termination input (input contains a 7)
		while(!terminate) {
			try {
				transform = reader.readLine();
			}catch(Exception e) {
				System.out.println("Incorrect input");
			}
			//check if terminate instruction is present
			if(transform.equals("7")) {
				terminate = true;
				System.out.println("proccesing output");
			}
			else if(transform.contains("7")) {
				terminate = true;
				transform = transform.split("7")[0];
				System.out.println("proccesing output");
				transform += "7";
			}
			//send transformaiton input to Master Server
			boolean succes = false;
			while(!succes) {
				try {
				dos.writeUTF(transform);
				dos.flush();
				succes =true;
				}catch(Exception e) {
					
				}
			}
			if(terminate != true) {
				System.out.println("enter further transformations.");
			}
			
			
		}
		succesful =false;
		//receive output from Master and display to user
		while(!succesful) {
			try {
				result = dis.readUTF();
				succesful = true;
			}catch(SocketTimeoutException e) {
				succesful = false;
			}catch(Exception e) {
				
			}
		}
		System.out.println("The output is : " + result);
		try {
		dis.close();
		dos.close();
		s.close();
		}catch(Exception e) {
		}
		
	}
	
}


