package edu.csc413.calculator.evaluator;

/**
 * Operand class used to represent an operand
 * in a valid mathematical expression.
 */
public class Operand {
    private int opValue;
    /**
     * construct operand from string token.
     *
     * @param token string that will convert to integer and to be saved
     */
    public Operand(String token) {
        opValue = Integer.parseInt(token);
    }

    /**
     * construct operand from integer
     *
     * @param value integer to be saved
     */
    public Operand(int value) {
        opValue = value;
    }

    /**
     * return value of operand
     *
     * @return opValue
     */
    public int getValue() {
        return opValue;
    }

    /**
     * Check to see if given token is a valid operand.
     *
     * @param token string to be checked
     * @return true or false
     */
    public static boolean check(String token) {
        if (token == null) {
            throw new IllegalArgumentException();
        }
        return token.matches("[-+]?\\d*\\.?\\d+");
    }
}
