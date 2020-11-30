import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);


        var a = scanner.nextInt();
        var b = scanner.nextInt();
        var c = scanner.nextInt();


        if (a + b > c && a + c > b && b + c > a)
            System.out.println("YES");
        else
            System.out.println("NO");
    }

}
