package States;

import java.io.IOException;

import Communications.*;
import Messages.*;


public class Waiting extends State{
	public static final long timeout=1000;
	public State process(String input, TCP tcp, UDPSender us,Message udpMessage,Message tcpMessage,long timeEnteredState){
		
		if(tcp.getActive()==true){
			System.out.println("\rConnection established");
			return new Connected();
		}
		else if(input.startsWith(":exit")){
			try {
				tcp.close();
			} catch (IOException e) {}
			System.out.println("\rDisconnecting");
			return new Disconnected();
		}
		else if(System.currentTimeMillis()-timeEnteredState>timeout){
			System.out.println("\rGot no response");
			return new Disconnected();
		}
		else{
			return this;
		}
	}
}
