package thenewboston.beginner;

import java.util.Random;

public class RandomNumGenrator_26 {

	public static void main(String[] args) {
		Random dice = new Random();
		int number;
		
		for (int counter=1; counter<=10; counter++){
			number = dice.nextInt(6)+1;
			System.out.println(number+" ");
			}
		

	}

}
