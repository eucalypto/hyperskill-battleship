import java.util.*;

public class Main {

    public static String getDayOfWeekName(int number) {
        var weekDays = Map.ofEntries(
                Map.entry(1, "Mon"),
                Map.entry(2, "Tue"),
                Map.entry(3, "Wed"),
                Map.entry(4, "Thu"),
                Map.entry(5, "Fri"),
                Map.entry(6, "Sat"),
                Map.entry(7, "Sun")
        );

        if (!weekDays.containsKey(number)) {
            throw new IllegalArgumentException();
        }

        return weekDays.get(number);
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dayNumber = scanner.nextInt();
        try {
            System.out.println(getDayOfWeekName(dayNumber));
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
    }
}