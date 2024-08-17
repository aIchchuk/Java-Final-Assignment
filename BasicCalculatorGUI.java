/*
Question2 a)

You are tasked with implementing a basic calculator with a graphical user interface (GUI) in Java. The calculator
should be able to evaluate valid mathematical expressions entered by the user and display the result on the GUI.
*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class BasicCalculatorGUI extends JFrame implements ActionListener {

    private JTextField textField;
    private JButton[] numberButtons;
    private JButton addButton, subButton, mulButton, divButton, decButton, openBracketButton, closeBracketButton, calculateButton, clearButton, deleteButton;

    public BasicCalculatorGUI() {
        setTitle("Basic Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(350, 50));
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        add(textField, BorderLayout.NORTH);

        // Initialize buttons
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = createButton(String.valueOf(i));
        }

        addButton = createButton("+");
        subButton = createButton("-");
        mulButton = createButton("*");
        divButton = createButton("/");
        decButton = createButton(".");
        openBracketButton = createButton("(");
        closeBracketButton = createButton(")");
        calculateButton = createButton("Calculate");
        clearButton = createButton("C");
        deleteButton = createButton("Del");

        // Layout for the calculator
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4, 10, 10));
        panel.add(clearButton);
        panel.add(deleteButton);
        panel.add(openBracketButton);
        panel.add(closeBracketButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(calculateButton);
        panel.add(divButton);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Calculate":
                calculateResult();
                break;
            case "C":
                textField.setText("");
                break;
            case "Del":
                String text = textField.getText();
                if (!text.isEmpty()) {
                    textField.setText(text.substring(0, text.length() - 1));
                }
                break;
            default:
                textField.setText(textField.getText() + command);
                break;
        }
    }

    private void calculateResult() {
        try {
            String expression = textField.getText();
            String postfix = infixToPostfix(expression);
            int result = evaluatePostfix(postfix);
            textField.setText(String.valueOf(result));
        } catch (Exception ex) {
            textField.setText("Error");
        }
    }

    private String infixToPostfix(String infix) throws Exception {
        Stack<Character> operators = new Stack<>();
        StringBuilder postfix = new StringBuilder();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isDigit(c)) {
                while (i < infix.length() && Character.isDigit(infix.charAt(i))) {
                    postfix.append(infix.charAt(i++));
                }
                postfix.append(' ');
                i--;
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postfix.append(operators.pop()).append(' ');
                }
                if (operators.isEmpty()) throw new Exception("Mismatched parentheses");
                operators.pop();
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    postfix.append(operators.pop()).append(' ');
                }
                operators.push(c);
            } else if (!Character.isWhitespace(c)) {
                throw new Exception("Invalid character: " + c);
            }
        }

        while (!operators.isEmpty()) {
            postfix.append(operators.pop()).append(' ');
        }

        return postfix.toString().trim();
    }

    private int evaluatePostfix(String postfix) throws Exception {
        Stack<Integer> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (Character.isDigit(token.charAt(0))) {
                stack.push(Integer.parseInt(token));
            } else if (token.length() == 1) {
                char op = token.charAt(0);
                int b = stack.pop();
                int a = stack.pop();
                stack.push(applyOperation(op, b, a));
            } else {
                throw new Exception("Invalid token: " + token);
            }
        }

        return stack.pop();
    }

    private int applyOperation(char op, int b, int a) throws Exception {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            default: throw new Exception("Invalid operator");
        }
    }

    private int precedence(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BasicCalculatorGUI::new);
    }
}
