package Experiment;

import java.util.Random;
import java.util.ArrayList;

public class additionSubtraction {

    public static ArrayList<Integer> generateOptions() {
        ArrayList<Integer> nums = new ArrayList(),
                options = new ArrayList();
        int chosenNum;

        for (int i = 1; i < 20; i++) {
            nums.add(i);
        }

        for (int i = 0; i < 2; i++) {
            options.add(nums.remove((int) (Math.random() * nums.size() - 1)));
        }

        return options;
    }

    public static int pickCorrectAnswer(ArrayList<Integer> options) {
        return 0;
    }

    public static void main(String[] args) {
        ArrayList<Integer> options;

        int num1, num2, correctAnswer,
                userOption = 0,
                numQuestions = 10, numOfCorrectAnswers = 0;

        Random rand = new Random();
//        rand.nextInt(max - min + 1) + min;

        for (int i = 0; i < numQuestions; i++) {

            num1 = rand.nextInt(10 - 0 + 1) + 0;
            num2 = rand.nextInt(10 + 10 + 1) - 10;
            correctAnswer = num1 + num2;

            System.out.printf("%d + (%d) = %d\n", num1, num2, correctAnswer);

            if (correctAnswer == userOption) {
                numOfCorrectAnswers++;
            }

            System.out.println("Options");
//            options.forEach(System.out::println);
            System.out.println("");

        }

        System.out.println("Score: " + numOfCorrectAnswers + "/" + numQuestions);

    }
}
