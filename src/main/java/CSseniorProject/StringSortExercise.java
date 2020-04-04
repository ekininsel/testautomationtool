package CSseniorProject;

import java.util.Scanner;

public class StringSortExercise {
    public static void main(String[] args) {

        Scanner n = new Scanner(System.in);
        System.out.println("Enter a text: ");
        String text = n.nextLine();
        System.out.println("The smallest char of the word is " + smallestChar(text) + ".");
        System.out.println("Index of the smallest char is " + indexOfsmallestChar(text) + ".");
    }

    public static char smallestChar(String str){
        char smallest = str.charAt(0);
        for(int i=0; i<str.length(); i++){
            if(str.charAt(i) < smallest)
                smallest = str.charAt(i);
        }

        return smallest;
    }

    public static int indexOfsmallestChar(String str){
        int indexofsmallest = 0;
        for(int i =0; i<str.length(); i++){
            if(str.charAt(i) < str.charAt(indexofsmallest))
                indexofsmallest = i;
        }
        return indexofsmallest;
    }
}
