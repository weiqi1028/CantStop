/**
 * @author Dan Han, Weiqi Zhang
 * Drawing the game board, and handle all actions to the buttons on the game board
 * Game User Interface
 * using manual layout
 */



import java.awt.*;
import java.text.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.*;


public class Board extends JFrame implements ActionListener {
	private static final long serialVersionUID = -8965773902056088264L;
	private ClientConnect client;
	private JPanel plane;
	private JPanel control;
	public JLabel[][] slots1;	// places for player1's markers
	public JLabel[][] slots2;	// places for player2's markers
	private JLabel[] label;
	private JPanel[] column;
	private JToggleButton[] button = new JToggleButton[11];	// number buttons
	public JToggleButton ok;	// ok button
	public JToggleButton roll;	// roll button
	public JToggleButton stop;	// stop button
	public ActionListener listen = new getInputNum();
	public ActionListener cmd = new getCommand();
	private int[] total = {3,5,7,9,11,13,11,9,7,5,3};
	private boolean[] conquer = {false,false,false,false,false,false,false,false,false,false,false};
	public int[] current = new int[11];
	private Icon dice1 = new ImageIcon("src/image/1.jpg");
	private Icon dice2 = new ImageIcon("src/image/2.jpg");
	private Icon dice3 = new ImageIcon("src/image/3.jpg");
	private Icon dice4 = new ImageIcon("src/image/4.jpg");
	private Icon dice5 = new ImageIcon("src/image/5.jpg");
	private Icon dice6 = new ImageIcon("src/image/6.jpg");
	private Icon yellow = new ImageIcon("src/image/Yellow.jpg");
	public JLabel[] RDice;
	public JLabel[] BDice;
	private JTextField rp;
	private JTextField bp;
	public JTextField rLine1;
	public JTextField rLine2;
	public JTextField rLine3;
	public JTextField bLine1;
	public JTextField bLine2;
	public JTextField bLine3;
	public JTextField rName;
	public JTextField bName;
	private JMenuItem help;
	private JMenuItem exit;
	private JMenuItem userInfo;
	private JMenuItem about;
	
	// constructor
	public Board(ClientConnect client){
		this.client = client;
		this.setTitle("Can't Stop Game");
		this.setResizable(false);
		
		/* frame for the game board
		*/
		this.setSize(800, 830);
		// place it in the center of the screen
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 800) / 2;
		this.setLocation(w, h); 
		this.setVisible(false);
		this.setLayout(null);
		ImageIcon board = new ImageIcon("src/image/Board.jpg");
		ImagePanel rootPanel = new ImagePanel(board);
		rootPanel.setLayout(null);
		rootPanel.setBounds(0, 30, 800, 800);

	
		this.add(rootPanel);
		plane = new JPanel();
		plane.setLayout(null);
		plane.setOpaque(false);
		plane.setBounds(70,20,660,580);
		rootPanel.add(plane);
		control = new JPanel();
		control.setLayout(null);
		control.setOpaque(false);
		control.setBounds(0, 600, 800, 200);
		rootPanel.add(control);
		
		/* draw the columns and slots on the board
		 */
		column = new JPanel[11];
		slots1 = new JLabel[11][14];
		slots2 = new JLabel[11][14];
		label = new JLabel[11];
		
		int max = 2;
	
