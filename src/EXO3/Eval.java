package EXO3;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Stack;

public class Eval {
    public static String evaluateMathExpression(String expression) {
        // Remove all whitespace from the expression string
        expression = expression.replaceAll("\\s+","");

        // Create a Stack to hold the operands and operators
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        // Iterate over each character in the expression
        for(int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // If the current character is a digit, parse the number and push it onto the operand stack
            if(Character.isDigit(c)) {
                StringBuilder numBuilder = new StringBuilder();
                while(i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    numBuilder.append(expression.charAt(i));
                    i++;
                }
                i--;
                operandStack.push(Double.parseDouble(numBuilder.toString()));
            }
            // If the current character is an operator, push it onto the operator stack
            else if(isOperator(c)) {
                while(!operatorStack.empty() && hasHigherPrecedence(c, operatorStack.peek())) {
                    performOperation(operandStack, operatorStack.pop());
                }
                operatorStack.push(c);
            }
            // If the current character is an opening parenthesis, push it onto the operator stack
            else if(c == '(') {
                operatorStack.push(c);
            }
            // If the current character is a closing parenthesis, perform operations until the matching opening parenthesis is found
            else if(c == ')') {
                while(!operatorStack.empty() && operatorStack.peek() != '(') {
                    performOperation(operandStack, operatorStack.pop());
                }
                operatorStack.pop();
            }
            // Invalid character
            else {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
        }

        // Perform remaining operations on the stacks until the operator stack is empty
        while(!operatorStack.empty()) {
            performOperation(operandStack, operatorStack.pop());
        }

        // The final result is the top value on the operand stack
        return String.valueOf(operandStack.pop());
    }

    // Returns true if the character is a valid operator, false otherwise
    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Returns true if operator1 has higher or equal precedence than operator2, false otherwise
    public static boolean hasHigherPrecedence(char operator1, char operator2) {
        if(operator1 == '*' || operator1 == '/') {
            return operator2 == '+' || operator2 == '-';
        }
        return true;
    }

    // Performs the given operation on the top two values of the operand stack and pushes the result onto the stack
    public static void performOperation(Stack<Double> operandStack, char operator) {
        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();
        double result = 0.0;
        switch(operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '/':
                result = operand1 / operand2;
                break;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
        operandStack.push(result);
    }

    public static void main(String[] args) {
        Eval eval = new Eval();
        System.out.println(eval.evaluateMathExpression("2*3+3"));
    }

}
