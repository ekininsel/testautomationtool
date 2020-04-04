package CSseniorProject;

import java.util.Scanner;

public class IsSorted {
    public static void main(String[] args){

        Scanner n = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a text: ");
        String text = n.nextLine();

        if (isSorted(text)) {
            System.out.println("The	input	string	is	sorted.");
        } else {
            System.out.println("The	input	string	is	not	sorted.");
        }
    }

    public static boolean isSorted(String text){
        for(int i =0; i<text.length()-1; i++){
            if(text.charAt(i) > text.charAt(i+1)){

                return false;
            }
        }
        return true;
    }
}
