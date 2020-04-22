package CSseniorProject;

import java.util.Scanner;

public class HowMany {
    public static void main(String[] args){
        Scanner n = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a number: ");
        int n1 = n.nextInt();

        Scanner N = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a number: ");
        int N1 = N.nextInt();

        System.out.println("The number " + n1 + " occurs " + howMany(N1,n1) + " times in the number. ");
    }
    public static int howMany(int N, int n){
        int count=0;
        if(N == 0 ){
            return 0;
        }
        else if(N%10==n){
            count++;
        }
        return count + howMany((N/10),n);
    }
}
