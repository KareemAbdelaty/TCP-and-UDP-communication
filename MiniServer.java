//Kareem Abdelaty
//30075331
//A2
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
//Abstract class to deifne defintion for Miniservers
public abstract class MiniServer {
	public String result;
	public String input;
	public int s;
	InetAddress ip ;
    byte[] buf = new byte[1024];
    DatagramSocket ds;
    DatagramSocket ds2;
    boolean on;
    //constructer that intialises sockets
	public MiniServer(int sp,int rp) {
		boolean done = false;
		s = sp;
		while(!done) {
			try {
			    ds = new DatagramSocket(rp); 
			    ds.setSoTimeout(5000); 
			    ds2 = new DatagramSocket(); 
			    ds2.setSoTimeout(5000);
			    ip = InetAddress.getByName("localhost");
			    done = true;
			    on = false;
			}catch(Exception e) {
				done = false;
			}
		}
	}
	//function that recieves udp packets on the given port. 
	public void recieve() {
		boolean s = false;
		while(!s) {
			try {
		    DatagramPacket dp2 = new DatagramPacket(buf, 1024);  
		    ds.receive(dp2);  
		    input = new String(dp2.getData(), 0, dp2.getLength());
		    System.out.println(input);
		    System.out.println("recieved input " + input);
		    s= true;
		    on = true;
		    
			}catch(SocketTimeoutException e) {
				System.out.println("timeout");
				s= false;
				on = false;
			}catch(Exception e) {
				s= false;
				on = false;
			}
			
		
		
	}

		
	}
	//function that sends the output back to the master server it assumes the master server is on localhost
	public void send() {
		boolean s = false;
		while(!s) {
			try {
		    //System.out.println(ip.getHostAddress());   
		    DatagramPacket dp = new DatagramPacket(result.getBytes(), result.length(), ip, this.s);  
		    System.out.println(result);
		    ds2.send(dp);
		    System.out.println("sending result " + result);
		    s= true;
		    on = false;
			}catch(SocketTimeoutException e) {
				System.out.println("timeout");
				s= false;
				on =true;
			}catch(Exception e) {
				s = false;
				on = true;
			}
			
		
		
	}
	}

	//abstract function that must be defined by each microserver
	abstract public void transfrom() ;
}
