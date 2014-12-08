/**
 * @author Dan Han, Weiqi Zhang, Abdulaziz Alshahrani
 * Game Over interface
 * class for display user's information (won, lost, and winning rate) and
 * restart or exit game
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.text.*;

public class GameOver extends JFrame implements ActionListener{
	private static final long serialVersionUID = -8965773902056088264L;
	private JToggleButton quit;
	private JToggleButton restart;
	private NumberFormat format;
	public String Swon;
	public String Slost;
	public String Srate;
	public String Sname;
	public String firstP;
	public String secondP;
	public String thirdP;
	public String rate1;
	public String rate2;
	public String rate3;
	public String winOrlose;
	public JTextField winOrLose;
	private ClientConnect client;
	private JTextField name;
	private JTextField won;
	private JTextField lost;
	private JTextField wr;
	private JTextField first;
	private JTextField second;
	private JTextField third;
	private JTextField Lrate1;
	private JTextField Lrate2;
	private JTextField Lrate3;

	
	public GameOver(ClientConnect client){
		format = NumberFormat.getInstance();    // format of winning rate, two decimals max 
		format.setMaximumFractionDigits(2);
		
		/* frame of game over interface
		 */
		this.client = client;
		this.setTitle("Game Over");
		this.setVisible(false);
		this.setSize(600,430);
		this.setLayout(null);
		this.setResizable(false);
		
		//place it at the center of the screen
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 600) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 430) / 2;
		this.setLocation(w, h); 
		this.setVisible(true);
		
		
		ImageIcon over = new ImageIcon("src/image/GameOver.jpg");
		ImagePanel gameOver = new ImagePanel(over);
		gameOver.setLayout(null);
		gameOver.setBounds(0, 0, 600, 400);
		this.add(gameOver);
		
		// restart button
		restart = new JToggleButton(new ImageIcon("src/image/bRestart.jpg"));
		restart.addActionListener(this);
		restart.setPressedIcon(new ImageIcon("src/image/tbRestart.jpg"));
		restart.setBounds(330, 340, 110, 50);
		gameOver.add(restart);
		
		// exit button
		quit = new JToggleButton(new ImageIcon("src/image/bExit.jpg"));
		quit.addActionListener(this);
		quit.setPressedIcon(new ImageIcon("src/image/tbExit.jpg"));
		quit.setBounds(460, 340, 110, 50);
		gameOver.add(quit);
		
		// text field for "You Win" or "You lose"
		winOrLose = new JTextField("");
		winOrLose.setBounds(200,15,200,50);
		winOrLose.setOpaque(false);
		winOrLose.setForeground(new Color(166,36,46));
		winOrLose.setHorizontalAlignment(JTextField.CENTER);
		winOrLose.setFont(new Font("Berlin Sans FB Demi",Font.BOLD,34));
		winOrLose.setEditable(false);
		winOrLose.setBorder(null);
		gameOver.add(winOrLose);
		
		// text field for user name
		name = new JTextField("");
		name.setBounds(20,90,500,30);
		name.setOpaque(false);
		name.setForeground(new Color(128,54,41));
		name.setHorizontalAlignment(JTextField.LEFT);
		name.setFont(new Font("Berlin Sans FB Demi",Font.BOLD,33));
		name.setEditable(false);
		name.setBorder(null);
		gameOver.add(name);
		
		// text field for number of games the user has won
		won = new JTextField("");
		won.setBounds(20,120,500,30);
		won.setOpaque(false);
		won.setForeground(new Color(128,54,41));
		won.setHorizontalAlignment(JTextField.LEFT);
		won.setFont(new Font("Berlin Sans FB Demi",Font.BOLD,33));
		won.setEditable(false);
		won.setBorder(null);
		gameOver.add(won);
		
		// text field for number of games the user has lost
		lost = new JTextField("");
		lost.setBounds(20,150,500,30);
		lost.setOpaque(false);
		lost.setForeground(new Color(128,54,41));
		lost.setHorizontalAlignment(JTextField.LEFT);
		lost.setFont(new Font("Berlin Sans FB Demi",Font.BOLD,33));
		lost.setEditable(false);
		lost.setBorder(null);
		gameOver.add(lost);
		
		// text field for winning rate of the user
		wr = new JTextField("");
		wr.setBounds(20,180,500,30);
		wr.setOpaque(false);
		wr.setForeground(new Color(128,54,41));
		wr.setHorizontalAlignment(JTextField.LEFT);
		wr.setFont(new Font("Berlin Sans FB Demi",Font.BOLD,33));
		wr.setEditable(false);
		wr.setBorder(null);
		gameOver.add(wr);
		
		
		/* drawing area for displaying the top three players' name with
		 * their winning rate
		 */
		first = new JTextField("");
		first.setBounds(20,240,290,30);
		first.setOpaque(false);
		first.setForeground(new Color(72,53,55));
		first.setHorizontalAlignment(JTextField.LEFT);
		first.setFont(new Font("Berlin Sans FB",Font.PLAIN,31));
		first.setEditable(false);
		first.setBorder(null);
		gameOver.add(first);
		
		second = new JTextField("");
		second.setBounds(20,270,290,30);
		second.setOpaque(false);
		second.setForeground(new Color(72,53,55));
		second.setHorizontalAlignment(JTextField.LEFT);
		second.setFont(new Font("Berlin Sans FB",Font.PLAIN,31));
		second.setEditable(false);
		second.setBorder(null);
		gameOver.add(second);
		
		third = new JTextField("");
		third.setBounds(20,300,290,30);
		third.setOpaque(false);
		third.setForeground(new Color(72,53,55));
		third.setHorizontalAlignment(JTextField.LEFT);
		third.setFont(new Font("Berlin Sans FB",Font.PLAIN,31));
		third.setEditable(false);
		third.setBorder(null);
		gameOver.add(third);
		
		Lrate1 = new JTextField("");
		Lrate1.setBounds(300,240,280,30);
		Lrate1.setOpaque(false);
		Lrate1.setForeground(new Color(72,53,55));
		Lrate1.setHorizontalAlignment(JTextField.LEFT);
		Lrate1.setFont(new Font("Berlin Sans FB",Font.PLAIN,31));
		Lrate1.setEditable(false);
		Lrate1.setBorder(null);
		gameOver.add(Lrate1);
		
		Lrate2 = new JTextField("");
		Lrate2.setBounds(300,270,280,30);
		Lrate2.setOpaque(false);
		Lrate2.setForeground(new Color(72,53,55));
		Lrate2.setHorizontalAlignment(JTextField.LEFT);
		Lrate2.setFont(new Font("Berlin Sans FB",Font.PLAIN,31));
		Lrate2.setEditable(false);
		Lrate2.setBorder(null);
		gameOver.add(Lrate2);
		
		Lrate3 = new JTextField("");
		Lrate3.setBounds(300,300,280,30);
		Lrate3.setOpaque(false);
		Lrate3.setForeground(new Color(72,53,55));
		Lrate3.setHorizontalAlignment(JTextField.LEFT);
		Lrate3.setFont(new Font("Berlin Sans FB",Font.PLAIN,31));
		Lrate3.setEditable(false);
		Lrate3.setBorder(null);
		gameOver.add(Lrate3);
		// end of drawing area for the top three players
		
		// when the frame is closed, system exits
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	}
	
	// refresh the information displayed on the board
	public void refresh(){
		name.setText("Name: " + Sname);
		won.setText("Won: " + Swon);
		lost.setText("Lost: " + Slost);
		wr.setText("Winning Rate: " + Srate);
		first.setText("First: " + firstP);
		second.setText("Second: " + secondP);
		third.setText("Third: " + thirdP);
		Lrate1.setText("Winning Rate: " + rate1);
		Lrate2.setText("Winning Rate: " + rate2);
		Lrate3.setText("Winning Rate: " + rate3);
		winOrLose.setText(winOrlose);
	}
	
	
	/* implements abstract method
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * action listener for exit button and restart button
	 */
	public void actionPerformed(ActionEvent event){
		Object obj = new Object();
		obj = event.getSource();
		if(obj.equals(quit)){
			client.SendToSocket("exit");
			System.exit(0);
		}
		else if(obj.equals(restart)){
			//restart
			client.SendToSocket("restart");
			System.out.println("restart");
			this.dispose();
		}
	}

	

}
