package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private List<String> players = new ArrayList<String>();
    private int gameBoardSize;
    private List<Integer> places = new ArrayList<Integer>();
    private List<Integer> purses  = new ArrayList<Integer>();
    private List<Boolean> inPenaltyBox  = new ArrayList<Boolean>();
    
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
	    players.add(playerName);
        int numerOfPlayers = howManyPlayers();
	    places.add(0);
	    purses.add(0);
	    inPenaltyBox.add(false);
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + numerOfPlayers);
	}
	
	private int howManyPlayers() {
		return players.size();
	}

	public void handleRoll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox.get(currentPlayer)) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				advancePlayerPlace(roll);
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}
		} else {
			advancePlayerPlace(roll);
			askQuestion();
		}
		
	}
	
    private void advancePlayerPlace(int roll) {
        places.set(currentPlayer, places.get(currentPlayer) + roll);
        if (places.get(currentPlayer) > gameBoardSize - 1){
            places.set(currentPlayer, places.get(currentPlayer) - gameBoardSize);
        }
        
        System.out.println(players.get(currentPlayer) 
        		+ "'s new location is " 
        		+ places.get(currentPlayer));
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
		switch (places.get(currentPlayer)) {
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
		if (inPenaltyBox.get(currentPlayer)){
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
	    purses.set(currentPlayer, purses.get(currentPlayer) + 1);
		System.out.println(String.format(
				"%s\n%s now has %d Gold Coins.",
				victoryMessage,
				players.get(currentPlayer),
				purses.get(currentPlayer)));
	}

	public void wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
        advancePlayer();
		inPenaltyBox.set(currentPlayer, true);
		
	}


	private boolean didPlayerWin() {
		return !(purses.get(currentPlayer) == 6);
	}
}
