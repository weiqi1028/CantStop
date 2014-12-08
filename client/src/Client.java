/**
 * @author Dan Han, Weiqi Zhang
 * main method of the client
 */

public class Client {
	/* main method
	 * 
	 */
	public static void main(String[] args){
		ClientConnect client = new ClientConnect();
		boolean start = false;
		boolean restart = true;
		Login login = new Login(client);
		Board board = new Board(client);
		PlayMidi soundBegin = new PlayMidi("src/sound/beginMusic.mid");
		soundBegin.Play();				// play beginMusic when game is not started
		while(true){
			start = login.getStart();
			if(start){
				login.setVisible(false);
				board.setVisible(true);
				break;
			}
		}
		while(restart){
		board.setVisible(false);
		board.initial();
		board.setVisible(true);
		String s = "";
		ClientGameStart newGame = new ClientGameStart(client);
		s = client.ReceiveFromSocket();
		soundBegin.Stop();					// stop the beginMusic, and play gameMusic, game begins
		PlayMidi soundGame = new PlayMidi("src/sound/gameMusic.mid");
		soundGame.Play();
		if(s.trim().equals("You are player 1"))
			newGame.protocol(1, board);		// user begins game as player1
		else if(s.trim().equals("You are player 2"))
			newGame.protocol(2, board);		// user begins game as player2

		// disable all buttons
		board.ok.setEnabled(false);
		board.stop.setEnabled(false);
		board.roll.setEnabled(false);
		
		// create game over interface
		GameOver over = new GameOver(client);		
		soundGame.Stop();					// stop the gameMusic, and play endMusic, game is over
		PlayMidi soundEnd = new PlayMidi("src/sound/endMusic.mid");
		soundEnd.Play();
		over.setVisible(false);
		
		// receive game result information from server
		s = client.ReceiveFromSocket();
		System.out.println(s);
		String receive[] = null;
		receive = s.split(",");
		if(receive[0].trim().equals("newInfo")){
			over.Sname = receive[1];
			over.Swon = receive[2];
			over.Slost = receive[3];
			over.Srate = receive[4] + "%";
			over.firstP = receive[5];
			over.secondP = receive[6];
			over.thirdP = receive[7];
			over.rate1 = receive[8] + "%";
			over.rate2 = receive[9] + "%";
			over.rate3 = receive[10] + "%";
		}
		over.winOrlose = newGame.WorN;
		over.refresh();   			// update game over interface, display new information
		over.setVisible(true);
		s = client.ReceiveFromSocket();
		soundEnd.Stop();			// stop endMusic
		if(!s.trim().equals("restart"))
			restart = false;
		}
			
	}
}
