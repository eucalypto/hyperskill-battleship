import java.io.InputStream;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;

        var bytes = new ArrayList<Byte>();
        int currentByte;
        while ((currentByte = inputStream.read()) != -1) {
            bytes.add((byte) currentByte);
        }

        bytes.forEach(System.out::print);
    }
}