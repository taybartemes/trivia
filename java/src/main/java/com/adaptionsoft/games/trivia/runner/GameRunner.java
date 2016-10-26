
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Game aGame = new Game(12);
		
		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");

		Random rand;

		if (args.length > 1) {
			rand = new Random(Integer.valueOf(args[1]));
		} else {
			rand = new Random();
		}

		do {
			
			aGame.handleRoll(rand.nextInt(5) + 1);
			
			notAWinner = rand.nextInt(9) == 7;
            if (notAWinner) {
                aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }
			
			
			
		} while (notAWinner);
		
	}
}
