package edu.csc413.calculator.evaluator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluatorUI extends JFrame implements ActionListener {
    private TextField txField = new TextField();
    private Panel buttonPanel = new Panel();

    /**
     * total of 20 buttons on the calculator,
     * numbered from left to right, top to bottom
     * bText[] array contains the text for corresponding buttons
     */
    private static final String[] bText = {
        "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", "0", "^", "=", "/", "(", ")", "C", "CE"
    };

    /**
     * C  is for clear, clears entire expression
     * CE is for clear expression (or entry), clears last entry up until the last operator.
     */
    private Button[] buttons = new Button[bText.length];

    public static void main(String argv[]) {
        EvaluatorUI calc = new EvaluatorUI();
    }

    public EvaluatorUI() {
        setLayout(new BorderLayout());
        this.txField.setPreferredSize(new Dimension(600, 50));
        this.txField.setFont(new Font("Courier", Font.BOLD, 28));

        add(txField, BorderLayout.NORTH);
        txField.setEditable(false);

        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new GridLayout(5, 4));

        // create 20 buttons with corresponding text in bText[] array
        Button bt;
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            bt = new Button(bText[i]);
            bt.setFont(new Font("Courier", Font.BOLD, 28));
            buttons[i] = bt;
        }

        // add buttons to button panel
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            buttonPanel.add(buttons[i]);
        }

        // set up buttons to listen for mouse input
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            buttons[i].addActionListener(this);
        }

        setTitle("Calculator");
        setSize(400, 400);
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * This function is triggered anytime a button is pressed on our Calculator GUI
     *
     * @param eventObject Event object generated when a button is pressed.
     */
    public void actionPerformed(ActionEvent eventObject) {
        String inputVal = eventObject.getActionCommand();
        switch (inputVal) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                // just append to textfield
                txField.setText(txField.getText() + inputVal);
                break;
            /*case ".":
                // check if previous character for operator or .
                if (txField.getText().length() > 0) {
                    if (txField.getText().charAt(txField.getText().length() - 1) != inputVal.charAt(0)) {
                        txField.setText(txField.getText() + inputVal);
                    }
                }
                // just append to textfield with a leading 0
                else {
                    txField.setText(txField.getText() + "0" + inputVal);
                }
                break;*/
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
                // check if previous character for operator
                if (txField.getText().length() > 0) {
                    // check last character
                    switch (txField.getText().charAt(txField.getText().length() - 1)) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case ')':
                            // append to textfield
                            txField.setText(txField.getText() + inputVal);
                            break;
                        case '+':
                        case '-':
                        case '*':
                        case '/':
                            // check if previous character is the same operator
                            if (txField.getText().charAt(txField.getText().length() - 1) != inputVal.charAt(0)) {
                                // replace last character with new operator
                                txField.setText(txField.getText().substring(0,txField.getText().length() - 1) + inputVal);
                            }
                            break;
                        case '(':
                            // check if current character is a -
                            if (inputVal.charAt(0) == '-') {
                                // append to textfield
                                txField.setText(txField.getText() + inputVal);
                            }
                            break;
                    }
                }
                break;
            case "(":
                // check if textfield is not empty
                if (txField.getText().length() > 0) {
                    // check last character
                    switch (txField.getText().charAt(txField.getText().length() - 1)) {
                        case '+':
                        case '-':
                        case '*':
                        case '/':
                        case '^':
                        case '(':
                            // append to textfield
                            txField.setText(txField.getText() + inputVal);
                            break;
                        // do nothing at 0-9,)
                    }
                }
                // just append to textfield
                else {
                    txField.setText(inputVal);
                }
                break;
            case ")":
                // check if textfield has more than one character
                if (txField.getText().length() > 1) {
                    int openingP = txField.getText().split("\\(", -1).length - 1;
                    int closingP = txField.getText().split("\\)", -1).length - 1;
                    // check if textfield has enough parentheses
                    if (openingP - closingP > 0) {
                        // append to textfield
                        txField.setText(txField.getText() + inputVal);
                    }
                }
                break;
            case "C":
                // clear textfield
                txField.setText(null);
                break;
            case "CE":
                // check if textfield is not empty
                if (txField.getText().length() > 0) {
                    int i = 0;
                    boolean endLoop = false;
                    do {
                        // check last character
                        switch (txField.getText().charAt(txField.getText().length() - 1)) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                // remove last character from textfield
                                txField.setText(txField.getText().substring(0, txField.getText().length() - 1));
                                break;
                            case '+':
                            case '-':
                            case '*':
                            case '/':
                            case '^':
                            case '(':
                            case ')':
                                // check if previous character is the same operator
                                if (i == 0) {
                                    txField.setText(txField.getText().substring(0, txField.getText().length() - 1));
                                }
                                endLoop = true;
                                break;
                            // do nothing at +,-,*,/,^,(,)
                        }
                        i++;
                    } while (!endLoop && txField.getText().length() > 0);
                }
                break;
            case "=":
                // check if textfield is not empty
                if (txField.getText().length() > 0) {
                    // check for number of opening and closing parentheses
                    int openingP = txField.getText().split("\\(", -1).length - 1;
                    int closingP = txField.getText().split("\\)", -1).length - 1;
                    if (closingP - openingP == 0) {
                        // check last character
                        switch (txField.getText().charAt(txField.getText().length() - 1)) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                            case ')':
                                // submit to evaluator and get results
                                Evaluator eval = new Evaluator();
                                txField.setText(String.valueOf(eval.eval(txField.getText())));
                                break;
                            // do nothing at +,-,*,/,^,(
                        }
                    }
                }
                break;
        }
    }
}
