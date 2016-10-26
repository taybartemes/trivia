package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private ArrayList<String> players = new ArrayList<String>();
    private int[] places = new int[6];
    private int[] purses  = new int[6];
    private boolean[] inPenaltyBox  = new boolean[6];
    
    private LinkedList<String> popQuestions = new LinkedList<String>();
    private LinkedList<String> scienceQuestions = new LinkedList<String>();
    private LinkedList<String> sportsQuestions = new LinkedList<String>();
    private LinkedList<String> rockQuestions = new LinkedList<String>();
    
    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
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
	    places[numerOfPlayers] = 0;
	    purses[numerOfPlayers] = 0;
	    inPenaltyBox[numerOfPlayers] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + numerOfPlayers);
	}
	
	private int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				advancePlayerPlace(roll);
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}
		} else {
			advancePlayerPlace(roll);
		}
		
	}

    private void advancePlayerPlace(int roll) {
        places[currentPlayer] = places[currentPlayer] + roll;
        if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
        
        System.out.println(players.get(currentPlayer) 
        		+ "'s new location is " 
        		+ places[currentPlayer]);
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }

	private void askQuestion() {
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
		switch (places[currentPlayer]) {
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
		if (inPenaltyBox[currentPlayer]){
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
		System.out.println(String.format(
				"%s\n%s now has %d Gold Coins.",
				victoryMessage,
				players.get(currentPlayer),
				++purses[currentPlayer]));
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		advancePlayer();
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
