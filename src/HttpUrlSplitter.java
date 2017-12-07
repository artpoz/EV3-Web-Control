
public class HttpUrlSplitter {
	public Command split(String command) {
		
		Command cmd = new Command();		
		String[] tokens = command.split(" "); 
		if (tokens.length > 1 && tokens[0].equals("GET")) {
			cmd.setHttpMethod("GET");
	    	String[] speedlist= tokens[1].split("=");
	    	if (speedlist.length > 1)
	    		cmd.setSpeed(Integer.parseInt(speedlist[1]));
	    	else
	    		cmd.setSpeed(0);
	    	
	        String direction[] = tokens[1].split("\\?");
	        cmd.setMove(direction[0].substring(1));
	        System.out.println("dir:"+cmd.getMove());
	        System.out.println("speed:"+cmd.getSpeed());
	        System.out.println("method:"+cmd.getHttpMethod());
		}
		return cmd;
	}
}
