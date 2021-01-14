
public class Echo extends MiniServer {

	public Echo(int p,int s) {
		super(p,s);
	}



	@Override
	public void transfrom() {
		result = input;
		
	}
	public static void main(String arg[]) {
		//intialise microserver 
		Echo echo = new Echo(7531,7534);
		//code that will close sockets on shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		        echo.ds.close();
		        echo.ds2.close();
		    }
		});
		while(true) {
			try {
				//loop that checks if there is input and if input i recieved applies the transformation on the input and 
				//sends out the output
				echo.recieve();
				if(echo.on) {
					echo.transfrom();
					echo.send();
				}

			}catch(Exception e){
				break;
				
			}
			
			
			
		}
			
	}

}
