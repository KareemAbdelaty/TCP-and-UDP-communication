
public class Reverse extends MiniServer{

	public Reverse(int p,int s) {
		super(p,s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void transfrom() {
		result = new StringBuilder(input).reverse().toString();
		
	}
	public static void main(String arg[]) {
		Reverse reverse = new Reverse(7532,7535);
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		        reverse.ds.close();
		        reverse.ds2.close();
		    }
		});
		while(true) {
			try {
				//loop that checks if there is input and if input i recieved applies the transformation on the input and 
				//sends out the output
				reverse.recieve();
				if(reverse.on) {					
				reverse.transfrom();
				reverse.send();
				}

			}catch(Exception e){
				break;
				
			}
			
			
			
		}
			
	}

}
