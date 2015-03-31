import java.util.ArrayList;
	
public class Main {
	static int guessCount = 0;
	static ArrayList<Game> gamelist = new ArrayList<Game>();
	public static void main(String[] args) {
		Game newGame = new Game(null, 0, 0, 0);
	}
	
	public static void incGuessCount(){
		guessCount++;
	}
	
	public static int getCount(){
		return guessCount;
	}
	public static Game getCurrentGame(){
		return gamelist.get(gamelist.size() - 1);
	}
	
	public static void addGame(Game newgame){
		gamelist.add(newgame);
	}
}
