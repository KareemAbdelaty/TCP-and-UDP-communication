
public class Ceaser extends MiniServer {

	public Ceaser(int p, int s) {
		super(p,s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void transfrom() {
	    StringBuilder strBuilder = new StringBuilder();
	    char c;
	    for (int i = 0; i < input.length(); i++)
	    {
	        c = input.charAt(i);
	        if (Character.isLetter(c))
	        {
	        c = (char) (input.charAt(i) + 2);
	        if ((Character.isLowerCase(input.charAt(i)) && c > 'z')
	            || (Character.isUpperCase(input.charAt(i)) && c > 'Z'))

	            c = (char) (input.charAt(i) - (26 - 2));
	        }
	        strBuilder.append(c);
	    }
	    result =  strBuilder.toString();
	    
		
	}
	public static void main(String arg[]) {
		Ceaser ces  = new Ceaser(7601,7610);
		//code that will close sockets on shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		        ces.ds.close();
		        ces.ds2.close();
		    }
		});
		while(true) {
			//loop that checks if there is input and if input i recieved applies the transformation on the input and 
			//sends out the output
				ces.recieve();
				if(ces.on) {				
				ces.transfrom();
				ces.send();
				}

			
			
			
		}
		
			
	}

}
