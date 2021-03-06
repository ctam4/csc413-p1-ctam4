package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;
import edu.csc413.calculator.operators.Operator;

public class DivideOperator extends Operator {
    /**
     * used to get the priority of an operator
     *
     * @return as an int, priority of operator
     */
    public int priority() {
        return 2;
    }

    /**
     * used to get the result of operand after execute with operator
     *
     * @return an operand object that has the result
     */
    public Operand execute(Operand op1, Operand op2) {
        return new Operand(op1.getValue() / op2.getValue());
    }
}
