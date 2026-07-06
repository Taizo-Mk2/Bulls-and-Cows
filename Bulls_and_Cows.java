package Bulls_and_Cows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @version 20260129
 * 
 */


public class Bulls_and_Cows {

	public static void main(String[] args) {

		game();

	}

	/**
	 * @param secret The answer
	 * @param guessed  2D array that serves the all guesses that Player2 typed
	 * @param max    The number of rounds
	 */
	public static void game() {
		System.out.println("Welcome to Bulls and Cows!");
		System.out.println("How long would you like the secret to be?");
		Scanner secretLength = new Scanner(System.in);
		int num = secretLength.nextInt();
		int[] secret = new int[num];

		List<Integer> numbers = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			numbers.add(i + 1);
		}
		Collections.shuffle(numbers);
		for (int i = 0; i < num; i++) {
			secret[i] = numbers.get(i);
		}

		// translate the type of Secret from Integer to Array
		int Answer = 0;
		for (int i = secret.length - 1; i >= 0; i--) {
			Answer += secret[i] * Math.pow(10, i);
		}

		System.out.println("How many rounds would you like to limit the game to?");
		Scanner maxRounds = new Scanner(System.in);
		int max = maxRounds.nextInt();
		int[][] guessed = new int[max][];

		for (int i = 0; i < max; i++) {
			guessed[i] = sequence(secret, guessed, i + 1, max);
			if (bullsAndCows(guessed[i], secret) == true) {
				System.out.println();
				System.out.println("You guessed it correctly!");
				break;
			} else if (i + 1 == max) {
				System.out.println();
				System.out.println("You failed to guess the secret within " + max + " rounds!");
				System.out.println("The secret was " + Answer + "" + ".");
				break;
			}

		}

	}

	/**
	 * Checking the guess and secret in one round
	 * 
	 * @param secret       The answer.
	 * @param guessed      The stored numbers that Player2 attempted.
	 * @param currentRound The current round. <=max
	 * @param max          The maximum of rounds.
	 * @return Player2's guess of this round.
	 */
	public static int[] sequence(int[] secret, int[][] guessed, int currentRound, int max) {

		boolean checkOK = false;
		System.out.println("=====================================");
		int[] guessArray = new int[secret.length];

		while (!checkOK) {
			System.out.println("Round " + currentRound + " of  " + max + ":");
			System.out.println();
			System.out.print("Enter your guess: ");

			Scanner guess = new Scanner(System.in);
			int guessNumber = guess.nextInt();

			for (int i = secret.length - 1; i >= 0; i--) {
				guessArray[i] = (int) ((guessNumber / Math.pow(10, secret.length - i - 1)) % 10);
			}

			if (checkCharacters(guessNumber, secret) == true
					&& checkGuess(guessNumber, guessArray, guessed, currentRound) == true
					&& checkDuplicate(guessArray)) {
				checkOK = true;
			}

		}

		return guessArray;
	}

	/**
	 * Check about the Player2 is typing correctly or not.
	 * 
	 * @param currentGuess The player2's current guess.
	 * @param secret       The answer.
	 * @return If Player2's guess is not correct number of digits, return false. If
	 *         not, return true.
	 */
	public static boolean checkCharacters(int currentGuess, int[] secret) {

		if ((currentGuess / Math.pow(10, secret.length - 1)) > 10
				|| (currentGuess / Math.pow(10, secret.length - 1)) < 1) {
			System.out.println("You did not enter a " + secret.length + " character guess. Try again!");
			System.out.println();
			return false;
		}

		return true;
	}

	/**
	 * Check about the duplicated numbers in the guess.
	 * 
	 * @param currentGuess The player2's current guess.
	 * @return If the current guess contains the same numbers, it returns false. If
	 *         not, return true. Ex1: If the guess is "1234", returns true. Ex2: If
	 *         the guess is "1111", returns false.
	 */
	public static boolean checkDuplicate(int[] currentGuess) {
		for (int i = 0; i < currentGuess.length - 1; i++) {
			for (int g = i + 1; g < currentGuess.length; g++) {
				if (currentGuess[i] == currentGuess[g]) {
					System.out.println("You have entered duplicate numbers. Try again!");
					System.out.println();
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Check about the number of player2's guess is already guessed or not.
	 * 
	 * @param guess        the number of player2 have typed.
	 * @param currentGuess
	 * @param guessed      guess the history of player2's guessed until this round.
	 * @param currentRound The current round.
	 * @return If the number of the current guess was already typed, returns false.
	 *         If not, reuturns true.
	 */
	public static boolean checkGuess(int guess, int[] currentGuess, int[][] guessed, int currentRound) {

		for (int i = 0; i < currentRound - 1; i++) {
			if (Arrays.equals(currentGuess, guessed[i])) {
				System.out.println("You already guessed " + guess + " on Round " + (i + 1) + "! Try again!");
				return false;
			}
		}

		return true;
	}

	/**
	 * Check about the bulls and the cows.
	 * 
	 * @param guess  The current guess
	 * @param secret The secret.
	 * @return If the bulls is equal to the length of the secret, it returns true.
	 *         If not, returns false.
	 */
	public static boolean bullsAndCows(int[] guess, int[] secret) {

		int bulls = 0;
		int cows = 0;

		for (int i = 0; i < secret.length; i++) {
			if (guess[i] == secret[i]) {
				bulls++;
			}
		}

		for (int i = 0; i < guess.length; i++) {
			for (int g = 0; g < secret.length; g++) {
				if (guess[i] == secret[g]) {
					cows++;
					break;
				}
			}
		}

		cows -= bulls;

		System.out.println("Bulls: " + bulls + "; Cows: " + cows);
		System.out.println();

		if (bulls == secret.length) {
			return true;
		} else {
			return false;
		}

	}

}
