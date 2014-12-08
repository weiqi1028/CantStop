/**
 * @author Dan Han, Weiqi Zhang, Abdulaziz Alshahrani
 * Game Starting user interface
 * class for get host name/IP and port number,
 * register new user, login returning user
 * using card layout
 * inside each panel, using manual layout
 * 
 */


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.*;

public class Login extends JFrame implements ActionListener, Serializable {
	private static final long serialVersionUID = -8965773902056088264L;
	private ClientConnect client;
	private JPanel pane = null;
	private ImagePanel connectPanel;
	private ImagePanel regPanel;
	private ImagePanel log;
	private CardLayout card = null;
	private TextField userName;
	private TextField pwd;
	private TextField newName;
	private TextField pwd1;
	private TextField pwd2;
	private JLabel ipLabel;
	private JLabel portLabel;
	private JToggleButton login;
	private JToggleButton back1;
	private JToggleButton back2;
	private JToggleButton btNew;
	private JToggleButton btReturn;
	private JToggleButton quit;
	private JToggleButton btconnect;
	private JToggleButton register;
	private JTextField host;
	private JTextField port;
	private boolean start = false;

	// constructor
	public Login(ClientConnect client){
		
		// frame for the game starting
		this.client = client;
		this.setTitle("Login");
		this.setVisible(false);
		this.setSize(600,430);
		this.setLayout(null);
		this.setResizable(false);
		// place it at the center of the screen
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 600) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 400) / 2;
		this.setLocation(w, h); 
		
		// card layout
		card = new CardLayout();
		pane = new JPanel(card);
		pane.setBounds(0, 0, 600, 400);
		this.add(pane);
		
		/* drawing connection interface
		 * adding action listeners to the buttons on it
		 */
		connectPanel = new ImagePanel(new ImageIcon("src/image/GameStart.jpg"));
		connectPanel.setLayout(null);
		connectPanel.setBounds(0, 0, 600, 400);
		pane.add(connectPanel, "connectpanel");
		
		btconnect = new JToggleButton(new ImageIcon("src/image/bConnect.jpg"));
		btconnect.setPressedIcon(new ImageIcon("src/image/tbConnect.jpg"));
		btconnect.addActionListener(this);
		btconnect.setBounds(65, 230, 220, 45);
		connectPanel.add(btconnect);

		btNew = new JToggleButton(new ImageIcon("src/image/bNewUser.jpg"));
		btNew.setPressedIcon(new ImageIcon("src/image/tbNewUser.jpg"));
		btNew.addActionListener(this);
		btNew.setBounds(65, 310, 220, 45);
		btNew.setVisible(false);
		connectPanel.add(btNew);
		
		btReturn = new JToggleButton(new ImageIcon("src/image/bRUser.jpg"));
		btReturn.setPressedIcon(new ImageIcon("src/image/tbRUser.jpg"));
		btReturn.addActionListener(this);
		btReturn.setBounds(315, 310, 220, 45);
		btReturn.setVisible(false);
		connectPanel.add(btReturn);
		
		quit = new JToggleButton(new ImageIcon("src/image/bQuit.jpg"));
		quit.setPressedIcon(new ImageIcon("src/image/tbQuit.jpg"));
		quit.setBounds(315, 230, 220, 45);
		quit.addActionListener(this);
		connectPanel.add(quit);
		
		ipLabel = new JLabel("Host IP: ");
		ipLabel.setBorder(null);
		ipLabel.setForeground(Color.BLACK);
		ipLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 24));
		ipLabel.setOpaque(false);
		ipLabel.setHorizontalAlignment(JTextField.LEFT);
		ipLabel.setBounds(112, 100, 90, 40);
		connectPanel.add(ipLabel);
		
		portLabel = new JLabel("Port Number: ");
		portLabel.setBorder(null);
		portLabel.setForeground(Color.BLACK);
		portLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 24));
		portLabel.setOpaque(false);
		portLabel.setHorizontalAlignment(JTextField.LEFT);
		portLabel.setBounds(112, 155, 150, 40);
		connectPanel.add(portLabel);
		
		host = new JTextField("");
		host.setEditable(true);
		host.setBorder(new LineBorder(new Color(28,9,0)));
		host.setForeground(Color.BLACK);
		host.setFont(new Font("Agency FB Bold", Font.PLAIN, 26));
		host.setOpaque(true);
		host.setBounds(202, 100, 225, 40);
		host.setVisible(true);
		connectPanel.add(host);
		
		port = new JTextField("");
		port.setEditable(true);
		port.setBorder(new LineBorder(new Color(28,9,0)));
		port.setForeground(Color.BLACK);
		port.setFont(new Font("Agency FB Bold", Font.PLAIN, 26));
		port.setOpaque(true);
		port.setBounds(262, 155, 165, 40);
		port.setVisible(true);
		connectPanel.add(port);
		// end of drawing connection interface
		
		
		/* drawing register interface
		 * adding action listeners for the buttons on it
		 */
		regPanel = new ImagePanel(new ImageIcon("src/image/GameStart.jpg"));
		regPanel.setBounds(0, 0, 600, 400);
		regPanel.setLayout(null);
		
		JLabel namLabel = new JLabel("UserName:");
		namLabel.setBorder(null);
		namLabel.setForeground(Color.BLACK);
		namLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 26));
		namLabel.setOpaque(false);
		namLabel.setHorizontalAlignment(JTextField.LEFT);
		namLabel.setBounds(112, 115, 120, 45);
		regPanel.add(namLabel);
		
		JLabel pwLabel = new JLabel("PassWord:");
		pwLabel.setBorder(null);
		pwLabel.setForeground(Color.BLACK);
		pwLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 26));
		pwLabel.setOpaque(false);
		pwLabel.setHorizontalAlignment(JTextField.LEFT);
		pwLabel.setBounds(112, 175, 120, 45);
		regPanel.add(pwLabel);
		
		JLabel rePwd = new JLabel("Re-enter PassWord:");
		rePwd.setBorder(null);
		rePwd.setForeground(Color.BLACK);
		rePwd.setFont(new Font("Bernard MT Condensed", Font.BOLD, 26));
		rePwd.setOpaque(false);
		rePwd.setHorizontalAlignment(JTextField.LEFT);
		rePwd.setBounds(10, 235, 215, 45);
		regPanel.add(rePwd);
		
		newName = new TextField("");
		newName.setEditable(true);
		newName.setForeground(Color.BLACK);
		newName.setFont(new Font("Agency FB Bold", Font.PLAIN, 26));
		newName.setBounds(230,115,260,40);
		regPanel.add(newName);
		
		pwd1 = new TextField("");
		pwd1.setEditable(true);
		pwd1.setForeground(Color.BLACK);
		pwd1.setFont(new Font("Agency FB Bold", Font.PLAIN, 26));
		pwd1.setEchoChar('*');
		pwd1.setBounds(230,175,260,40);
		regPanel.add(pwd1);
		
		pwd2 = new TextField("");
		pwd2.setEditable(true);
		pwd2.setForeground(Color.BLACK);
		pwd2.setFont(new Font("Agency FB Bold", Font.PLAIN, 26));
		pwd2.setEchoChar('*');
		pwd2.setBounds(230,235,260,40);
		regPanel.add(pwd2);
		
		register = new JToggleButton(new ImageIcon("src/image/bRegister.jpg"));
		register.setPressedIcon(new ImageIcon("src/image/tbRegister.jpg"));
		register.addActionListener(this);
		register.setBounds(230, 330, 90, 45);
		regPanel.add(register);
		
		back1 = new JToggleButton(new ImageIcon("src/image/bBack.jpg"));
		back1.setPressedIcon(new ImageIcon("src/image/tbBack.jpg"));
		back1.addActionListener(this);
		back1.setBounds(400, 330, 90, 45);
		regPanel.add(back1);
		//end of drawing register interface
		
		/* drawing login interface
		 * adding action listeners for the buttons on it
		 */
		ImageIcon gameStart = new ImageIcon("src/image/GameStart.jpg");
		log = new ImagePanel(gameStart);
		log.setBounds(0, 0, 600, 400);
		log.setLayout(null);

		
		
		login = new JToggleButton(new ImageIcon("src/image/bLogin.jpg"));
		login.setPressedIcon(new ImageIcon("src/image/tbLogin.jpg"));
		login.addActionListener(this);
		login.setBounds(230, 320, 90, 45);
		log.add(login);
		
		back2 = new JToggleButton(new ImageIcon("src/image/bBack.jpg"));
		back2.setPressedIcon(new ImageIcon("src/image/bBack.jpg"));
		back2.addActionListener(this);
		back2.setBounds(400, 320, 90, 45);
		log.add(back2);
		
		JLabel nameLabel = new JLabel("UserName:");
		nameLabel.setBorder(null);
		nameLabel.setForeground(Color.BLACK);
		nameLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 26));
		nameLabel.setOpaque(false);
		nameLabel.setHorizontalAlignment(JTextField.LEFT);
		nameLabel.setBounds(100, 150, 120, 40);
		log.add(nameLabel);
		
		JLabel pwdLabel = new JLabel("PassWord:");
		pwdLabel.setBorder(null);
		pwdLabel.setForeground(Color.BLACK);
		pwdLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 26));
		pwdLabel.setOpaque(false);
		nameLabel.setHorizontalAlignment(JTextField.LEFT);
		pwdLabel.setBounds(100, 220, 120, 40);
		log.add(pwdLabel);
		
		userName = new TextField("");
		userName.setEditable(true);
		userName.setForeground(Color.BLACK);
		userName.setFont(new Font("Agency FB Bold", Font.PLAIN, 28));
		userName.setBounds(230, 150, 260, 40);
		log.add(userName);
	
		pwd = new TextField("");
		pwd.setEditable(true);
		pwd.setForeground(Color.BLACK);
		pwd.setFont(new Font("Agency FB Bold", Font.PLAIN, 28));
		pwd.setBounds(230, 220, 260, 40);
		pwd.setEchoChar('*');
		log.add(pwd);
		//end of drawing login interface
		
		// adding loggin panel and register panel to card layout
		pane.add(log,"logpanel");
		pane.add(regPanel,"regpanel");
		
		// when the frame is closed, system exits
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setVisible(true);
		
	
	}
		
	
	/* implements abstract method
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * action listener for buttons
	 */
	public void actionPerformed(ActionEvent event){
		Object source = event.getSource();
		if(source.equals(btconnect)){	
			String ip = host.getText();
			int pn = Integer.parseInt(port.getText());
			client.Create(ip, pn);				// create connection with server
			btNew.setVisible(true);
			btReturn.setVisible(true);
			host.setVisible(false);
			port.setVisible(false);
			btconnect.removeActionListener(this);
			ipLabel.setVisible(false);
			portLabel.setVisible(false);
			System.out.println("host ip: " + ip);
			System.out.println("port number: " + pn);
			JOptionPane.showMessageDialog(null, "Connected!");
		}
		else if(source.equals(register)){
			userData data1 = new userData();
			String status = "";
			client.SendToSocket("newUser");
			data1.userName = newName.getText();
			data1.pwd = pwd1.getText();
			if(!data1.pwd.equals(pwd2.getText())){
				JOptionPane.showMessageDialog(null, "The password is inconsistent! Please enter again");
				pwd1.setText("");
				pwd2.setText("");
			
			}
			else if(data1.userName.trim().equals("") || data1.pwd.trim().equals("")){
				JOptionPane.showMessageDialog(null, "User name or passwoord can not be empty!");
			}
			else{
				try{
					ObjectOutputStream toServer = new ObjectOutputStream(client.sock.getOutputStream());
					toServer.writeObject(data1);
					status = client.ReceiveFromSocket();
					System.out.println(status);
					if(status.trim().equals("exist")){
						JOptionPane.showMessageDialog(null, "Existed user name!");
						System.out.println(data1.userName);
						System.out.println(data1.pwd);
						newName.setText("");
						pwd1.setText("");
						pwd2.setText("");
					
					}
					else if(status.trim().equals("registered")){  // register successfully
						newName.setText("");
						pwd1.setText("");
						pwd2.setText("");
						userName.setVisible(true);
						pwd.setVisible(true);
						card.show(pane, "logpanel");
						
					}
				} 
				catch (InvalidClassException e2) {
					System.out.println("Invalid class!");
					
				} 
				catch (NotSerializableException e3) {
					System.out.println("Serializable error!");
					
				} 
				catch (IOException e4) {
					System.out.println("IO exception occur!");
			
				}
			}
		}
		
		else if(source.equals(quit)){
			System.exit(0);
		}
		
		else if(source.equals(back1)){
			card.show(pane, "connectpanel");
			newName.setText("");
			pwd1.setText("");
			pwd2.setText("");
		}
		else if(source.equals(login)){
			if(userName.getText().trim().equals("") || pwd.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "user name or password can not be empty!");
			}
			else{
				log();
			}
		}
		else if(source.equals(back2)){
			card.show(pane, "connectpanel");
			userName.setText("");
			pwd.setText("");
		}
		else if(source.equals(btNew)){
			card.show(pane, "regpanel");
			userName.setVisible(false);
			pwd.setVisible(false);
		}
		else if(source.equals(btReturn)){
			card.show(pane, "logpanel");
			userName.setVisible(true);
			pwd.setVisible(true);
		}
	}
		
		
		
	
	// called when login button is pressed
	private void log(){
		userData data = new userData();
		data.userName = userName.getText();
		data.pwd = pwd.getText();
		client.SendToSocket("reUser");
		
		try{
			ObjectOutputStream toServer = new ObjectOutputStream(client.sock.getOutputStream());
			toServer.writeObject(data);
			String status = client.ReceiveFromSocket();
			if(status.trim().equals("lSuccess")){            // login successfully, game begins
				start = true;
				JOptionPane.showMessageDialog(null, "game start!");
			}
			else if(status.trim().equals("CantFind")){
				JOptionPane.showMessageDialog(null, "User name does not exist!");
				userName.setText("");
				pwd.setText("");
			}
			else if(status.trim().equals("wrongPwd")){
				JOptionPane.showMessageDialog(null, "Invalid password! Please enter again");
				pwd.setText("");
			}
			else if(status.trim().equals("alreadyLogin")){
				JOptionPane.showMessageDialog(null, "User has already logged in!");
				userName.setText("");
				pwd.setText("");
			}
		}
	
		catch (InvalidClassException e2) {
			System.err.println("Invalid class!");
		} 
		catch (NotSerializableException e3) {
			System.err.println("Serializable error!");
		} 
		catch (IOException e4) {
			System.err.println("IO exception occur!");
		}
	}
	 public boolean getStart(){
		 return start;
	 }


}
