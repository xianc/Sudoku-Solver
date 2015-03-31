import java.io.*;
import java.util.*;
public class Game {
	public  Cell[][] board;

	public boolean correct;
	public int gameId;
	public int startpairfindx;
	public int startpairfindy;

	public Game(Cell[][] board, int gameId, int startpairfindx, int startpairfindy){
		if (board==null) {
			this.board = new Cell[9][9];
			importPuzzle();
		}
		else this.board = board;

		this.startpairfindx = startpairfindx;
		this.startpairfindy = startpairfindy;
		this.gameId = gameId;
		Main.addGame(this);
		solvePuzzle(81);
	}

	void importPuzzle() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter filename for a board:");
		String fileName = scan.nextLine();
		Scanner scanFile = null;

		try {
			scanFile = new Scanner (new File (fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist");
			e.printStackTrace();
		}

		int row = -1;
		int column = -1;
		while(scanFile.hasNext()){	  
			StringTokenizer str = new StringTokenizer(scanFile.nextLine());
			row++;
			column = -1;
			while(str.hasMoreTokens()){
				String s = str.nextToken();
				column++;
				// System.out.println(s);
				if (!s.equals("-")){
					board[row][column] = new Cell(Integer.parseInt(s), row, column);
				}
				else board[row][column] = new Cell(0, row, column);
			}
		}	
	}


	void solvePuzzle(int startingCells){
		int remainingCells = 0;
		for(int i=0; i<9; i++){
			for (int j=0; j<9; j++){
				if (!board[i][j].findPossibilities()) remainingCells++;
			}
		}	
		if (remainingCells<startingCells) {

			System.out.println("Found less remaining cells: "+ remainingCells);
			printPuzzle();
			if (remainingCells !=0) solvePuzzle(remainingCells);
			else {
				System.out.println("Solution found! Total guesses: "+ Main.getCount());
				printPuzzle();
				System.exit(0);
			}

		}

		else if (remainingCells == startingCells){

			System.out.println("Solver stuck! Will attempt dichotomy");
			pickCheck();
			System.out.println("Game "+ gameId + " finished");

		}
	}

	
	/*void pickCheck(){
		int curSize=0;
		int i,j;
		int row=0;
		int col=0;
		int prevSize=9;

		//pick cell with least possibilities
		for(i=startpairfindx+1; i<9; i++){
			for (j=startpairfindy; j<9; j++){
				curSize=board[i][j].possibilities.size();
				if (curSize<prevSize && curSize!=1){
					prevSize=curSize;
					row=i;
					col=j;
				}
			}
		}
		System.out.println("Cell of size: "+ prevSize);
		dichotomy(row,col);
	}*/
	
	void pickCheck(){

		int i; 
		int j;
		for(i=startpairfindx+1; i<9; i++){
			for (j=startpairfindy; j<9; j++){
				if (board[i][j].possibilities.size()==2) {
					System.out.println("Cell with 2 possibilities found");
					dichotomy(i,j);

				}
			}
		}

	}
	void dichotomy(int i, int j){
		int removed = board[i][j].possibilities.get(0);
		int kept = board[i][j].possibilities.get(1);
		System.out.println("Game " +  gameId +" : cell at "+i+","+j+ " has "+ board[i][j].possibilities);
		Cell[][] clone = new Cell[9][9]; 

		for(int o=0; o<9; o++){
			for (int p=0; p<9; p++){
				clone[o][p]=new Cell(board[o][p].possibilities,o,p);
			}
		}

		clone[i][j].possibilities.remove((Integer)removed);
		Main.incGuessCount();
		new Game(clone, gameId+1, i, j);
		
		System.out.println("Back to parent game "+ gameId);
		Cell[][] clone1 = new Cell[9][9]; 

		for(int o=0; o<9; o++){
			for (int p=0; p<9; p++){
				clone1[o][p]=new Cell(board[o][p].possibilities,o,p);
			}
		}
		
		System.out.println("Changing path (second choice");
		System.out.println("now cell at "+i+","+j+ " has " + clone1[i][j].possibilities);
		clone1[i][j].possibilities.remove((Integer)kept);
		System.out.println("removed possibility "+ kept);
		System.out.println("now cell at "+i+","+j+ " has " + clone1[i][j].possibilities);
		
		Main.incGuessCount();
		new Game(clone1, gameId+1, i, j);
		System.out.println("Back to parent game "+ gameId + "! (second choice back)");

	}


	void printPuzzle(){
		for(int i=0; i<9; i++){
			System.out.println("");
			for (int j=0; j<9; j++){
				if (board[i][j].getValue() == 0) System.out.print("- ");
				else System.out.print(board[i][j].getValue()+ " ");
				if ((j+1)%3==0 && j!=0 && j!=8)
					System.out.print(" | ");

			}   
			if ((i+1)%3==0 && i!=0 && i!=8)
				System.out.print("\n------------------------");


		}
		System.out.println("");


	}
}
