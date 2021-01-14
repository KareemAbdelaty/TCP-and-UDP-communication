
public class Mystery extends MiniServer{
	public Mystery(int p,int s) {
		super(p,s);
	}

	@Override
	public void transfrom() {
		result  = input.replaceAll("[AEIOUaeiou]", "");
		
	}
	public static void main(String arg[]) {
		Mystery mys = new Mystery(7602,7539);
		//code that will close sockets on shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		        mys.ds.close();
		        mys.ds2.close();
		    }
		});
		while(true) {
			try {
				//loop that checks if there is input and if input i recieved applies the transformation on the input and 
				//sends out the output
				mys.recieve();
				if(mys.on) {
					mys.transfrom();
					mys.send();
				}

			}catch(Exception e){
				break;
				
			}
			
			
			
		}
			
	}

}