		for(int i = 0; i<=5; i++){
			column[i] = new JPanel();
			
			column[i].setBounds(0+60*i,200-i*40,60,160+80*i);
			column[i].setLayout(null);
			column[i].setOpaque(false);
			plane.add(column[i]);

			for( int j = max ; j>=0; j--){
				slots1[i][j] = new JLabel("     ");
				slots1[i][j].setBounds(0,0+(max-j)*20,60,20);
				slots1[i][j].setBorder(null);
				column[i].add(slots1[i][j]);
				slots1[i][j].setForeground(Color.blue);
				slots1[i][j].setBackground(Color.WHITE);
				slots1[i][j].setIcon(null);
				slots1[i][j].setOpaque(false);
			}
			
			label[i] = new JLabel("     ");
			label[i].setBounds(0,max*20+20,60,40);
			label[i].setIcon(new ImageIcon("src/image/n"+Integer.toString(i+2)+".jpg"));
			label[i].setOpaque(false);
			column[i].add(label[i]);

			
			for( int k = 0 ; k<= max; k++){
				slots2[i][k] = new JLabel("");
				slots2[i][k].setBounds(0,max*20+60+k*20,60,20);
				slots2[i][k].setBorder(null);
				slots2[i][k].setHorizontalAlignment(SwingConstants.CENTER);
				column[i].add(slots2[i][k]);
				slots2[i][k].setForeground(Color.RED);
				slots2[i][k].setBackground(Color.WHITE);
				slots2[i][k].setIcon(null);
				slots2[i][k].setOpaque(false);
			}
			max+=2;
		}
		max-=2;
		for(int i = 6; i<=10; i++){
			column[i] = new JPanel();
			column[i].setBounds(60*i,40+(i-6)*40,60,480-80*(i-6));
			column[i].setLayout(null);
			column[i].setOpaque(false);
			plane.add(column[i]);
			max-=2;
			for(int j = max ; j>=0; j--){
				slots1[i][j] = new JLabel("");
				slots1[i][j].setBounds(0,(max-j)*20,60,20);
				slots1[i][j].setBorder(null);
				column[i].add(slots1[i][j]);
				slots1[i][j].setForeground(Color.blue);
				slots1[i][j].setBackground(Color.WHITE);
				slots1[i][j].setIcon(null);
				slots1[i][j].setOpaque(false);
			}
			
			label[i] = new JLabel();
			label[i].setBounds(0,max*20+20,60,40);
			label[i].setIcon(new ImageIcon("src/image/n"+Integer.toString(i+2)+".jpg"));
			column[i].add(label[i]);
			
			for( int k = 0 ; k<= max; k++){
				slots2[i][k] = new JLabel("");
				slots2[i][k].setBounds(0,max*20+60+k*20,60,20);
				slots2[i][k].setBorder(null);
				
				column[i].add(slots2[i][k]);
				slots2[i][k].setForeground(Color.RED);
				slots2[i][k].setBackground(Color.WHITE);
				slots2[i][k].setIcon(null);
				slots2[i][k].setOpaque(false);
			}
		}// end of drawing columns and slots
		
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setLayout(null);
		ButtonPanel.setBounds(70, 0, 660, 30);
		control.add(ButtonPanel);
		
		/* drawing number buttons and ok button, roll button
		 * stop button
		 * and adding action listeners to the buttons
		 */
		
		for(int i = 0; i<11; i++){
			button[i] = new JToggleButton(new ImageIcon("src/image/b"+Integer.toString(i+2)+".jpg")); 
			button[i].setBounds(60*i, 0, 60, 30);
			button[i].setActionCommand(Integer.toString(i+2));
			button[i].setSelectedIcon(new ImageIcon("src/image/tb"+Integer.toString(i+2)+".jpg"));
			addButton(button[i], listen);
			ButtonPanel.add(button[i]);
		}
		
		JPanel RollOrNot = new JPanel();
		RollOrNot.setLayout(null);
		RollOrNot.setBounds(370, 30, 60, 150);
		RollOrNot.setOpaque(false);
		control.add(RollOrNot);
		
		ok = new JToggleButton(new ImageIcon("src/image/ok.jpg"));
		ok.setPressedIcon(new ImageIcon("src/image/tok.jpg"));
		ok.setActionCommand("OK");
		ok.setBounds(0, 10, 60, 30);
		ok.setForeground(Color.WHITE);
		roll = new JToggleButton(new ImageIcon("src/image/roll.jpg"));
		roll.setPressedIcon(new ImageIcon("src/image/troll.jpg"));
		roll.setActionCommand("roll,");
		roll.setBounds(0, 50, 60, 30);
		roll.setFont(new Font("ALGERIAN",Font.BOLD,17));
		stop = new JToggleButton( new ImageIcon("src/image/stop.jpg"));
		stop.setPressedIcon(new ImageIcon("src/image/tstop.jpg"));
		stop.setActionCommand("stop,");
		stop.setBounds(0, 90, 60, 30);
		stop.setFont(new Font("ALGERIAN",Font.BOLD,17));
		
