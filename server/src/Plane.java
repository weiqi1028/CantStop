/**
 * @author Dan Han, Weiqi Zhang, Abdulaziz Alshahrani
 * This class is to record the Plane state of each player
 */
public class Plane {
	static final protected int totle[] = {3,5,7,9,11,13,11,9,7,5,3};
	static protected boolean conquer[] = {false, false,false,false,false,false,false,false,false,false,false};
	private boolean iConquer[] = {false, false,false,false,false,false,false,false,false,false,false};
	static protected int token;
	static protected int occu1=0 , occu2 = 0, occu3 = 0;
    private int current[]= {0,0,0,0,0,0,0,0,0,0,0};
    public int NumOfConquer = 0;
	public int previous[]= {0,0,0,0,0,0,0,0,0,0,0};
	
	//Initial the plane
    public void BoardInitial(){
    	token = 0;
    	occu1 = occu2 = occu3 = 0;
    	for(int i = 0;i <11;i++){
    		conquer[i] = false;
    		iConquer[i]=false;
    		current[i] = 0;
    		previous[i]=0;
    	}
    	NumOfConquer = 0;
    }
    
    /*Initial the plane at the beginning of every turn, such as
     * refresh the token and current state 
     */
	public void initial(){
		System.arraycopy(previous, 0, current, 0, 11);
		token = 0 ;
		occu1 = occu2 = occu3 = 0;
	}
	
	/**
	 * Change the current state of the plane on the column corresponding to the 
	 * @param num, at the same time change the value of tokens.
	 */
	public void ChangeCurrent(int num){
		if( occu1 == 0 && occu2 == 0 && occu3 == 0){
			occu1 = num ;
			token=1;
		}
		else if(occu1 != 0 && occu2 == 0 && occu3 == 0){
			if(occu1 != num){
				occu2 = num;
				token =2;
			}
		}
		else if(occu1 != 0 && occu2 != 0 && occu3 == 0){
			if(occu1 != num && occu2 != num){
				occu3 = num;
				token =3 ;
			}
		}
		current[num-2]++;
		return;
	}
	
	//This method is to record the state of the plane after a turn
	public void ChangePrevious(){
		System.arraycopy(current, 0, previous, 0, 11);
		for(int i = 0; i<11; i++){
			if(previous[i]==totle[i]){
				conquer[i]=true;
				iConquer[i]=true;
			}
		}
	}
	
	/* Return the array current[] of the plane, in which records the
	 * current state of each column 
	 */
	public int[] CurrentBoard(){
		return current;
	}
	
	/* Return the array conquer[] of the plane, in which records whether
	 * the corresponding columns have been conquered
	 */
	public boolean[] Conquer(){
		return conquer;
	}
	
	/*Judge whether the player win the game
	 */
	public boolean WinerOrNot(){
		for(int i=0;i<11;i++){
			if(iConquer[i])
				NumOfConquer++;
		}
		if(NumOfConquer>=3){
			NumOfConquer = 0;
			return true;
		}
		else{	
			NumOfConquer = 0;
			return false;
		}
	}
}
