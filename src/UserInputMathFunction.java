import org.mariuszgromada.math.mxparser.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInputMathFunction {
    private Function f;
    private String functionString;

    public UserInputMathFunction() {

    }

    public void getUserInput() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter a function: f(x) = a * x^2 + b * x + c");

        this.functionString = bufferedReader.readLine();
        this.f = new Function(this.functionString);
    }

    /* example "x = 3" */
    // TODO: check f(x) or f(x,y)
    public double getArgumentValue(String argument) {
        Argument x = new Argument(argument);
        Expression expression = new Expression("f(x)", f, x);

        return expression.calculate();
    }
}
