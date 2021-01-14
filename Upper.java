
public class Upper extends MiniServer{

	public Upper(int p,int s) {
		super(p,s);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void transfrom() {
		result = input.toUpperCase();
		
	}
	public static void main(String arg[]) {
		Upper up = new Upper(7533,7536);
		//code that will close sockets on shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		        up.ds.close();
		        up.ds2.close();
		    }
		});
		while(true) {
			try {
				//loop that checks if there is input and if input i recieved applies the transformation on the input and 
				//sends out the output
				up.recieve();
				if(up.on) {
					up.transfrom();
					up.send();
				}

			}catch(Exception e){
				System.out.println(e);
				break;
				
			}
			
			
			
		}
			
	}

}
