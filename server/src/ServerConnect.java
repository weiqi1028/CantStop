
import java.io.*;
import java.util.Vector;
import java.net.*;
public class ServerConnect implements Serializable {
	private static final long serialVersionUID = -8965773902056088264L;
	public ServerSocket server;
	public Socket sock;
	private int port;
	public String string;
	private BufferedReader in;
	private PrintWriter out;
	private static Vector vList = new Vector();
	public static Vector userOnline = new Vector();
	private Object obj;
	private ObjectInputStream fromClient;
	public String UName;
	public int win;
	public int lose;
	public float rate;
	public long time;
	public String FirstName,SecondName,ThirdName;
	public float FirstRate,SecondRate,ThirdRate;
	public boolean enable = false;

	//Constructor
	public ServerConnect(int port){
		this.port = port;
	}

	//Build connection with client
	public boolean create(){
		while(true){
		server = null;
		try{
			server = new ServerSocket(port);
		}
		catch (IOException ioe){
			System.err.println(ioe+"Unable to listen on port : " + port);
			Serve4Game.server.log(ioe+"Unable to listen on port : " + port);
			System.exit(-1);
		}
		try{
			sock = server.accept();
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);
			Serve4Game.server.log("Build connection on port "+port);
			
		}
		catch(IOException ioe){
			System.err.println("Accept failed: " + ioe.getMessage());
			Serve4Game.server.log("Accept failed: " + ioe.getMessage());
			System.exit(-1);
		}
		String s = "";
		try{
		inner:
		while(!(s = ReceiveFromSocket()).equals(null)){
			if(s!=null){
				if(s.trim().equals("newUser")){
					RegisterUser();
					continue inner;
				}
				if(s.trim().equals("reUser")){
					Serve4Game.server.log("Returning user login");
					boolean f = serverLogin();
					if(!f)
						continue inner;
					else if(f){
						return true;
						}
					}
				}
			}
		}
		catch(NullPointerException e){
			return false;
		}
		}
		}
	
	//Release resources
	public void destroy(){
		try{
			in.close();
			out.close();
			sock.close();
			server.close();
		}
		catch(IOException ioe){
			Serve4Game.server.log("Unable to close writer, reader, or socket: " + ioe.getMessage());
		}	
	}
	
	//write to socket
	public void SendToSocket (String s){
		out.println(s); 
	}
	
	// read from socket
	public String ReceiveFromSocket(){
		try{
				string = in.readLine(); 
		}
		catch (IOException ioe){
			Serve4Game.server.log("Unable to read from or write to client: " + ioe.getMessage());
			return null;
		}
		return string;
	}
	
	//Register new user
	public boolean RegisterUser(){
		// Receive user data from client
		try{
			fromClient = new ObjectInputStream(sock.getInputStream());
		}
		catch(IOException e){
			System.err.println(e);
			return false;
		}
		try{
			
			obj = (Object)fromClient.readObject();
		}
		catch(IOException e){
			System.err.println(e);
			Serve4Game.server.log("IOException occur");
			return false;
		}
		catch(ClassNotFoundException e1){
			System.err.println("Read object error: "+ e1);
			Serve4Game.server.log("Read object error: "+ e1);
			return false;
		}
		//register:
		try{
			int flag = 0;
			userData newUser = (userData) obj;
			File uList = new File("src/txt/UserData.txt");
			if(uList.length()!=0){
				ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(uList));
				vList = (Vector)objInput.readObject();
				for(int i = 0; i<vList.size();i++){
					userData reg =(userData)vList.elementAt(i);
					if(reg.userName.equals(newUser.userName)){
						SendToSocket("exist");
						Serve4Game.server.log("Register failed, existed user name");
						flag = 1;
						newUser.userName = "";
						newUser.pwd = "";
						return false;
					}
	
				}
				objInput.close();
			}
			if(flag ==0){
				
				//add new user
				vList.addElement(newUser);
				
				// write user data to file
				FileOutputStream file = new FileOutputStream(uList);
				ObjectOutputStream objOut = new ObjectOutputStream(file);
				objOut.writeObject(vList);
				SendToSocket("registered");
				Serve4Game.server.log("User "+newUser.userName+" register successfully!");
				file.close();
				objOut.close();
				return true;
				
			}
		}
		catch(ClassNotFoundException e){
			System.err.println(e);
			Serve4Game.server.log("ClassNotFoundException occurs");
			return false;
		}
		catch(IOException e){
			System.err.println(e);
			Serve4Game.server.log("IOException occurs");
			return false;
		}
		return true;
		}
	
	//Login returning user
	public boolean serverLogin(){
		//Receive user data from client
		try{
			fromClient = new ObjectInputStream(sock.getInputStream());
		}
		catch(IOException e){
				Serve4Game.server.log("IOException occurs");
		}
		try{
			
			obj = (Object)fromClient.readObject();
		}
		catch(IOException e){
			System.err.println(e);
			Serve4Game.server.log("IOException occurs");
		}
		catch(ClassNotFoundException e1){
			System.err.println("Read object error: "+ e1);
			Serve4Game.server.log("Read Object error");
		}
		try{
			
			// read user data from file
			userData loginData = (userData) obj;
			FileInputStream UD = new FileInputStream("src/txt/UserData.txt");
			ObjectInputStream objInput1 = new ObjectInputStream(UD);
			vList = (Vector)objInput1.readObject();
			int find = 0;
			for(int i = 0; i<vList.size();i++){
				userData reg = (userData)vList.elementAt(i);
				
				//examine user name and password
					if(reg.userName.equals(loginData.userName)){
						Serve4Game.server.log("User information found");
						find = 1;
						if(!reg.pwd.equals(loginData.pwd)){
							SendToSocket("wrongPwd");
							Serve4Game.server.log("Login fail: wrong password");
							return false;
						}
						else if(reg.pwd.equals(loginData.pwd)){
							Serve4Game.server.log("password valid");
							int login_flag = 0;
							
							//examine whether the user has already logged in
							for(int j = 0; j<userOnline.size();j++){
								String _custName = ((userData)userOnline.elementAt(j)).userName;
								if(loginData.userName.equals(_custName)){
									login_flag = 1;
									SendToSocket("alreadyLogin");
									Serve4Game.server.log("Login failed: user already login");
									return false;
								}	
							}
							if(login_flag ==0){			// login successfully
								Serve4Game.server.log("User "+reg.userName+" login successfully");
								UName = reg.userName;
								win = reg.won;
								lose = reg.lost;
								rate = reg.rate;
								time = reg.time;
								userOnline.addElement(loginData);
								SendToSocket("lSuccess");
								return true;
							}
							
						}
					}
			}
			if(find ==0){
				SendToSocket("CantFind");
				Serve4Game.server.log("Can't find user name");	
			}
			UD.close();
			objInput1.close();
		}catch(ClassNotFoundException e){
			System.err.println(e);
		}catch(IOException e){
			System.err.println(e);
		}
		return false;
	}
	
	
	// update user data
	public void RefreshInfo(){
		try{
			Serve4Game.server.log("Refresh start");
			
			// read user data from file
			FileInputStream readFile = new FileInputStream("src/txt/UserData.txt");
			ObjectInputStream objInput2 = new ObjectInputStream(readFile);
			vList = (Vector)objInput2.readObject();
			int find = 0;
			
			//update data
			for(int i = 0; i<vList.size();i++){
				userData reg = (userData)vList.elementAt(i);
				if(reg.userName.equals(UName)){
					find = 1;
					vList.removeElementAt(i);
					reg.won = win;
					reg.lost = lose;
					reg.time = time;
					rate = (float)win/((float)win+(float)lose);
					reg.rate = rate;
					vList.addElement(reg);
					break;
				}
			}
			
			// write user data back to file
			File ff = new File("src/txt/UserData.txt");
			FileOutputStream file1 = new FileOutputStream(ff);
			ObjectOutputStream objOut1 = new ObjectOutputStream(file1);
			objOut1.writeObject(vList);
			for(int i = 0; i<vList.size();i++){
				userData reg = (userData)vList.elementAt(i);
				Serve4Game.server.log("Data after Refresh:  "+reg.userName+" Win:"+reg.won + " Lose:" +reg.lost + " Winning Rate:"+ reg.rate);
			}
		}
		catch(ClassNotFoundException e){
			System.err.println(e);
			Serve4Game.server.log("Class not found exception occur!");
		}
		catch(FileNotFoundException e1){
			System.err.println(e1);
			Serve4Game.server.log("File not found exception occur!");
		}
		catch(IOException e2){
			System.err.println(e2);
			Serve4Game.server.log("IO exception occur!");
		}
		Serve4Game.server.log("Refresh Over");
		}
	
	//sort user by winning rate, won, lost, and time
	public void sort(){
		try{
			Serve4Game.server.log("Sort user data");
			
			// read user from file
			File flist = new File("src/txt/UserData.txt");
			FileInputStream fs = new FileInputStream(flist);
			ObjectInputStream objInput = new ObjectInputStream(fs);
			vList = (Vector)objInput.readObject();
			int num = vList.size();
			userData[] data = new userData[num];
			int k;
			float keyRate;
			int keyWon;
			long keyTime;
			userData key = new userData();

			for(int i = 0; i<num; i++){
				data[i] = new userData();
				data[i] = (userData)vList.elementAt(i);
			}
			for(int j = 1; j<num; j++){
				//sort by winning rate
				keyRate = data[j].rate;
				key = data[j];
				k = j - 1;
				while(k>=0 && data[k].rate<keyRate){
					data[k+1] = data[k];
					k--;
				}
				data[k+1] = key;
		
			}
			for(int j = 1; j<num; j++){
				//if the winning rate is same, sort by won
				keyRate = data[j].rate;
				keyWon = data[j].won;
				key = data[j];
				k = j - 1;
				while(k>=0 && data[k].rate == keyRate && data[k].won<keyWon){
					data[k+1] = data[k];
					k--;
				}
				data[k+1] = key;
			}
			for(int j = 1; j<num; j++){
				//if the winning rate is same, and won is also same, sort by time
				keyRate = data[j].rate;
				keyWon = data[j].won;
				keyTime = data[j].time;
				key = data[j];
				k = j - 1;
				while(k>=0 && data[k].rate == keyRate && data[k].won == keyWon && data[k].time>keyTime){
					data[k+1] = data[k];
					k--;
				}
				data[k+1] = key;
			}
			
			
			vList.removeAllElements();
			for(int m = 0; m<num; m++)
				vList.addElement(data[m]);
			
			// record information of first, second, and third players
			if(vList.size()>=1){
				key = (userData)vList.elementAt(0);
				Serve4Game.server.log("First:"+data[0].userName+" Win:" + data[0].won+" Lose:" +data[0].lost +" Winning Rate:"+data[0].rate);
				FirstName = key.userName;
				FirstRate = key.rate * 100;
			}
			if(vList.size()>=2){
				Serve4Game.server.log("Second:"+data[1].userName+" Win:" + data[1].won+" Lose:" +data[1].lost +" Winning Rate:"+data[1].rate);
				key = (userData)vList.elementAt(1);
				SecondName = key.userName;
				SecondRate = key.rate * 100;
			}
			if(vList.size()>=3){
				key = (userData)vList.elementAt(2);
				Serve4Game.server.log("Third: "+data[2].userName +" Win:"+ data[2].won +" Lose:"+data[2].lost +" Winning Rate:"+data[2].rate);
				ThirdName = key.userName;
				ThirdRate = key.rate * 100;
			}
			
			// write the sorted user data back to file
			FileOutputStream file = new FileOutputStream("src/txt/UserData.txt");
			ObjectOutputStream objOutput = new ObjectOutputStream(file);
			objOutput.writeObject(vList);
		}
		catch(IOException ioe){
			System.err.println(ioe);
		}
		catch (ClassNotFoundException e) {
			System.err.println(e);
		}
		Serve4Game.server.log("Sort complete");
	}
	
	
}



