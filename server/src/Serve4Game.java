/**
 * @author Dan Han, Weiqi Zhang
 * Main method of the server
 */
import java.util.*;
import java.text.*;
public class Serve4Game {
	
	static public ServerFrame server = new ServerFrame();
	static int roll1, roll2, roll3, roll4;
	static boolean act1=false,act2 = false;
	/*
	 * Main method
	 */
	public static void main(String[] args){
		start:
		while(true){
			int portNum1,portNum2;
			server.log("Please input port number 1 and 2.");
			while(true){	//get port number from the panel
				if(server.flagPort){
					portNum1 = server.portNum1;
					portNum2 = server.portNum2;
					break;
				}
				else
					continue;
			}
			server.log("Server is listening port "+Integer.toString(portNum1)+" and "+
					Integer.toString(portNum2));
			ServerConnect player1 = new ServerConnect(portNum1);
			ServerConnect player2 = new ServerConnect(portNum2);
			boolean flag1;
			boolean flag2;
			flag1 = player1.create();	//build connection with client
			flag2 = player2.create();	//build connection with client
			
			//build new plane for clients
			Plane Board1 = new Plane();	
			Plane Board2 = new Plane();
			String Name1="", Name2="";
			int win1 = 0,win2 = 0,lose1 = 0,lose2 = 0;
			float rate1 = 0,rate2 = 0;
			NumberFormat format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(2);
			
			//Game Start
			try{
			outer:
			while(flag1&&flag2){
				server.log("Game Start!");
				Board1.BoardInitial();
				Board2.BoardInitial();
				roll1 = 0;
				roll2 = 0;
				roll3 = 0;
				roll4 = 0;
				int NumOfPlayer1 , NumOfPlayer2 ;
				Random rand=new Random();
				NumOfPlayer1 =rand.nextInt(2)+1;
				ServerGameStart NewStart = new ServerGameStart();
				if(NumOfPlayer1 == 1){
					NumOfPlayer2 = 2;
					act1 = true;
					act2 = false;
				}
				else{
					NumOfPlayer2 = 1;
					act1 = false;
					act2 = true;
				}
				Name1 = player1.UName;
				win1 = player1.win;
				lose1 = player1.lose;
				rate1 = player1.rate;
				Name2 = player2.UName;
				win2 = player2.win;
				lose2 = player2.lose;
				rate2 = player2.rate;
				
				//send user information to client
				player1.SendToSocket("You are player "+NumOfPlayer1);
				player2.SendToSocket("You are player "+NumOfPlayer2);
				player1.SendToSocket("Info,"+Name1+","+Name2+","+rate1+","+rate2+","+win1+","+win2+","+lose1+","+lose2);
				player2.SendToSocket("Info,"+Name2+","+Name1+","+rate2+","+rate1+","+win2+","+win1+","+lose2+","+lose1);
				boolean WinOrNot = false;
			
				inner:
				while(!WinOrNot){
					if(act1){
						NewStart.Protocol(Board1, player1, player2);
						WinOrNot = Board1.WinerOrNot()||Board2.WinerOrNot();
						continue inner;
					}
					else if(act2){
						NewStart.Protocol(Board2, player2, player1);
						WinOrNot = Board1.WinerOrNot()||Board2.WinerOrNot();
						continue inner;
					}
				}
				
				//Update user data
				if(Board1.WinerOrNot()){
					player1.win++;
					player2.lose++;
					player1.time = System.currentTimeMillis();
					player2.time = System.currentTimeMillis();
				}
				else if(Board2.WinerOrNot()){
					player2.win++;
					player1.lose++;
					player1.time = System.currentTimeMillis();
					player2.time = System.currentTimeMillis();
				}
				player1.RefreshInfo();
				player2.RefreshInfo();
				player2.sort();
			
				//send new user information at the end of the game
				String r1,r2,fr,sr,tr;
				r1 = format.format(player1.rate*100);
				r2 = format.format(player2.rate*100);
				fr = format.format(player2.FirstRate);
				sr = format.format(player2.SecondRate);
				tr = format.format(player2.ThirdRate);
				player1.SendToSocket("newInfo,"+player1.UName+","+player1.win+","+player1.lose+","+r1+","+
					player2.FirstName+","+player2.SecondName+","+player2.ThirdName+","+
					fr+","+sr+","+tr); 
				player2.SendToSocket("newInfo,"+player2.UName+","+player2.win+","+player2.lose+","+r2+","+
					player2.FirstName+","+player2.SecondName+","+player2.ThirdName+","+
					fr+","+sr+","+tr);
				String re1 = "re1",re2 = "re2";
				while(!re1.trim().equals(null)&&!re2.trim().equals(null)){
					re1 = player1.ReceiveFromSocket();
					re2 = player2.ReceiveFromSocket();
					try{
						if(re1.trim().equals("restart")&&re2.trim().equals("restart")){//restart game
							player1.SendToSocket("restart");
							player2.SendToSocket("restart");
							server.log("Game restart!");
							continue outer;
						}
						else if(re1.trim().equals("exit")||re2.trim().equals("exit")){//exit game
							player1.SendToSocket("exit");
							player2.SendToSocket("exit");
							server.log("Game Over! The connection is reset");
							break outer;
						}
					}
					catch(NullPointerException e){
						break outer;
					}
				}
			}
		}
			catch(NullPointerException e){
				//connection reset, restart the server
				player1.destroy();
				player2.destroy();
				server.flagPort=false;
				ServerConnect.userOnline.removeAllElements();
				server.txtPort1.setText("");
				server.txtPort2.setText("");
				server.btnSave.setEnabled(true);
				server.txtPort1.setEditable(true);
				server.txtPort2.setEditable(true);
				server.taLog.setText("");
				server.log("Disconnect with players. Connection reset.");
				continue start;
			}
			

			//connection reset, restart the server
			player1.destroy();
			player2.destroy();
			server.flagPort=false;
			ServerConnect.userOnline.removeAllElements();
			server.txtPort1.setText("");
			server.txtPort2.setText("");
			server.btnSave.setEnabled(true);
			server.txtPort1.setEditable(true);
			server.txtPort2.setEditable(true);
			server.taLog.setText("");
		}
	}
}
