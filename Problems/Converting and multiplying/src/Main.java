import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        var inputs = new ArrayList<String>();

        while (true) {
            var lastInput = scanner.next();
            if (lastInput.equals("0")) break;
            inputs.add(lastInput);
        }

        inputs.forEach(input -> {
            try {
                var converted = Integer.parseInt(input);
                System.out.println(converted * 10);
            } catch (NumberFormatException e) {
                System.out.println("Invalid user input: " + input);
            }
        });


    }
}