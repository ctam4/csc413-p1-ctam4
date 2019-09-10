package edu.csc413.calculator.evaluator;

import edu.csc413.calculator.operators.Operator;

import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {
    private Stack<Operand> operandStack;
    private Stack<Operator> operatorStack;
    private StringTokenizer tokenizer;
    private static final String DELIMITERS = "+-*^/ ()";

    public Evaluator() {
        operandStack = new Stack<>();
        operatorStack = new Stack<>();
    }

    private void process(Operator oldOpr, Operand op1, Operand op2) {
        if (oldOpr == null || op1 == null || op2 == null) {
            throw new IllegalArgumentException();
        }
        Operand result = oldOpr.execute(op1, op2);
        if (result == null) {
            System.out.println("*****execute results null******");
            throw new RuntimeException("*****execute results null******");
        }
        operandStack.push(result);
    }

    public int eval(String expression) {
        String token;

        // The 3rd argument is true to indicate that the delimiters should be used
        // as tokens, too. But, we'll need to remember to filter out spaces.
        this.tokenizer = new StringTokenizer(expression, DELIMITERS, true);

        while (this.tokenizer.hasMoreTokens()) {
            // filter out spaces
            if (!(token = this.tokenizer.nextToken()).equals(" ")) {
                try {
                    // check if token is an operand
                    if (Operand.check(token)) {
                        operandStack.push(new Operand(token));
                    } else {
                        // throw exception if token is not an operator
                        if (!Operator.check(token)) {
                            System.out.println("*****invalid token******");
                            throw new RuntimeException("*****invalid token******");
                        }
                        // create new operator object from token
                        Operator newOperator = (Operator) Operator.getOperator(token);
                        // check if operator is "(", operator stack is empty, or not empty but current operator priortiy is greater than the previous one
                        if (newOperator.getClass().getSimpleName() == "OpeningParenthesisOperator" ||
                        operatorStack.size() == 0 ||
                        newOperator.priority() >= operatorStack.peek().priority()) {
                            operatorStack.push(newOperator);
                        }
                        // check if operator is ")"
                        else if (newOperator.getClass().getSimpleName() == "ClosingParenthesisOperator") {
                            // process operators until the "(" is encountered
                            Operator oldOpr;
                            Operand op1, op2;
                            do {
                                oldOpr = operatorStack.pop();
                                op2 = operandStack.pop();
                                op1 = operandStack.pop();
                                process(oldOpr, op1, op2);
                            } while (operatorStack.peek().getClass().getSimpleName() != "OpeningParenthesisOperator");
                        }
                        // if none of above cases apply
                        else {
                            // process an operator
                            Operator oldOpr;
                            Operand op1, op2;
                            while (operatorStack.peek().priority() >= newOperator.priority() && operandStack.size() >= 2) {
                                // note that when we eval the expression 1 - 2 we will
                                // push the 1 then the 2 and then do the subtraction operation
                                // This means that the first number to be popped is the
                                // second operand, not the first operand - see the following code
                                oldOpr = operatorStack.pop();
                                op2 = operandStack.pop();
                                op1 = operandStack.pop();
                                process(oldOpr, op1, op2);
                            }
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("*****null argument******");
                    throw new RuntimeException("*****null argument******");
                }
            }
        }

        // Control gets here when we've picked up all of the tokens; you must add
        // code to complete the evaluation - consider how the code given here
        // will evaluate the expression 1+2*3
        // When we have no more tokens to scan, the operand stack will contain 1 2
        // and the operator stack will have + * with 2 and * on the top;
        // In order to complete the evaluation we must empty the stacks,
        // that is, we should keep evaluating the operator stack until it is empty;
        // Suggestion: create a method that processes the operator stack until empty.
        operatorStack.clear();

        //Don't forget to change the return value!
        return operandStack.peek().getValue();
    }
}