		addButton(roll, cmd);
		addButton(stop, cmd);
		addButton(ok, listen);
		RollOrNot.add(ok);
		RollOrNot.add(roll);
		RollOrNot.add(stop);
		//end of drawing buttons and adding action listeners
		
		
		/* drawing dice area and information area for red player
		 */
		JPanel diceRed = new JPanel();
		diceRed.setBounds(40, 40, 100, 100);
		diceRed.setOpaque(false);
		control.add(diceRed);
		RDice = new JLabel[4];
		for(int i = 0; i<4; i++){
			RDice[i] = new JLabel("");
			diceRed.setLayout(new GridLayout(2, 2));
			diceRed.add(RDice[i]);
			RDice[i].setIcon(null);
			
		}
		JPanel infoRed = new JPanel();
		infoRed.setLayout(null);
		infoRed.setBounds(150, 40, 210, 111);
		infoRed.setBorder(new LineBorder(Color.BLACK));
		control.add(infoRed);
		infoRed.setBackground(new Color(153,11,28));
		
		JPanel redPlayer = new JPanel();
		redPlayer.setLayout(null);
		redPlayer.setBounds(40, 510, 150, 60);
		redPlayer.setForeground(new Color(112,32,74));
		redPlayer.setOpaque(false);
		rootPanel.add(redPlayer);

		rp = new JTextField("Player1");
		rp.setFont(new Font("ALGERIAN",Font.BOLD,28));
		rp.setForeground(new Color(112,32,74));
		rp.setBorder(null);
		rp.setBounds(0, 0, 150,30);
		
		redPlayer.add(rp);
		rp.setHorizontalAlignment(JTextField.LEFT);
		rp.setEditable(false);
		rp.setOpaque(false);

		
		rName = new JTextField("");
		rName.setBounds(0, 30, 150,30);
		rName.setForeground(new Color(112,32,74));
		rName.setBorder(null);
		rName.setFont(new Font("ALGERIAN",Font.BOLD,22));
		redPlayer.add(rName);
		rName.setHorizontalAlignment(JTextField.LEFT);
		rName.setEditable(false);
		rName.setOpaque(false);
		
