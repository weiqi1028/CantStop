/**
 * @author Dan Han, Weiqi Zhang
 * this class defines how client exchanges information with server
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class ClientGameStart {
	public static String userInformation;		// two users' information, displayed on the game board(Board class)
	private ClientConnect client;
	boolean act = false;
	ImageIcon red = new ImageIcon("src/image/red.jpg");
	ImageIcon blue = new ImageIcon("src/image/blue.jpg");
	JLabel actSlot[][];
	JLabel inactSlot[][];
	ImageIcon actIcon;
	ImageIcon inactIcon;
	JLabel[] actDice;
	JLabel[] inactDice;
	JTextField actTxt1;
	JTextField actTxt2;
	JTextField actTxt3;
	JTextField inactTxt1;
	JTextField inactTxt2;
	JTextField inactTxt3;
	JTextField actName;
	JTextField inactName;
	String actS = "";
	String inactS = "";
	boolean WinOrNot = false;
	public String WorN;
	
	/** 
	 * constructor
	 * @param client, Connect class
	 */
	public ClientGameStart(ClientConnect client){
		this.client=client;
	}
	
	/**
	 * the protocol method, describes how client exchange information with server
	 * @param player, indicates the role of the player
	 * @param board, Board class
	 */
	public void protocol(int player,Board board){             // if the user is player1
		if(player == 1){
			act = true;
			actIcon = red;
			inactIcon = blue;
			actSlot = board.slots1;
			inactSlot = board.slots2;
			actDice = board.RDice;
			inactDice = board.BDice;
			actTxt1 = board.rLine1;
			actTxt2 = board.rLine2;
			actTxt3 = board.rLine3;
			inactTxt1 = board.bLine1;
			inactTxt2 = board.bLine2;
			inactTxt3 = board.bLine3;
			actS = "Red's";
			inactS = "Blue's";
			actTxt1.setText("Game Start!");
			actTxt2.setText("You are P1");
			actTxt3.setText("Red's player");
			inactTxt2.setText("Game Start!");
			actName = board.rName;
			inactName = board.bName;
		}
		else if(player == 2){					// if the user is player2
			act = false;
			inactIcon = red;
			actIcon = blue;
			actSlot = board.slots2;
			inactSlot = board.slots1;
			inactDice = board.RDice;
			actDice = board.BDice;
			actTxt1 = board.bLine1;
			actTxt2 = board.bLine2;
			actTxt3 = board.bLine3;
			inactTxt1 = board.rLine1;
			inactTxt2 = board.rLine2;
			inactTxt3 = board.rLine3;
			actS = "Blue's";
			inactS = "Red's";
			actTxt1.setText("Game Start!");
			actTxt2.setText("You are P2");
			actTxt3.setText("Blue's player");
			inactTxt2.setText("Game Start!");
			inactName = board.rName;
			actName = board.bName;
		}
		
		String s = "";
		s= client.ReceiveFromSocket();
		userInformation = s;				// get two users' information from server
		String[] receive = null;
		receive = s.split(",");
		String result = receive[0];
		if(result.trim().equals("Info"));
		{
			actName.setText(receive[1]);
			inactName.setText(receive[2]);
		}
		outer:
		while(!WinOrNot){				// game flow
		s= client.ReceiveFromSocket();
		receive = null;
		receive = s.split(",");
		result = receive[0];
		if(result.trim().equals("current")){       // receive current state of the board from server
			int[] num = new int[11];
			for(int i = 0;i<11;i++)
				num[i] = Integer.parseInt(receive[i+1]);
			System.arraycopy(num, 0, board.current, 0, 11);
			for(int i = 0;i<11;i++)
				System.out.print(board.current[i]+" ");
		}
		
		inner1:
		while(act){			// user's turn
			inactTxt1.setText("");
			inactTxt2.setText(inactS + " player");
			inactTxt3.setText("");
			actTxt1.setText("Your turn");
			s = client.ReceiveFromSocket();
			receive = null;
			receive = s.split(",");
			result = receive[0];
			if(result.trim().equals("err")){	// "err" received from server, input again
				actTxt2.setText("Error!");
				actTxt3.setText("Try again!");
				board.ok.setEnabled(true);
				continue inner1;
			}
			else if(result.trim().equals("ack")){			// "ack" received from server, make moves
				int num1 = Integer.parseInt(receive[1]);
				int num2 = Integer.parseInt(receive[2]);
				board.ack(actSlot, num1, num2);
				actTxt2.setText("Roll Or Stop?");
				actTxt3.setText("");
				board.ok.setEnabled(false);
				board.roll.addActionListener(board.cmd);
				board.stop.addActionListener(board.cmd);
				continue inner1;
			}
			
			else if(result.trim().equals("stop")){			// "stop" received from server, place markers
				board.stop(actSlot, inactSlot, actIcon);
				s = client.ReceiveFromSocket();
				if(s.trim().equals("win")){			// "win" received from server, end this game
					WinOrNot = !WinOrNot;
					actTxt2.setText("You win!");
					actTxt1.setText("");
					actTxt3.setText("");
					inactTxt2.setText("");
					inactTxt1.setText("");
					inactTxt3.setText("");
					WorN = "You Win!!";
					break outer;
				}
				else if(s.trim().equals("ACK")){	// "ACK" received from server, another player's turn
					act = !act;
					actTxt2.setText("Change players");
					inactTxt2.setText("Change players");
					continue outer;
				}
				
			}
			else if(result.trim().equals("crap")){		// "crap" received from server, another player's turn
				for(int k = 0;k <5; k++)
					board.current[k] = Integer.parseInt(receive[k+1]);
				for(int k = 5;k<11; k++)
					board.current[k] = Integer.parseInt(receive[k+1]);
				board.crap(actSlot, board.current, actIcon);
				act = !act;
				actTxt2.setText("Crap out!");
				actTxt3.setText("Change players");
				continue outer;
			}
			else if(result.trim().equals("dice")){		// "dice" received from server, update dice area
				int num1 = Integer.parseInt(receive[1]);
				int num2 = Integer.parseInt(receive[2]);
				int num3 = Integer.parseInt(receive[3]);
				int num4 = Integer.parseInt(receive[4]);
				board.changeDice(actDice, num1, num2,num3,num4);
				board.ok.setEnabled(true);
				board.roll.removeActionListener(board.cmd);
				board.stop.removeActionListener(board.cmd);
				actTxt2.setText("Please choose");
				actTxt3.setText("two numbers");
				continue inner1;
			}
		}
		inner2:
		while(!act){		// another user's turn, the same as while(act)

			actTxt1.setText(inactS + " turn");
			board.ok.setEnabled(false);
			board.roll.setEnabled(false);
			board.stop.setEnabled(false);
			s = client.ReceiveFromSocket();
			receive = null;
			receive = s.split(",");
			result = receive[0];
			if(result.trim().equals("err")){
				inactTxt2.setText("Input error!");
				inactTxt3.setText("");
				board.ok.setEnabled(true);
				continue inner2;
			}
			else if(result.trim().equals("ack")){
				int num1 = Integer.parseInt(receive[1]);
				int num2 = Integer.parseInt(receive[2]);
				board.ack(inactSlot, num1, num2);
				inactTxt1.setText(inactS + " player");
				inactTxt2.setText("chose");
				inactTxt3.setText(receive[1] + " and " + receive[2]);
				continue inner2;
			}
			else if(result.trim().equals("stop")){
				board.stop(inactSlot, actSlot, inactIcon);
				System.out.println("stop");
				s = client.ReceiveFromSocket();
				if(s.trim().equals("win")){
					WinOrNot = !WinOrNot;
					inactTxt2.setText("");
					actTxt2.setText("You lose!");
					inactTxt1.setText("");
					actTxt1.setText("");
					inactTxt3.setText("");
					actTxt3.setText("");
					WorN = "You Lose!!";
					break outer;
				}
				else if(s.trim().equals("ACK")){
					act = !act;
					actTxt2.setText("Change players");
					inactTxt2.setText("Change players");
					board.ok.setEnabled(true);
					board.roll.setEnabled(true);
					board.stop.setEnabled(true);
					board.roll.addActionListener(board.cmd);
					board.stop.addActionListener(board.cmd);
					continue outer;
				}
				
			}
			else if(result.trim().equals("crap")){
				for(int k = 0;k <5; k++)
					board.current[k] = Integer.parseInt(receive[k+1]);
				for(int k = 5;k<11; k++)
					board.current[k] = Integer.parseInt(receive[k+1]);
				board.crap(inactSlot, board.current, inactIcon);
				act = !act;
				actTxt2.setText("Change players");
				inactTxt2.setText("Change players");
				board.ok.setEnabled(true);
				board.roll.setEnabled(true);
				board.stop.setEnabled(true);
				board.roll.addActionListener(board.cmd);
				board.stop.addActionListener(board.cmd);
				continue outer;
			}
			else if(result.trim().equals("dice")){
				int num1 = Integer.parseInt(receive[1]);
				int num2 = Integer.parseInt(receive[2]);
				int num3 = Integer.parseInt(receive[3]);
				int num4 = Integer.parseInt(receive[4]);
				board.changeDice(inactDice, num1, num2,num3,num4);
				continue inner2;
			}
		}
	}
	
	
	}
}
