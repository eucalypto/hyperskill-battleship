import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        var currentChar = reader.read();
        var charList = new ArrayList<Character>();
        while (currentChar != -1) {
            charList.add((char) currentChar);
            currentChar = reader.read();
        }

        Collections.reverse(charList);

        charList.forEach(System.out::print);

        reader.close();
    }
}