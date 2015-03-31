import java.util.ArrayList;
import java.util.Arrays;


public class Cell {
	int row;
	int column;
	boolean given = false;

	ArrayList<Integer> possibilities = new ArrayList<Integer>();

	public Cell(int start, int row, int column){
		if (start == 0) possibilities = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		else {
			possibilities.add(start);
			given = true;
		}
		this.row = row;
		this.column = column;
	}

	public Cell(ArrayList<Integer> start, int row, int column){
		possibilities=new ArrayList<Integer>();
		for (int i=0; i<start.size(); i++){
			possibilities.add(Integer.valueOf(start.get(i)));
		}
		if (start.size()==1) {
			given = true;
		}

		this.row = row;
		this.column = column;
	}

	int getValue(){
		if (isSingle()) return possibilities.get(0);
		else return 0;
	}

	boolean isSingle(){
		if (possibilities.size()==1) return true;
		else return false;
	}

	boolean isSolved(){
		if (given) return true; //gotta be solved
		if (isSingle()){
			for (int i = 0; i<9; i++){ //go look at all columns in a row
				if (Main.getCurrentGame().board[row][i]!=null && Main.getCurrentGame().board[row][i]!=this && Main.getCurrentGame().board[row][i].isSingle() && this.getValue() == Main.getCurrentGame().board[row][i].getValue() )
					return false;
			}
			for (int j = 0; j<9; j++){// all rows in a column
				if (Main.getCurrentGame().board[j][column]!=null && Main.getCurrentGame().board[j][column]!=this && Main.getCurrentGame().board[j][column].isSingle() && this.getValue() == Main.getCurrentGame().board[j][column].getValue()) 
					return false;
			}
			return true;
		}
		else return false;
	}

	boolean findPossibilities(){

		for (int i = 0; i<9; i++){ //go look at all columns in a row
			if (!isSolved() && Main.getCurrentGame().board[row][i]!=null && Main.getCurrentGame().board[row][i].isSolved() ) {
				possibilities.remove((Integer)Main.getCurrentGame().board[row][i].getValue());
				if (isSingle() && !isSolved()) possibilities.add((Integer)Main.getCurrentGame().board[row][i].getValue());
			}
		}

		for (int j = 0; j<9; j++){// all rows in a column
			if (!isSolved() && Main.getCurrentGame().board[j][column]!=null && Main.getCurrentGame().board[j][column].isSolved()) {
				possibilities.remove((Integer)Main.getCurrentGame().board[j][column].getValue());
				if (isSingle() && !isSolved()) possibilities.add((Integer)Main.getCurrentGame().board[j][column].getValue());
			}

		}

		

		if (column<3 && row<3) groupSolve(0,3,0,3);
		else if (column<3 && row>2 && row<6) groupSolve(0,3,3,6);
		else if (column<3 && row>5) groupSolve(0,3,6,9);

		else if (column>2 && column<6 && row<3) groupSolve(3,6,0,3);
		else if (column>2 && column<6 && row>2 && row<6) groupSolve(3,6,3,6);
		else if (column>2 && column<6 && row>5) groupSolve(3,6,6,9);

		else if (column>5 && row<3) groupSolve(6,9,0,3);
		else if (column>5 && row>2 && row<6) groupSolve(6,9,3,6);
		else if (column>5 && row>5) groupSolve(6,9,6,9);

		//if (possibilities.size()==2) doublesCheck();

		return isSolved();
	}
	
	void doublesCheck(){
		int i;

		for (i = 0; i<9; i++){ //go look at all columns in a row, find another pair with the same 2 possibilities
			if (Main.getCurrentGame().board[row][i]!=this && 
				Main.getCurrentGame().board[row][i].possibilities.equals(possibilities))
				removeDoubles("columns", i);
		}
		int j;
		for (j = 0; j<9; j++){//look at all rows in a column
			if (Main.getCurrentGame().board[j][column]!=this && 
				Main.getCurrentGame().board[j][column].possibilities.equals(possibilities))
				removeDoubles("rows", j);
		}
	}

	void removeDoubles(String rc, int k){ //rc is to determine if to look in rows/column, k is the index of the other cell with common poss.
		for (int m = 0; m<9; m++){
			if (rc.equals("columns") && Main.getCurrentGame().board[row][m]!=this && Main.getCurrentGame().board[row][m]!=Main.getCurrentGame().board[row][k] && !Main.getCurrentGame().board[row][m].isSolved()) {
				Main.getCurrentGame().board[row][m].possibilities.remove((Integer)possibilities.get(0));
				Main.getCurrentGame().board[row][m].possibilities.remove((Integer)possibilities.get(1));
			//	if (Main.getCurrentGame().board[row][m].possibilities.size() == 1) Main.getCurrentGame().board[row][m].isSolved() = true;
			}
			else if (rc.equals("rows") && Main.getCurrentGame().board[m][column]!=this && Main.getCurrentGame().board[m][column]!=Main.getCurrentGame().board[k][column] && !Main.getCurrentGame().board[m][column].isSolved()) {
				Main.getCurrentGame().board[m][column].possibilities.remove((Integer)possibilities.get(0));
				Main.getCurrentGame().board[m][column].possibilities.remove((Integer)possibilities.get(1));
				//if (Main.getCurrentGame().board[m][column].possibilities.size() == 1) Main.getCurrentGame().board[m][column].solved = true;
			}
		}

		}
	 


	void groupSolve(int colstart, int colend, int rowstart, int rowend){
		for (int i = rowstart; i<rowend; i++){
			for (int j = colstart; j<colend; j++){
				if (!isSolved() && Main.getCurrentGame().board[i][j]!=null && Main.getCurrentGame().board[i][j].isSolved()) {
					possibilities.remove((Integer)Main.getCurrentGame().board[i][j].getValue());
					if (isSingle() && !isSolved()) possibilities.add((Integer)Main.getCurrentGame().board[row][i].getValue());
				}
			}
		}
	}
	void printPossibilities(){
		System.out.println("Possibilities of ("+row+")("+column+"): "+possibilities);
	}
}