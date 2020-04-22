package CSseniorProject;

public class Fibonacci {
    public static void main(String[] args){
        int[] myarray=generateFibonacci(9);

        System.out.print("[");
        for (int i=0; i<myarray.length; i++){
            if(i>0)
                System.out.print(",");
            System.out.print(" " + myarray[i]);
        }
        System.out.println("]");

    }
    public static int[] generateFibonacci(int n){
        int[] fibseries=new int[n];
        fibseries[0]=1;
        fibseries[1]=1;
        for (int i=2; i<n; i++){
            fibseries[i]=fibseries[i-1]+fibseries[i-2];
        }
        return fibseries;
    }
}
