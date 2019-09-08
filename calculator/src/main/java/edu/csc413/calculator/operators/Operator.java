package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

import java.util.HashMap;

public abstract class Operator {
    /**
     * The Operator class should contain an instance of a HashMap.
     * The keys of the map will be the tokens we're interested in,
     * and values will be instances of Operator.
     * ALL subclasses of operator MUST be in their own file.
     *
     * Where does this declaration go?
     * What should its access level be?
     * Class or instance variable?
     *
     * Is this the right declaration?
    */
    private static HashMap operators = new HashMap();
    static {
        operators.put("+", new AddOperator());
        operators.put("-", new SubtractOperator());
        operators.put("*", new MultiplyOperator());
        operators.put("/", new DivideOperator());
        operators.put("^", new PowerOperator());
        operators.put("(", new OpeningParenthesisOperator());
        operators.put(")", new ClosingParenthesisOperator());
    };

    /**
     * used to get the priority of an operator
     *
     * @return as an int, priority of operator
     */
    public abstract int priority();

    /**
     * used to get the result of operand after execute with operator
     *
     * @param op1 first operand object
     * @param op2 second operand object
     * @return an operand object that has the result
     */
    public abstract Operand execute(Operand op1, Operand op2);

    /**
     * determines if a given token is a valid operator.
     * please do your best to avoid static checks
     * for example token.equals("+") and so on.
     * Think about what happens if we add more operators.
     *
     * @param token string to be checked
     * @return true or false
     */
    public static boolean check(String token) {
        return operators.containsKey(token);
    }

    /**
     * used to retrieve an operator from our HashMap.
     * This will act as out publicly facing function,
     * granting access to the Operator HashMap.
     *
     * @param token key of the operator we want to retrieve
     * @return reference to a Operator instance.
     */
    public static Operator getOperator(String token) {
        if (token == null) {
            throw new IllegalArgumentException();
        }
        return (Operator) operators.get(token);
    }
}
