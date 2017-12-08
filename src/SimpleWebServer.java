import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.EncoderMotor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
 
public class SimpleWebServer {
  public static final int PORT = 80;
  private ServerSocket ss;
  private Socket sock;
  private static RMIRegulatedMotor rightMotor;
  private static RMIRegulatedMotor leftMotor;
  private HttpUrlSplitter spliter;
 
  public SimpleWebServer() throws IOException {
    ss = new ServerSocket(PORT);
    spliter = new HttpUrlSplitter();
    
  }
 
  public void run() throws IOException {
    for (;;) {
      sock = ss.accept();
      InputStream is = sock.getInputStream();
      OutputStream os = sock.getOutputStream();
 
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      PrintStream ps = new PrintStream(os);
 
      for (;;) {
        String cmd = br.readLine();
        if (cmd == null)
          break;
        String reply = execute(cmd);
        if (reply != null)
          ps.println(reply);
        else {
          br.close();
          ps.close();
          break;
        }
      }
 
    }
  }
 
  public String execute(String cmd) throws RemoteException {
	  
	Command command = spliter.split(cmd);
    System.out.println("cmd: " + cmd);
   
    if("GET".equals(command.getHttpMethod())) {
    	int speed = command.getSpeed();
    	leftMotor.setSpeed(speed);
    	rightMotor.setSpeed(speed);
    	if ("forward".equals(command.getMove())) {
    		leftMotor.forward();
    		rightMotor.forward();  
    	} else if ("backward".equals(command.getMove())) {
    		leftMotor.backward();
    		rightMotor.backward();
    	} else if ("left".equals(command.getMove())) {
    		leftMotor.backward();
    		rightMotor.forward();
    	} else if ("right".equals(command.getMove())) {
    		 leftMotor.forward();
    		 rightMotor.backward();
    	} else if ("stop".equals(command.getMove())) {
    		leftMotor.stop(true);
    		rightMotor.stop(true);
    	}
    	return "HTTP/1.1 200 OK\r\n\r\nOK\r\n";		
    	}
    return null;
}
  
 
  public static void main(String[] args) throws IOException, MalformedURLException, NotBoundException {
	  System.out.println("Server started...");
	  
	  Sound.beep();
	  
	  RemoteEV3 ev3 = new RemoteEV3("192.168.1.136");
	  leftMotor = ev3.createRegulatedMotor("B",  'L');
      rightMotor = ev3.createRegulatedMotor("C",  'L'); 
      
      new SimpleWebServer().run();
	  
      leftMotor.close();
 	  rightMotor.close();
  }
}