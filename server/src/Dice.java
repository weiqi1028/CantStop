/**
 * @author Dan Han, Weiqi Zhang, Abdulaziz Alshahrani
 * This class is to generate four dices
 */
import java.util.Random;
	public class Dice {
    	private int i;
    	private int dice1, dice2, dice3, dice4;
    	
    	//This method is used for generating a random number between 1 and 6
    	private int generate(){
		Random rand=new Random();
		i=rand.nextInt(6)+1;
		return i;
    	}
    	
    	//generate four dices
    	public void Roll(){
    		dice1 = generate();
    		dice2 = generate();
    		dice3 = generate();
    		dice4 = generate();
    	}
    	
    	//return dices
    	public int Dice1(){
    		return dice1;
    	}
    	public int Dice2(){
    		return dice2;
    	}
    	public int Dice3(){
    		return dice3;
    	}
    	public int Dice4(){
    		return dice4;
    	}
   	}
