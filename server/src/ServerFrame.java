/**
 * @author Dan Han, Weiqi Zhang
 * Server Interface for displaying server log and server information, and set
 * port number
 * Using manual layout
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class ServerFrame extends JFrame{
	private static final long serialVersionUID = -8936397327038098620L;
	private JPanel pnlServer, pnlServerInfo;
	private JLabel lblLog,lblServerName,lblIP,lblPort1, lblPort2;
	private JButton btnExit,btnRefresh,btnProtocol,btnSaveLog,btnUserOnline;
	public JButton btnSave;
	private JTextField txtIP,txtServerName;
	public JTextField txtPort1,txtPort2;
	public boolean flagPort = false;
	public int portNum1,portNum2;
	public JTextArea taLog;
	private String IP,Address;

	//Constructor
	public ServerFrame() {
		super("Can't Stop Server");
		setSize(450, 500);
		
		// when frame is closed, system exits
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();// Display at the center of the screen
		Dimension fra = this.getSize();
		if (fra.width > scr.width) {
			fra.width = scr.width;
		}
		if (fra.height > scr.height) {
			fra.height = scr.height;
		}
		this.setLocation((scr.width - fra.width) / 2,
				(scr.height - fra.height) / 2);
		this.setBackground(new Color(52, 130, 203));
		this.setLayout(null);
		
		JPanel pnlBK = new JPanel();
		pnlBK.setLayout(null);
		pnlBK.setBackground(new Color(52, 130, 203));
		pnlBK.setBounds(0, 0, 450, 500);
		this.add(pnlBK);
		
		//Drawing information area for server 
		pnlServer = new JPanel();
		pnlServer.setLayout(null);
		pnlServer.setBackground(new Color(52, 130, 203));
		pnlServer.setBounds(25, 20, 400, 230);
		pnlBK.add(pnlServer);
		
		lblIP = new JLabel("Server IP:");
		lblIP.setForeground(Color.BLACK);
		lblIP.setFont(new Font("Arial", 0, 20));
		lblIP.setBounds(5, 10, 190, 40);
		lblIP.setBackground(Color.decode("#d6f4f2"));
		lblIP.setHorizontalAlignment(SwingConstants.LEFT);
		lblIP.setOpaque(true);
		pnlServer.add(lblIP);
		
		txtIP = new JTextField(20);
		txtIP.setForeground(Color.BLACK);
		txtIP.setBackground(Color.decode("#d6f4f2"));
		txtIP.setBounds(200, 10, 190, 40);
		txtIP.setOpaque(true);
		txtIP.setEditable(false);
		try{
		InetAddress addr = InetAddress.getLocalHost();
		IP=addr.getHostAddress().toString();		//get server's IP
		Address=addr.getHostName().toString();		//get server's Name
		}
		catch(Exception e){
			System.err.println("Bad IP Address!"+e);
			} 
		txtIP.setFont(new Font("Arial", 0, 20));
		txtIP.setHorizontalAlignment(JTextField.CENTER);
		txtIP.setText(IP);
		pnlServer.add(txtIP);
		
		lblServerName = new JLabel("Server Name:");
		lblServerName.setForeground(Color.BLACK);
		lblServerName.setFont(new Font("Arial", 0, 20));
		lblServerName.setBounds(5, 55, 190, 40);
		lblServerName.setBackground(Color.decode("#d6f4f2"));
		lblServerName.setHorizontalAlignment(SwingConstants.LEFT);
		lblServerName.setOpaque(true);
		lblServerName.setBorder(new LineBorder(Color.BLACK));
		pnlServer.add(lblServerName);
		
		txtServerName = new JTextField(20);
		txtServerName.setForeground(Color.BLACK);
		txtServerName.setBackground(Color.decode("#d6f4f2"));
		txtServerName.setBounds(200, 55, 190, 40);
		txtServerName.setOpaque(true);
		txtServerName.setEditable(false);
		txtServerName.setFont(new Font("Arial", 0, 20));
		txtServerName.setHorizontalAlignment(JTextField.CENTER);
		txtServerName.setText(Address);
		pnlServer.add(txtServerName);
		
		lblPort1 = new JLabel("Input Port Numeber 1:");
		lblPort1.setForeground(Color.BLACK);
		lblPort1.setFont(new Font("Arial", 0, 20));
		lblPort1.setBounds(5, 100, 220, 40);
		lblPort1.setBackground(Color.decode("#d6f4f2"));
		lblPort1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPort1.setOpaque(true);
		pnlServer.add(lblPort1);
		
		lblPort2 = new JLabel("Input Port Numeber 2:");
		lblPort2.setForeground(Color.BLACK);
		lblPort2.setFont(new Font("Arial", 0, 20));
		lblPort2.setBounds(5, 145, 220, 40);
		lblPort2.setBackground(Color.decode("#d6f4f2"));
		lblPort2.setHorizontalAlignment(SwingConstants.LEFT);
		lblPort2.setOpaque(true);
		pnlServer.add(lblPort2);
		
		txtPort1 = new JTextField("");
		txtPort1.setForeground(Color.BLACK);
		txtPort1.setFont(new Font("Arial", 0, 20));
		txtPort1.setBounds(230, 100, 160, 40);
		txtPort1.setBackground(Color.decode("#d6f4f2"));
		txtPort1.setHorizontalAlignment(SwingConstants.CENTER);
		txtPort1.setOpaque(true);
		txtPort1.setEditable(true);
		pnlServer.add(txtPort1);
		
		txtPort2 = new JTextField("");
		txtPort2.setForeground(Color.BLACK);
		txtPort2.setFont(new Font("Arial", 0, 20));
		txtPort2.setBounds(230, 145, 160, 40);
		txtPort2.setBackground(Color.decode("#d6f4f2"));
		txtPort2.setHorizontalAlignment(SwingConstants.CENTER);
		txtPort2.setOpaque(true);
		txtPort2.setEditable(true);
		pnlServer.add(txtPort2);
		
		btnExit = new JButton("Exit");
		btnExit.setForeground(Color.BLACK);
		btnExit.setFont(new Font("Arial", 0, 20));
		btnExit.setBounds(205, 190, 190, 40);
		btnExit.setHorizontalAlignment(SwingConstants.CENTER);
		btnExit.setOpaque(true);
		pnlServer.add(btnExit);
		
		//Action listener for "Exit" button
		btnExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.exit(0);
			}
		});
		
		btnSave = new JButton("Create");
		btnSave.setForeground(Color.BLACK);
		btnSave.setFont(new Font("Arial", 0, 20));
		btnSave.setBounds(5, 190, 190, 40);
		btnSave.setHorizontalAlignment(SwingConstants.CENTER);
		btnSave.setOpaque(true);
		pnlServer.add(btnSave);
		
		//Action listener for "Create" button 
		btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				try{
				String port1 = "",port2 = "";
				port1 = txtPort1.getText();
				port2 = txtPort2.getText();
				if(port1.equals(null) ||port2.equals(null)){
					JOptionPane.showMessageDialog(null, "Illegal Port Number!");
					txtPort1.setText("");
					txtPort2.setText("");
				}
				else if(!port1.equals(null)&& !port2.equals(null)){
				
				portNum1 = Integer.parseInt(port1);
				portNum2 = Integer.parseInt(port2);
				if(portNum1>1024&&portNum2>1024&&portNum1!=portNum2){
					txtPort1.setEditable(false);
					txtPort2.setEditable(false);
					btnSave.setEnabled(false);
					flagPort = true;
				}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Illegal Port Number!");
					txtPort1.setText("");
					txtPort2.setText("");
				}
				}
				catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null, "Port number cannot be empty");
				}
			}
		});
		//End of drawing information area for server
		
		//Drawing log area for server
		pnlServerInfo = new JPanel();
		pnlServerInfo.setLayout(null);
		pnlServerInfo.setBackground(new Color(52, 130, 203));
		pnlServerInfo.setFont(new Font("Arial", 0, 12));
		pnlServerInfo.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(""), BorderFactory
						.createEmptyBorder(1, 1, 1, 1)));
		pnlServerInfo.setBounds(25, 260, 400, 200);
		pnlBK.add(pnlServerInfo);

		lblLog = new JLabel("Server Log");
		lblLog.setForeground(Color.YELLOW);
		lblLog.setFont(new Font("Arial", 0, 20));
		lblLog.setBounds(5, 5, 265, 30);
		lblLog.setHorizontalAlignment(SwingConstants.CENTER);
		lblLog.setOpaque(false);
		pnlServerInfo.add(lblLog);
		
		taLog = new JTextArea(8,10);
		
		taLog.setForeground(Color.BLACK);
		taLog.setEditable(false);
		taLog.setFont(new Font("Arial", 0, 16));
		taLog.setCaretPosition(taLog.getText().length());
		taLog.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(taLog);
		scroll.setBounds(5, 35, 270, 160);
		pnlServerInfo.add(scroll);
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.setForeground(Color.BLACK);
		btnRefresh.setFont(new Font("Arial", 0, 18));
		btnRefresh.setBounds(280, 35, 110, 35);
		btnRefresh.setHorizontalAlignment(SwingConstants.CENTER);
		btnRefresh.setOpaque(true);
		pnlServerInfo.add(btnRefresh);
		
		//Action listener for "Refresh" button
		btnRefresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				taLog.setText("");
			}
		});
		
		btnProtocol = new JButton("Protocol");
		btnProtocol.setForeground(Color.BLACK);
		btnProtocol.setFont(new Font("Arial", 0, 18));
		btnProtocol.setBounds(280, 75, 110, 35);
		btnProtocol.setHorizontalAlignment(SwingConstants.CENTER);
		btnProtocol.setOpaque(true);
		pnlServerInfo.add(btnProtocol);
		
		//Action listener for "Protocol" button
		btnProtocol.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				JFrame Fprotocol = new JFrame("Can't Stop Protocol");
				Fprotocol.setSize(400, 400);
				int wp = (Toolkit.getDefaultToolkit().getScreenSize().width - 400)/2;
				int wh = (Toolkit.getDefaultToolkit().getScreenSize().height - 400)/2;
				Fprotocol.setLocation(wp, wh);
				
				JTextArea textProtocol = new JTextArea(20, 40);
				textProtocol.setEditable(false);
				textProtocol.setLineWrap(true);
				JPanel panelProtocol = new JPanel();
				JScrollPane scroll = new JScrollPane(textProtocol);
				panelProtocol.add(scroll);
				Fprotocol.add(panelProtocol);
				try{
					BufferedReader br = new BufferedReader(new FileReader("src/txt/protocol.txt"));
					String s = "";
					while((s = br.readLine())!=null){
						textProtocol.append(s);
						textProtocol.append("\n");
						textProtocol.append("\n");
					}
				}
				catch(IOException ioe){
					System.err.println(ioe);
				}
				Fprotocol.pack();
				Fprotocol.setVisible(true);
			}
		});
		
		btnSaveLog = new JButton("SaveLog");
		btnSaveLog.setForeground(Color.BLACK);
		btnSaveLog.setFont(new Font("Arial", 0, 18));
		btnSaveLog.setBounds(280, 115, 110, 35);
		btnSaveLog.setHorizontalAlignment(SwingConstants.CENTER);
		btnSaveLog.setOpaque(true);
		pnlServerInfo.add(btnSaveLog);
		
		//Action listener for "Save Log" button
		btnSaveLog.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				saveLog();
			}
		});
		
		btnUserOnline = new JButton("User OL");
		btnUserOnline.setForeground(Color.BLACK);
		btnUserOnline.setFont(new Font("Arial", 0, 18));
		btnUserOnline.setBounds(280, 155, 110, 35);
		btnUserOnline.setHorizontalAlignment(SwingConstants.CENTER);
		btnUserOnline.setOpaque(true);
		pnlServerInfo.add(btnUserOnline);
		
		//Action listener for "User OL" button
		btnUserOnline.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				userData reg1 = new userData();
				userData reg2 = new userData();
				if(ServerConnect.userOnline.size()>=1)
					reg1 = (userData)ServerConnect.userOnline.elementAt(0);
				if(ServerConnect.userOnline.size()>=2)
					reg2 = (userData)ServerConnect.userOnline.elementAt(1);
				JOptionPane.showMessageDialog(null, "Player1: " + reg1.userName 
						+ "\n" + "Player2: " + reg2.userName );
			}
		});
		//End of drawing log area for server
		
		this.setVisible(true);
	}
	
	//Method for add server information to log area
	public void log(String s){
		String newta = taLog.getText();
		newta += ("\n"+s);
		taLog.setText(newta);
	}
	
	//Method for save current log in a .txt file
	protected void saveLog() {
		try {
			FileOutputStream fileoutput = new FileOutputStream("src/txt/log.txt",true);
			String temp = taLog.getText();
			fileoutput.write(temp.getBytes());
			fileoutput.close();
			JOptionPane.showMessageDialog(null, "Saved in log.txt");
		} 
		catch (Exception e) {
			System.err.println(e);
		}
	}
}
