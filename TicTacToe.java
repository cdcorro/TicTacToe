

import java.util.*;
/**
 * 
 * @author Charles Corro
 *
 */
interface global{
	 
 	public static final int EMPTY = 0;
    public static final int O = 1;
    public static final int X = 2;
    public static final int DRAW = 3;
	
}

class block implements global{
	private int state;
	// default constructor
	public block() {
		state = 0;
	}
	/**
	 * 
	 * @param state = the proposed state
	 * if state is valid then set state
	 */
	public void setState(int state) {
		if((state == 1)||(state == 2)){
			this.state = state;
		}
	}
	/**
	 * 
	 * @return int = state of block
	 */
	public int getState() {
		return state; //dictates who has the block (X or O)
	}
	/**
	 * if state is 1 then O has it
	 * if state is 3 then X has it
	 * otherwise its empty
	 */
	public String toString() {
		if (state == 1) {
			return "O";
		}
		else if (state == 2) {
			return "X";
		}
		return " ";
	}
	
}

class board implements global{
	block b[][] = new block[3][3]; // a 3x3 of blocks
	int state; //dictates the winner
	/**
	 * default constructor that instantiates each block int the board of 3x3 blocks
	 */
	public board() {
		for(int i= 0;i<3 ;i++) {
			for(int j= 0;j<3 ;j++) {
				b[i][j] = new block();
			}
		}
	}
	/**
	 * when game is done shows the board without any numbers
	 * aka final board to show when won
	 */
	public void Display() {
		System.out.println(""+b[0][0]+"|"+b[0][1]+"|"+b[0][2]);
		System.out.println("-|-|-");
		System.out.println(""+b[1][0]+"|"+b[1][1]+"|"+b[1][2]);
		System.out.println("-|-|-");
		System.out.println(""+b[2][0]+"|"+b[2][1]+"|"+b[2][2]);
	}
	/**
	 * lists possible moves in terms of integers
	 */
	public void MovemakeBoard() {
		int h = 1;
		for(int i = 0; i <3;i++) {
			for(int j = 0; j <3;j++) {
				if(b[i][j].getState() == 0) {
					System.out.print(h++);
				}
				else {
					h++;
					System.out.print(b[i][j]);
				}
				if (j != 2) {
					System.out.print("|");
				}
				
			}
			System.out.println();
			if(i != 2) {
				System.out.println("-|-|-");
			}
		}
	}
	/**
	 * 
	 * @return int sets the state of the board to see who won
	 */
	public int getState() {
		return updateState();
	}
	/**
	 * 
	 * @param d = the column designated
	 * @return if a column is filled with the same states then return the state
	 */
	private int checkdown(int d) {
		if((b[0][d].getState() == b[1][d].getState())&&(b[1][d].getState() == b[2][d].getState())&&(b[0][d].getState()!=0)){
			return b[0][d].getState();
		}
		return 0;
	}
	/**
	 * 
	 * @param a = the row designated
	 * @return if a row is filled with the same states then return the state
	 */
	private int checkacross(int a) {
		if((b[a][0].getState() == b[a][1].getState())&&(b[a][1].getState() == b[a][2].getState())&&(b[a][0].getState()!=0)) {
			return b[a][0].getState();
			
		}
		return 0;
	}
	/**
	 * 
	 * @return if a diagonal is filled with the same states then return the state
	 */
	private int checkdiagonal() {
		if((b[0][0].getState() == b[1][1].getState())&&(b[1][1].getState() == b[2][2].getState())&&(b[2][2].getState()!=0)){
			return b[0][0].getState();
		}
		else if((b[2][0].getState() == b[1][1].getState())&&(b[1][1].getState() == b[0][2].getState())&&(b[0][2].getState()!=0)) {
			return b[2][0].getState();
		}
		return 0;
	}
	/**
	 * 
	 * @return return 3 if a draw 0 if moves can still be made
	 */
	private int checkDraw() {
		for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (b[i][j].getState() == 0) {
                    return 0;
                }
            }
        }
        return 3;
	}
	/**
	 * checks to see if anyone has one
	 * @returns the state of the player that one or 0 if stil ongoing
	 */
	public int updateState() {
		int state = checkdown(0)+checkacross(0)+checkdown(1)+checkacross(1)+checkdown(2)+checkacross(2);
		state += checkdiagonal();
		
		if(state != 0) {
			this.state = state;
		}
		else {
			this.state = checkDraw();
		}
		
		return this.state;
	}
	/**
	 * 
	 * @param n = a number from 1 -9 to designate which block to fill
	 * @param state = which player is making the move
	 * @return true if move is legal, false if not;
	 */
	public boolean MakeMove(int n, int state) {
		int i = 0;//rows
		int j = 0;//columns
		switch(n) {
			case 1:
				break;
			case 2:
				i = 0;
				j = 1;
				break;
			case 3:
				i = 0;
				j = 2;
				break;
			case 4:
				i = 1;
				j = 0;
				break;
			case 5:
				i = 1;
				j = 1;
				break;
			case 6:
				i = 1;
				j = 2;
				break;
			case 7:
				i = 2;
				j = 0;
				break;
			case 8:
				i = 2;
				j = 1;
				break;
			case 9:
				i = 2;
				j = 2;
				break;
		}
		if (b[i][j].getState() == 0) {
			b[i][j].setState(state);
			return true;
		}
		return false;
		
	}
}

