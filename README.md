CantStop
========

Can't stop board game

Firstly, Server should create a game as following:
  1. Execute server.
  2. Server will get its IP address and name automatically, you need to input two port numbers, and then click "Create" button to create a game. Note: port number 1 will be connected first.
  3. Server log is displayed on the server interface, click "Refresh" button to refresh server log, click "Protocol" button bo view Can't Stop protocol, click "SaveLog" button to save server log, click "User OL" button to check who is on line.

Then, client should be executed to connect to server and begin the game as following:
  1. Execute client.
  2. Firstly, you will enter Login interface, you should enter host IP or name, and port number, then click "Connect" button to connect to server. If you are a new user, click "New user" button to register a new account for the game, if you are a returning user, click "Returning user" button to login.
  3. After two players logged in, game start.
  4. When the game ends, your information will be displayed on the game over interface, also with the top three players. You can choose to restart the game or exit. Note: the game would not be restarted unless both two players choose to restart.

**************************************************************************************************

Note: 
  1. UserData.txt is on the server's side, it contains all the players' information (eg. username, password, rank...).
  2.log.txt is on the server's side, it contains the saved sever's log.
  3.image folder is on the client's side, it contains all the pictures client needs.
  4. sound folder is on the client's side, it contains all the sounds client needs.
  5. help.txt is on the client's side.
  6. protocol.txt in on the server's side.

Server's main method is in Serve4Game.java file, client's main method is in Client.java file.
