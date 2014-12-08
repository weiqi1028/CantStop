/**
 * @author Dan Han, Weiqi Zhang
 * This class is used to judge whether the two numbers the players chose
 * are legal, and whether the number can make a legal move or not
 */
public class Judge {
	public int num1 , num2, err;
	public boolean bool1 = false,bool2= false;
	
	/**
	 * This method is used to judge whether the two numbers the player chose can
	 * form a pair or not
	 * @param n1 and @param n2 is the two numbers player chose
	 * @return is an integer. If it is equal to 0, the input is legal; or it should
	 *  return err to player
	 */
	public int combine(int n1,int n2){
		num1 = 0; 
		num2 = 0;
		//Whether the numbers are out of range
		if(n1<2||n1>12||n2<2||n2>12){
			bool1 = bool2 = false;
			return 1;
		}
		//Whether the numbers can form a pair
		if(((n1 == (Serve4Game.roll1 + Serve4Game.roll2 ))&& (n2 == (Serve4Game.roll3 + Serve4Game.roll4)))||
				((n2 == (Serve4Game.roll1 + Serve4Game.roll2)) &&(n1 == (Serve4Game.roll3 + Serve4Game.roll4)))){
				if(n1==(Serve4Game.roll1 + Serve4Game.roll2 )&& n2 ==( Serve4Game.roll3 + Serve4Game.roll4)){
					num1 = n1;
					num2 = n2;
					return 0;
				}
				else if((n2 == (Serve4Game.roll1 + Serve4Game.roll2)) &&( n1 == (Serve4Game.roll3 + Serve4Game.roll4))){
					num1 = n2; 
					num2 = n1;
					return 0;
				}
		}
		else if(((n1 == (Serve4Game.roll1 + Serve4Game.roll3 ))&& (n2 == (Serve4Game.roll2 + Serve4Game.roll4)))||
				((n2 == (Serve4Game.roll1 + Serve4Game.roll3)) &&( n1 == (Serve4Game.roll2 + Serve4Game.roll4)))){
				if((n1 == (Serve4Game.roll1 + Serve4Game.roll3 ))&& (n2 == (Serve4Game.roll2 + Serve4Game.roll4))){
					num1 = n1;
					num2 = n2;
					return 0;
				}
				else if((n2 == (Serve4Game.roll1 + Serve4Game.roll3 ))&& (n1 == (Serve4Game.roll2 + Serve4Game.roll4))){
					num1 = n1;
					num2 = n2;
					return 0;
				}
		}
		else if(((n1 == (Serve4Game.roll1 + Serve4Game.roll4 ))&& (n2 == (Serve4Game.roll2 + Serve4Game.roll3)))||
				((n2 == (Serve4Game.roll1 + Serve4Game.roll4)) &&( n1 == (Serve4Game.roll2 + Serve4Game.roll3)))){
				if((n1 == (Serve4Game.roll1 + Serve4Game.roll4 ))&& (n2 == (Serve4Game.roll2 + Serve4Game.roll3))){
					num1 = n1;
					num2 = n2;
					return 0;
				}
				else if((n2 == (Serve4Game.roll1 + Serve4Game.roll4 ))&& (n1 == (Serve4Game.roll2 + Serve4Game.roll3))){
					num1 = n1;
					num2 = n2;
					return 0;
				}
		}
		return 2;	
	}
	/**
	 * This method is to judge whether the number is legal to make a move 
	 * on certain player's move
	 * @param m is the number chosen
	 * @param Board is the corresponding board of the player
	 * @return is boolean, which is true if can make a move
	 */
	public boolean judger(int m, Plane Board){
		if(Plane.token == 3){
			if(m!=Plane.occu1 && m !=Plane.occu2 && m != Plane.occu3){
				m =0;
				return false;
			}
		}
		if(Board.CurrentBoard()[m-2] == Plane.totle[m-2]){
			m = 0;
			return false;
		}
		if(Plane.conquer[m-2]){
			m = 0;
			return false;
		}
		return true;
	}
	/**
	 * This method calls method combine() and judger() to determine whether the two numbers
	 * as a whole are legal too make a move
	 * @param n1 and @param n2 are the two numbers
	 * @param Board is the current Board of the player
	 * @return is a integer, if it's 0, the player can make a move using the two numbers;
	 * or it will send err to player
	 */
	public int judge(int n1, int n2,Plane Board ){
		err = combine(n1,n2);
		if(err == 0){
		if(num1!=0)
			bool1=judger(num1, Board);
		if(num2!=0)
			bool2=judger(num2, Board);
		if(!bool1&&!bool2){
			err=3;
			}
		}
		return err;
	}
}
