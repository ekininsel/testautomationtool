package CSseniorProject;

import java.util.Random;
import java.util.Scanner;

public class GoldGame {
    static int x ;
    static int a ;
    static int b ;
    static int c ;

    public static void main(String[] args) {
        gameStarter (x, a, b, c);
    }

    public static String gameStarter(int x, int a, int b, int c) {
        String result = "\"100 den büyük bir sayı girmelisin.\"";
        if (x >= 100)
        {
            int aa = getInt(a) ;
            int bb = getInt (b);
            int cc = getInt (c);
            if (a ==b & b ==c )
                result = "\"100 altın kazandın.\"";
            else if (a==b  || b==c || a==c)
                result = "\"15 altın kazandınız.\"";
            else if (a !=b & b!=c & a!=c)
                result = "\"Kazamadın.\"";
        }
        return result;
    }

    public static int getInt(int num){
        if(num < 100)
            num = num*2;
        else if(num == 0)
            num = num+1234;
        else if(num >100)
            num = num%3;
        return num;
    }
}
