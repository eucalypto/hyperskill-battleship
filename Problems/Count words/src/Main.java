import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        var line = reader.readLine();
        var words = Arrays.stream(line.strip().split(" +")).filter(word -> !word.equals("")).count();

        System.out.println(words);

        reader.close();
    }
}