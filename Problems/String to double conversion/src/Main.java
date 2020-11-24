class Converter {

    /**
     * It returns a double value or 0 if an exception occurred
     */
    public static double convertStringToDouble(String input) {
        try {
            return (input == null) ? 0 : Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}