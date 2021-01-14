//kareem abdelaty
//30075331
//CPSC 441 A2
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
//Master server can only service one client at a time
public class Master{
	public static ServerSocket ss = null; 
	//sockets dedicate to receiving response from microserver
	public static DatagramSocket decho;
	public static DatagramSocket dreverse;
	public static DatagramSocket dup;
	public static DatagramSocket dlo;
	public static DatagramSocket dce;
	public static DatagramSocket dme; 
	//function to sends input to and recieves output from microserver and keeps trying until both
	//operations are succesfull
	public static String contactMicroserver(int port, String word,InetAddress ip,byte[] buf,String x, DatagramSocket d) {
		String w ="";
		boolean s = false;
		while(!s) {
			try {
			DatagramSocket	ds = new DatagramSocket(); 
			ds.setSoTimeout(5000);
			System.out.println(word);
 		    DatagramPacket dp = new DatagramPacket(word.getBytes(), word.length(), ip, port);  
		    ds.send(dp);
		    ds.close();
		    DatagramPacket dp2 = new DatagramPacket(buf, 1024); 
		    d.receive(dp2);  
		    w = new String(dp2.getData(), 0, dp2.getLength());    
		    System.out.println(w);
		    s= true;
			}catch(SocketTimeoutException e) {
				s =false;
				System.out.println(e);
				System.out.println("failed to contact microserver due timeout ," + x + "trying again");
			}catch(Exception e) {
				s =false;
				System.out.println(e);
				System.out.println("failed to contact microserver," + x + "trying again");

		
			}
			}
		return w;
		
	}
	//funciton that handles clients requests by recieving the input string from the client/ the required transformations
	//applies those trasformations to the input by contacting the correct microserver and returns the output to the client 
	private static void handle(Socket so) throws Exception {
		DataInputStream dis = null;
		DataOutputStream dos = null;
		InetAddress ip = InetAddress.getByName("localhost");
	    byte[] buf = new byte[1024];  
		String word ="";
		String input ="";
		String result ="";
		boolean succesful = false;
		so.setSoTimeout(5000);
		while(!succesful) {
			try {
				dis = new DataInputStream(so.getInputStream());
				succesful = true;
			}catch(Exception e) {
				
			}
		}
		succesful = false;
		while(!succesful) {
			try {
				word = dis.readUTF();
				succesful = true;
			}catch(Exception e) {
			
			}
		}
		System.out.println("recieved word: " + word);
		succesful =false;
		while(!succesful) {
			try {
				input = dis.readUTF();
				succesful = true;
			}catch(Exception e) {
		
				
			}
		}
		System.out.println("recieved transfromation: " + input);
		//sanatise input transformations by removing all characters except number 1 to 7
		input = input.replaceAll("[^1-7]", "");
		boolean done = false;
		while(!done) {
			System.out.println("7");
			if(input.equals("7")) {
				done = true;
				break;
			}
			if(input.contains("7")) {
				done = true;
				input = input.split("7")[0];
			}
			int inputs[] = new int[input.length()];
			char inputc[] = input.toCharArray();
			for(int i =0;i<input.length();i++) {
				inputs[i] = Integer.parseInt(Character.toString(inputc[i]));
			}
			//preform transformations
			for(int i =0;i<inputs.length;i++) {
	
				int transform = inputs[i];
				if(transform == 1) {
					word = contactMicroserver(7534,word,ip,buf," echo ",decho);
				}
				else if(transform ==2) {
					word = contactMicroserver(7535,word,ip,buf," reverse ",dreverse);
					}
				else if(transform == 3) {
					word =contactMicroserver(7536,word,ip,buf," upper ",dup);
				}
				else if(transform ==4) {
					word = contactMicroserver(7537,word,ip,buf," lower ",dlo);
				}
				else if(transform ==5) {
					word = contactMicroserver(7610,word,ip,buf," ceaser ",dce);
				}
				else if(transform ==6) {
					word =contactMicroserver(7539,word,ip,buf," remove vowels ",dme);
					
				}
			}
			dos = new DataOutputStream(so.getOutputStream());
			System.out.println("getting next input");
			succesful =false;
			input ="";
			//if no termination characters where received read the next set of transformations
			while((!succesful) && (!done)) {
				try {
					String line = dis.readUTF();	
					input += line;
					System.out.println("recieved input :"  + input);
					succesful = true;
				}catch(SocketTimeoutException e) 
				{
					succesful = false;
					System.out.println("failed to read input, trying again");
				}catch(Exception e) {
					succesful = false;

				}
			}
			input = input.replaceAll("[^1-7]", "");
			

			
		}
		System.out.println("done");
		result = word;	
		boolean tryi =false;
		//return output to client
		while(!tryi) {
			try {
		dos.writeUTF(result);
		dos.flush();
		tryi = true;
			}catch(SocketTimeoutException e) {
				tryi = false;
				System.out.println("trying again");
			}catch(Exception e) {
				tryi =false;
			}
		}
		dos.close();
		dis.close();
		so.close();			
		return;
		
		
	}
	public static void main(String arg[]) throws Exception {
		boolean succesfull = false;
		while(!succesfull) {
			//intialise all sockets
			try {
			decho = new DatagramSocket(7531);
			decho.setSoTimeout(5000);
			dreverse = new DatagramSocket(7532);
			dreverse.setSoTimeout(5000);
			dup= new DatagramSocket(7533);
			dup.setSoTimeout(5000);
			dlo = new DatagramSocket(7560);
			dlo.setSoTimeout(5000);
			dce = new DatagramSocket(7601);
			dce.setSoTimeout(5000);
			dme = new DatagramSocket(7602); 
			dme.setSoTimeout(5000);
			succesfull =  true;
			}catch(Exception e) {
				succesfull = false;
			}
		}
		//code that will close sockets on shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
				decho.close();
				dreverse.close();
				dup.close();
				dlo.close();
				dce.close();
				dme.close();
		    }
		});
		succesfull = false;
		//recieve and handle client connections
		while(!succesfull) {
			try {
				ss = new ServerSocket(Integer.parseInt(arg[0]));
				succesfull = true;
			} catch(Exception e){
				System.out.println(e);
			}
		}
		while(true) {
			try {
				Socket s = ss.accept();
				handle(s);
			}catch(Exception e) {
				
			}
		}
		
		
	}

}
