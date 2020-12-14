public class Main {

    public static void main(String[] args) {
        // write your program here
        var count = 0;
        for (var secret : Secret.values()) {
            if (secret.name().startsWith("STAR"))
                count++;
        }

        System.out.println(count);
    }
}

/* sample enum for inspiration
   enum Secret {
    STAR, CRASH, START, // ...
}
*/
