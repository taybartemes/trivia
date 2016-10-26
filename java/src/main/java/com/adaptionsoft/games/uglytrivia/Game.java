package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private int gameBoardSize;

    private List<Player> players = new ArrayList<Player>();
    
    private LinkedList<String> popQuestions = new LinkedList<String>();
    private LinkedList<String> scienceQuestions = new LinkedList<String>();
    private LinkedList<String> sportsQuestions = new LinkedList<String>();
    private LinkedList<String> rockQuestions = new LinkedList<String>();
    
    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;
    
    public  Game(int boardSize){
        gameBoardSize = boardSize;
        for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	private String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	public void add(String playerName) {
	    Player player = new Player(playerName);
	    players.add(player);
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
	}
	
	public void handleRoll(int roll) {
	    Player player = getCurrentPlayer();
		System.out.println(player.getName() + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (player.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(getCurrentPlayer() + " is getting out of the penalty box");
				player.advancePlayerPlace(roll, gameBoardSize);
				askQuestion();
			} else {
				System.out.println(getCurrentPlayer() + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}
		} else {
			player.advancePlayerPlace(roll, gameBoardSize);
			askQuestion();
		}
		
	}

    private Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }
	
	private void askQuestion() {
        System.out.println("The category is " + currentCategory());
		if (currentCategory().equals("Pop"))
			System.out.println(popQuestions.removeFirst());
		if (currentCategory().equals("Science"))
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory().equals("Sports"))
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory().equals("Rock"))
			System.out.println(rockQuestions.removeFirst());		
	}
	
	
	private String currentCategory() {
		switch (getCurrentPlayer().getPlace()) {
			case 0:
			case 4:
			case 8:
				return "Pop";
			case 1:
			case 5:
			case 9:
				return "Science";
			case 2:
			case 6:
			case 10:
				return "Sports";
			default:
				return "Rock";
		}
	}

	public boolean wasCorrectlyAnswered() {
	    boolean winner = true;
		if (getCurrentPlayer().isInPenaltyBox()){
			if (isGettingOutOfPenaltyBox) {
				getCorrectAnswer("Answer was correct!!!!");
				winner = didPlayerWin();
			}
		} else {
			getCorrectAnswer("Answer was corrent!!!!");
			winner = didPlayerWin();
		}
		
        advancePlayer();
        return winner;
	}

    private void advancePlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }
    }

	private void getCorrectAnswer(String victoryMessage) {
	    getCurrentPlayer().setPurse(getCurrentPlayer().getPurse() + 1);
		System.out.println(String.format(
				"%s\n%s now has %d Gold Coins.",
				victoryMessage,
				getCurrentPlayer().getName(),
				getCurrentPlayer().getPurse()));
	}

	public void wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(getCurrentPlayer()+ " was sent to the penalty box");
        advancePlayer();
        getCurrentPlayer().setInPenaltyBox(true);
	}

	private boolean didPlayerWin() {
	    return !(getCurrentPlayer().getPurse() == 6);
	}
}
