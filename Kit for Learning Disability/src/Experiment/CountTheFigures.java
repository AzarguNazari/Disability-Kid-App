/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Experiment;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author J
 */
public class CountTheFigures {

    /**
     * @param args the command line arguments
     * @return
     */
    public static ArrayList<Integer> generateOptions() {
        ArrayList<Integer> nums = new ArrayList(),
                options = new ArrayList();

        for (int i = 1; i < 11; i++) {
            nums.add(i);
        }

        for (int i = 0; i < 4; i++) {
            options.add(nums.remove((int) (Math.random() * nums.size() - 1)));
        }

        return options;
    }

    public static int pickCorrectAnswer(ArrayList<Integer> options) {
        return options.get((int) (Math.random() * options.size() - 1));
    }

    public static void main(String[] args) {

        ArrayList<Integer> options;

        int correctOption,
                userOption = 0,
                numQuestions = 10, numOfCorrectAnswers = 0;

        for (int i = 0; i < numQuestions; i++) {
            options = generateOptions();
            correctOption = pickCorrectAnswer(options);

            if (correctOption == userOption) {
                numOfCorrectAnswers++;
            }
            
            
            System.out.println("Options");
            options.forEach(System.out::println);
            System.out.println("");

        }

        System.out.println("Score: " + numOfCorrectAnswers + "/" + numQuestions);

    }

}