class game implements global{
	board gb = new board(); //creates the board
    player plX;				//player X
    player plO;				//player O
    int turn;				//who turn it is
    int start;				//who starts the game
    boolean done;			//if intro is done
    
    public game() {
    	done = false;
    	turn = 0;
    	start = 0;
    	String n1 = ""; //name for player 1
    	String n2 = ""; //name for player 2
    	int choice = 0; //choice for mode
    	
    	Scanner scn = new Scanner(System.in);
    	System.out.println("Welcome to the Game of TIC-TAC-TOE!\n");
    	while(done == false) {
	    	System.out.println("Choose a game mode:");
	    	System.out.println("1)User VS User");
	    	System.out.println("2)User VS Computer");
	    	System.out.print("Please enter your choice: ");
	    	choice = scn.nextInt();
	    	if (choice == 1) { //player vs player
	    		System.out.print("Please enter Player1's name: ");
	    		scn.nextLine();
	    		n1 = scn.nextLine().trim();
	    		System.out.print("Please enter Player2's name: ");
	    		n2 = scn.nextLine().trim();
	    		done = true;
	    	}
	    	else if(choice == 2) {// player vs computer
	    		System.out.print("Please enter your Player's name: ");
	    		scn.nextLine();
	    		n1 = scn.nextLine().trim();
	    		n2 = "Computer";
	    		done = true;
	    	}
	    	else {
	    		System.out.println("Invalid input... please choose a valid input:");
	    		continue;
	    	}
    	}
    	if(choice == 1) { //creates 2 players
    		plX = new humanPlayer(gb,1,n1);
    		plO = new humanPlayer(gb,2,n2);
    	}
    	else {	//creates 1 player and 1 computer
    		plX = new humanPlayer(gb,1,n1);
    		plO = new AIPlayer(gb,2,n2);
    	}
    	
    }
    /**
     * starts the game in the board
     */
    public void start() { //starts the game
        int i = 0; //to exit the loop say who won
        Random  rd = new Random();
        start = rd.nextInt(2) + 1; //chooses a random integer number from 1-2
        if (start == 1) {
            System.out.println(plX.name + " wins the coin toss... O will start\n");
        }
        else {
            System.out.println(plO.name + " wins the coin toss... X will start\n");
        }
        turn = start; // whoever starts is that persons turn first
        while (i == 0) {
            if (turn == 1) {
                System.out.println("it is "+plX.name+"'s turn "+ ":");
                plX.play(gb); //play using that player
            }
            else {
                System.out.println("it is " + plO.name+ "'s turn" + ":");
                plO.play(gb); //play using that player
            }
            if (gb.getState() != 0) { // if someone one, then exit loop
                i = 1;
            }
            if (i == 0) { //otherwise
                if (turn == 1) { //switch to other persons turn
                    turn = 2;
                }
                else { //switch to other persons turn
                    turn = 1;
                }
            }
        }
        gb.Display(); // after finishing show final board
        if (gb.getState() == 3) {
            System.out.println("It's a DRAW! there is no winner...");
        }
        else if (turn == 1) { 
            System.out.println("Congratulations! The winner is " + plX.name);
        }
        else {
            System.out.println("Congratulations! The winner is " + plO.name);
        }
    }
}

abstract class player implements global{
	int symbol; // 1 = O 2 = X
	String name; //name of player
	board gb; //gameboard

	abstract public void play(board b); //make a move
	
	public String toString() { //return the name
		return name;
	}
}

class humanPlayer extends player{
	/**
	 * default constructor
	 * @param b = board
	 * @param s = symbol that was assigned
	 * @param name = name of player
	 */
	public humanPlayer(board b, int s, String name) {
		this.name = name;
		gb = b;
		symbol = s;
	}
	/**
	 * shows board of possible moves, then asks for input of what move to make
	 * and makes move chosen
	 */
	public void play(board b) {
	        Scanner scanner = new Scanner(System.in);
	        gb = b;
	        boolean move;
	        do{
	            gb.MovemakeBoard();
	            System.out.print("Please enter a number corresponding to a move you would like to make: ");
	            move = gb.MakeMove(scanner.nextInt(), symbol);
	            if (!move) {
	                System.out.println("Invalid move! try again...");
	            }
	        } while (!move);
	}
}

class AIPlayer extends player{
	/**
	 * default constructor
	 * @param b = board
	 * @param s = symbol that was assigned
	 * @param name = name of player
	 */
	public AIPlayer(board b,int s, String name) {
		this.name = name;
		gb = b;
		symbol = s;
	}

	/**
	 * makes move for computer
	 */
	public void play(board b) {
		gb = b;
		int n = 0;
        boolean move;
        do{
        	
            move = gb.MakeMove(randomMove(), symbol);
            if (!move) {
                System.out.println("Invalid move! try again...");
            }
            		
        } while (!move);
        
	}
	/**
	 * 
	 * @return an integer corresponding t a random legal move.
	 */
	public int randomMove() {
		int ub = 0;
        int[] lineboard = new int[9];
        Random random = new Random();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (gb.b[i][j].getState() == 0) {
                    lineboard[ub++] = 3 * i + 1 + j;
                }
            }
        }
        return lineboard[random.nextInt(ub)];
	}
	
}


public class TicTacToe {

	public static void main(String[] args) {
		game myGame = new game();

	    myGame.start();
	}
}