		rLine1 = new JTextField("");
		rLine1.setBounds(0, 0, 200,37);
		rLine1.setBackground(new Color(153,11,28));
		rLine1.setBorder(null);
		rLine1.setForeground(Color.WHITE);
		rLine1.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,24));
		infoRed.add(rLine1);
		rLine1.setHorizontalAlignment(JTextField.CENTER);
		rLine1.setEditable(false);
		
		rLine2 = new JTextField("");
		rLine2.setBounds(0, 37, 200,37);
		rLine2.setBackground(new Color(153,11,28));
		rLine2.setBorder(null);
		rLine2.setForeground(Color.WHITE);
		rLine2.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,24));
		infoRed.add(rLine2);
		rLine2.setHorizontalAlignment(JTextField.CENTER);
		rLine2.setEditable(false);
		
		rLine3 = new JTextField("");
		rLine3.setBounds(0, 74, 200,37);
		rLine3.setBackground(new Color(153,11,28));
		rLine3.setBorder(null);
		rLine3.setForeground(Color.WHITE);
		rLine3.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,24));
		infoRed.add(rLine3);
		rLine3.setHorizontalAlignment(JTextField.CENTER);
		rLine3.setEditable(false);
		// end of drawing dice area and information area for red player
		
		
		/* drawing dice area and information area for blue player
		 * 
		 */
		JPanel infoBlue = new JPanel();
		infoBlue.setLayout(null);
		infoBlue.setBackground(new Color(45,39,94));
		infoBlue.setBounds(440,40,210,111);
		control.add(infoBlue);
		
		JPanel bluePlayer = new JPanel();
		bluePlayer.setLayout(null);
		bluePlayer.setBounds(610, 510, 150, 60);
		bluePlayer.setForeground(new Color(112,32,74));
		infoBlue.setBorder(new LineBorder(Color.BLACK));
		bluePlayer.setOpaque(false);
		rootPanel.add(bluePlayer);
		
		bp = new JTextField("Player2");
		bp.setForeground(new Color(112,32,74));
		bp.setBorder(null);
		bp.setBounds(0, 0,150,30);
		bluePlayer.add(bp);
		bp.setHorizontalAlignment(JTextField.LEFT);
		bp.setEditable(false);
		bp.setOpaque(false);
		bp.setFont(new Font("ALGERIAN",Font.BOLD,28));
		
		bName = new JTextField("");
		bName.setBounds(0, 30, 150,30);
		bName.setForeground(new Color(112,32,74));
		bName.setBorder(null);
		bName.setFont(new Font("ALGERIAN",Font.BOLD,22));
		bName.setEditable(false);
		bName.setOpaque(false);
		
		bluePlayer.add(bName);
		bName.setHorizontalAlignment(JTextField.LEFT);
		
		bLine1 = new JTextField("");
		bLine1.setBounds(0, 0, 200,37);
		bLine1.setBackground(new Color(45,39,94));
		bLine1.setForeground(Color.WHITE);
		bLine1.setBorder(null);
		bLine1.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,24));
		infoBlue.add(bLine1);
		bLine1.setHorizontalAlignment(JTextField.CENTER);
		bLine1.setEditable(false);
		
		bLine2 = new JTextField("");
		bLine2.setBounds(0, 37, 200,37);
		bLine2.setBackground(new Color(45,39,94));
		bLine2.setForeground(Color.WHITE);
		bLine2.setBorder(null);
		bLine2.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,24));
		infoBlue.add(bLine2);
		bLine2.setHorizontalAlignment(JTextField.CENTER);
		bLine2.setEditable(false);
		
		bLine3 = new JTextField("");
		bLine3.setBounds(0, 74, 200,37);
		bLine3.setBackground(new Color(45,39,94));
		bLine3.setForeground(Color.WHITE);
		bLine3.setBorder(null);
		bLine3.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,24));
		infoBlue.add(bLine3);
		bLine3.setHorizontalAlignment(JTextField.CENTER);
		bLine3.setEditable(false);

		JPanel diceBlue = new JPanel();
		diceBlue.setBounds(660,40,100,100);
		diceBlue.setOpaque(false);
		control.add(diceBlue);
		
		BDice = new JLabel[4];
		for(int i = 0; i<4; i++){
			BDice[i] = new JLabel("");
			diceBlue.setLayout(new GridLayout(2, 2));
			diceBlue.add(BDice[i]);
			BDice[i].setIcon(null);
		}	
		// end of drawing dice area and information area for blue player
		
		
		/* drawing tool bar at the top of the frame
		 * adding action listeners to the menu items
		 */
		JMenuBar menubar = new JMenuBar();
		menubar.setBounds(0, 0, 800, 30);
		JMenu menu = new JMenu("Menu");
		menubar.add(menu);
		userInfo = new JMenuItem("User Info");
		help = new JMenuItem("Help");
		exit = new JMenuItem("Exit");
		about = new JMenuItem("About Game");
		menu.add(userInfo);
		menu.add(help);
		menu.add(about);
		menu.add(exit);
		userInfo.addActionListener(this);
		help.addActionListener(this);
		exit.addActionListener(this);
		about.addActionListener(this);
		this.add(menubar);
		// end of drawing tool bar
		
		// when close the game frame, the system exits
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  


	}
	
	
	/* initialize the board, remove all the markers on the board
	 * enable all the buttons
	 * get ready for a new game
	 */
	public void initial(){
		int max = 2;
		for(int i = 0; i<=5; i++){
			for( int j = max ; j>=0; j--)
				slots1[i][j].setIcon(null);
			for( int k = 0 ; k<= max; k++)
				slots2[i][k].setIcon(null);
			max+=2;
		}
		max-=2;
		for(int i = 6; i<=10; i++){
			max-=2;
			for(int j = max ; j>=0; j--)
				slots1[i][j].setIcon(null);
			for( int k = 0 ; k<= max; k++)
				slots2[i][k].setIcon(null);
		}
		for(int j = 0; j<11; j++){
			current[j] = 0;
			conquer[j] = false;
		}
			
		ok.setEnabled(true);
		stop.setEnabled(true);
		roll.setEnabled(true);
		for(int k = 0; k<4; k++){
			RDice[k].setIcon(null);
			BDice[k].setIcon(null);
		}
	}
	
	private void addButton(JButton bt, ActionListener listener){
		bt.addActionListener(listener);
	}
	
	private void addButton(JToggleButton bt, ActionListener listener){
		bt.addActionListener(listener);
	}
	
	
	/* implements abstract method
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * action listener for menu items
	 */
	public void actionPerformed(ActionEvent event){
		Object obj = new Object();
		obj = event.getSource();
		if(obj.equals(exit))
			System.exit(0);
		else if(obj.equals(help)){
			JFrame Fhelp = new JFrame("Help");
			Fhelp.setSize(400, 400);
			int wh = (Toolkit.getDefaultToolkit().getScreenSize().width - 400) / 2;
			int hh = (Toolkit.getDefaultToolkit().getScreenSize().height - 400) / 2;
			Fhelp.setLocation(wh, hh); 
			JPanel Phelp =new JPanel();
			JTextArea Htext=new JTextArea(20,40);
			Htext.setEditable(false);
			JScrollPane Scroll =new JScrollPane(Htext);
			try{
				BufferedReader br = new BufferedReader(new FileReader("src/txt/help.txt"));
				String helpInfo = "";
				while((helpInfo = br.readLine())!=null){
					Htext.append(helpInfo);
					Htext.append("\n");
				}
			}
			catch(IOException ioe){
				System.err.println(ioe);
			}
			Phelp.add(Scroll);
			Fhelp.add(Phelp);
			Htext.setLineWrap(true);
			Fhelp.pack();
			Fhelp.setVisible(true);
		}
		else if(obj.equals(about)){
			JOptionPane.showMessageDialog(null, "Can't Stop Game" + "\n" +
					"Since 1980" + "\n" +
					"Designer: Sid Sackson" + "\n" +
					"Author: " + "\n" +
					"Dan Han" + "\n" + 
					"Weiqi Zhang" + "\n" + 
					"Abdulaziz Alshahrani"
					);
		}
		else if(obj.equals(userInfo)){
			NumberFormat f = NumberFormat.getInstance();
			f.setMaximumIntegerDigits(2);
			String[] s = null;
			s = ClientGameStart.userInformation.split(",");
			float rate1, rate2;
			int won1, won2, lost1, lost2;
			String name1, name2;
			name1 = s[1];
			name2 = s[2];
			rate1 = Float.parseFloat(s[3])*100;
			rate2 = Float.parseFloat(s[4])*100;
			String srate1 = f.format(rate1);
			String srate2 = f.format(rate2);
			won1 = Integer.parseInt(s[5]);
			won2 = Integer.parseInt(s[6]);
			lost1 = Integer.parseInt(s[7]);
			lost2 = Integer.parseInt(s[8]);
			JOptionPane.showMessageDialog(null, "User Name: " + name1 + "  " + "Winning Rate: " + srate1 + "  " + "Won: " + won1 + "  " + "Lost: " + lost1 + "\n" +
					                            "User Name: " + name2 + "  " + "Winning Rate: " + srate2 + "  " + "Won: " + won2 + "  " + "Lost: " + lost2);
		}
	}
	
	/* action listener for number buttons and ok button
	 * 
	 */
	private class getInputNum implements ActionListener{
		String send = "number,";
		String exNum = "";
		int two = 0;
		public void actionPerformed(ActionEvent event){
			String num = "";
			if(!(event.getActionCommand().trim().equals("OK"))){
				num = event.getActionCommand();
				if(num.equals(exNum)){
					num = "";
					exNum = "";
				}
				else{
					if(!(exNum.trim().equals(""))){
							button[Integer.parseInt(exNum)-2].setSelected(false);
					}
					exNum = num;
				}
			}
			else if(event.getActionCommand().trim().equals("OK")){
				if(!(exNum.trim().equals(""))){
						button[Integer.parseInt(exNum)-2].setSelected(false);
					if(two == 1){
						send = send + exNum;
						System.out.println(send);
						client.SendToSocket(send);
						num = "";
						exNum = "";
						send = "number,";
						two = 0;
					}
					else if(two == 0){
						send = send + exNum + ",";
						two++;
						num = "";
						exNum = "";
					}
				}	
			}
		}
	}

	
	/* action listener for stop button, roll button
	 * 
	 */
	private class getCommand implements ActionListener{
		public void actionPerformed(ActionEvent event){
			Object obj = new Object();
			obj = event.getSource();
			if(obj.equals(stop))
				client.SendToSocket("stop,");
			else if(obj.equals(roll))
				client.SendToSocket("roll,");
		}
	}
	
	// update dice area when new random numbers are received from server
	public void changeDice(JLabel[] label,int num1,int num2,int num3,int num4){
		drawDice(label[0], num1);
		drawDice(label[1], num2);
		drawDice(label[2], num3);
		drawDice(label[3], num4);
	}
	
	// called by public void changeDice(), drawing dice pictures
	private void drawDice(JLabel label, int number){
		switch(number){
		case 1:
			label.setIcon(dice1);
			break;
		case 2:
			label.setIcon(dice2);
			break;
		case 3:
			label.setIcon(dice3);
			break;
		case 4:
			label.setIcon(dice4);
			break;
		case 5:
			label.setIcon(dice5);
			break;
		case 6:
			label.setIcon(dice6);
			break;
		}
		
	}
	
	// called when "ack" is received from server
	public void ack(JLabel[][] label, int num1, int num2){
		if(num1 != 0 && !conquer[num1-2]){
			label[num1-2][current[num1-2]].setIcon(yellow);
			current[num1-2]+=1;
		}
		if(num2 != 0 && !conquer[num2-2]){
			label[num2-2][current[num2-2]].setIcon(yellow);
			current[num2-2]+=1;
		}
	}
	
	// called when "stop" is received from server
	public void stop(JLabel[][] label1, JLabel[][] label2, ImageIcon image){
		for(int k = 0; k<11; k++){
			if(current[k] == total[k] && !conquer[k]){
				conquer[k] = true;
				for(int j = total[k]-1;j>=0;j--){
					label1[k][j].setIcon(image);
					label2[k][j].setIcon(image);
				}
			}
			else{
				for(int j = current[k]-1;j>=0;j--){
					if(!conquer[k])
						label1[k][j].setIcon(image);
				}
			}
		}
	}
	
	// called when "crap" is received from server
	public void crap(JLabel[][] label, int[] c, ImageIcon icon){
		int max = 3;
		for(int k = 0;k <5; k++){
				for(int j = c[k]-1;j>=0;j--){
					if(!conquer[k])
						label[k][j].setIcon(icon);
			}
				for(int j = c[k];j<max;j++){
					if(!conquer[k])
						label[k][j].setIcon(null);
				}
				max = max+2;
		}
		for(int k = 5;k<11; k++){
				for(int j = c[k]-1;j>=0;j--){
					if(!conquer[k])
						label[k][j].setIcon(icon);
			}
				for(int j = c[k];j<max;j++){
					if(!conquer[k])
						label[k][j].setIcon(null);
				}
				max = max-2;
		}
		}
	}

