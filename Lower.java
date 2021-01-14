
public class Lower extends MiniServer {

	public Lower(int p,int s) {
		super(p,s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void transfrom() {
		result = input.toLowerCase();
		
	}
	public static void main(String arg[]) {
		Lower lo = new Lower(7560,7537);
		//code that will close sockets on shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		        lo.ds.close();
		        lo.ds2.close();
		    }
		});
			while(true) {
				//loop that checks if there is input and if input i recieved applies the transformation on the input and 
				//sends out the output
					lo.recieve();
					if(lo.on) {
						lo.transfrom();
						lo.send();
					}
			
			
		}
			
	}

}
