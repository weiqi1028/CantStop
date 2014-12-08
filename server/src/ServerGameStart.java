/**
 * @author Dan Han, Weiqi Zhang
 * This class defines how server exchange information with client
 */
public class ServerGameStart {
	/**
	 * the protocol method, describes how server exchanges information with client
	 * @param Board, represents the current board state of the player corresponding 
	 * to @param server1
	 * @param server1, the connection between server and player
	 * @param server2, the connection between server and player
	 */
	public void Protocol(Plane Board,ServerConnect server1,ServerConnect server2){
		Dice dice = new Dice();
		Judge judge = new Judge();
		String action = null;
		int receive1, receive2;
		boolean WinOrNot=false;
		Board.initial();
		outer:
		while(!WinOrNot){
			//send current state to client
			server1.SendToSocket("current,"+Integer.toString(Board.CurrentBoard()[0])+","+
					Integer.toString(Board.CurrentBoard()[1])+","+
					Integer.toString(Board.CurrentBoard()[2])+","+
					Integer.toString(Board.CurrentBoard()[3])+","+
					Integer.toString(Board.CurrentBoard()[4])+","+
					Integer.toString(Board.CurrentBoard()[5])+","+
					Integer.toString(Board.CurrentBoard()[6])+","+
					Integer.toString(Board.CurrentBoard()[7])+","+
					Integer.toString(Board.CurrentBoard()[8])+","+
					Integer.toString(Board.CurrentBoard()[9])+","+
					Integer.toString(Board.CurrentBoard()[10]));
			server2.SendToSocket("current,"+Integer.toString(Board.CurrentBoard()[0])+","+
					Integer.toString(Board.CurrentBoard()[1])+","+
					Integer.toString(Board.CurrentBoard()[2])+","+
					Integer.toString(Board.CurrentBoard()[3])+","+
					Integer.toString(Board.CurrentBoard()[4])+","+
					Integer.toString(Board.CurrentBoard()[5])+","+
					Integer.toString(Board.CurrentBoard()[6])+","+
					Integer.toString(Board.CurrentBoard()[7])+","+
					Integer.toString(Board.CurrentBoard()[8])+","+
					Integer.toString(Board.CurrentBoard()[9])+","+
					Integer.toString(Board.CurrentBoard()[10]));
			inner:
			while(true){
				String[] receive = null;
				String s = "";
				s = server1.ReceiveFromSocket();
				receive = null;
				receive = s.split(",");
				action = receive[0];
				if (action.trim().equals("roll")){	//"roll" received from client, generate four random numbers
					dice.Roll();
					Serve4Game.roll1=dice.Dice1();
					Serve4Game.roll2=dice.Dice2();
					Serve4Game.roll3=dice.Dice3();
					Serve4Game.roll4=dice.Dice4();
					server1.SendToSocket("dice,"+Serve4Game.roll1+","+Serve4Game.roll2+","+
						Serve4Game.roll3+","+Serve4Game.roll4);
					server2.SendToSocket("dice,"+Serve4Game.roll1+","+Serve4Game.roll2+","+
						Serve4Game.roll3+","+Serve4Game.roll4);
					int r1,r2,r3,r4,r5,r6;
					r1 = Serve4Game.roll1 + Serve4Game.roll2;
					r2 = Serve4Game.roll1 + Serve4Game.roll3;
					r3 = Serve4Game.roll1 + Serve4Game.roll4;
					r4 = Serve4Game.roll2 + Serve4Game.roll3;
					r5 = Serve4Game.roll2 + Serve4Game.roll4;
					r6 = Serve4Game.roll3 + Serve4Game.roll4;
					
					//judge whether the player craps out
					if(!judge.judger(r1, Board)&&!judge.judger(r2, Board)&&!judge.judger(r3, Board)
						&&!judge.judger(r4, Board)&&!judge.judger(r5, Board)&&!judge.judger(r6, Board))
					{
						Board.initial();
						server1.SendToSocket("crap,"+Integer.toString(Board.CurrentBoard()[0])+","+
							Integer.toString(Board.CurrentBoard()[1])+","+
							Integer.toString(Board.CurrentBoard()[2])+","+
							Integer.toString(Board.CurrentBoard()[3])+","+
							Integer.toString(Board.CurrentBoard()[4])+","+
							Integer.toString(Board.CurrentBoard()[5])+","+
							Integer.toString(Board.CurrentBoard()[6])+","+
							Integer.toString(Board.CurrentBoard()[7])+","+
							Integer.toString(Board.CurrentBoard()[8])+","+
							Integer.toString(Board.CurrentBoard()[9])+","+
							Integer.toString(Board.CurrentBoard()[10]));
						server2.SendToSocket("crap,"+Integer.toString(Board.CurrentBoard()[0])+","+
							Integer.toString(Board.CurrentBoard()[1])+","+
							Integer.toString(Board.CurrentBoard()[2])+","+
							Integer.toString(Board.CurrentBoard()[3])+","+
							Integer.toString(Board.CurrentBoard()[4])+","+
							Integer.toString(Board.CurrentBoard()[5])+","+
							Integer.toString(Board.CurrentBoard()[6])+","+
							Integer.toString(Board.CurrentBoard()[7])+","+
							Integer.toString(Board.CurrentBoard()[8])+","+
							Integer.toString(Board.CurrentBoard()[9])+","+
							Integer.toString(Board.CurrentBoard()[10]));
						Serve4Game.act1 = !Serve4Game.act1 ;
						Serve4Game.act2 = !Serve4Game.act2 ;
						break outer;	
					}
				else
					continue inner;
				}
				
				//judge whether the numbers client chose can make a move
				else if (action.trim().equals("number")){
					receive1 = Integer.parseInt(receive[1]);
					receive2 = Integer.parseInt(receive[2]);
					judge.err = judge.judge(receive1, receive2, Board);
					if (judge.err == 1||judge.err==2||judge.err ==3){
						server1.SendToSocket("err,");
						server2.SendToSocket("err,");
						continue inner;
					}
					else if(judge.bool1&&judge.bool2){
						Board.ChangeCurrent(judge.num1);
						if(judge.num2 !=0){
							judge.bool2 = judge.judger(judge.num2,Board);
							if(judge.bool2 && judge.num2 != 0)
								Board.ChangeCurrent(judge.num2);
							else if(!judge.bool2)
								judge.num2 = 0;
						}
						server1.SendToSocket("ack,"+judge.num1+","+judge.num2);
						server2.SendToSocket("ack,"+judge.num1+","+judge.num2);
						continue inner;
					}
					else if((judge.bool1)&&(!judge.bool2)){
						Board.ChangeCurrent(judge.num1);
						server1.SendToSocket("ack,"+judge.num1+","+0);
						server2.SendToSocket("ack,"+judge.num1+","+0);
						continue inner;
					}
					else if((!judge.bool1)&&(judge.bool2)&&judge.num2!=0){
						Board.ChangeCurrent(judge.num2);
						judge.num1 = 0;
						server1.SendToSocket("ack,"+judge.num1+","+judge.num2);
						server2.SendToSocket("ack,"+judge.num1+","+judge.num2);
						continue inner;
					}
				}
				
				else if (action.trim().equals("stop")){	//"stop" received from client, change current state
					server1.SendToSocket("stop");
					server2.SendToSocket("stop");
					Board.ChangePrevious();
					WinOrNot =Board.WinerOrNot();	//judge whether the player win the game
					Board.initial();
					Serve4Game.act1 = !Serve4Game.act1 ;
					Serve4Game.act2 = !Serve4Game.act2 ;
					if (WinOrNot){
						server1.SendToSocket("win");
						server2.SendToSocket("win");
						break outer;
					}
					else{
						server1.SendToSocket("ACK");
						server2.SendToSocket("ACK");
					}
				break outer;
				}
			}
		}
		judge.err = 0;
		return;
	}
}



