package CSseniorProject;

import java.util.Scanner;

public class GetDigit {
    public static void main(String[] args){
        Scanner n = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int n1 = n.nextInt();

        Scanner N = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int N1 = N.nextInt();

        int digit = getDigit(n1,N1);
        System.out.println("the digit is "+ digit);
    }


    public static int getDigit(int n1, int N1) {
        while(n1>1){
            n1/=10;
            N1--;
        }
        return n1%10;
    }
}
