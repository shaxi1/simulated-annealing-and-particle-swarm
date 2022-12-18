import java.io.IOException;

public class SA_Helper {
    int dimensionsNumber;
    int maxIterations;

    Boolean userDefinedFunction;
    UserInputMathFunction userFunction;
    final double userRandomRange = 5;

    /* more complex test functions */
    int complexFunctionIdx; /* set idx of wanted function and userDefinedFunction to false */
    final double rastriginRange = 5.12; /* Rastrigin function index = 0 */

    public SA_Helper(int dimensionsNumber, int maxIterations, Boolean userDefinedFunction, int complexFunctionIdx) throws IOException {
        this.dimensionsNumber = dimensionsNumber;
        this.maxIterations = maxIterations;
        this.complexFunctionIdx = complexFunctionIdx;

        this.userDefinedFunction = userDefinedFunction;
        if (userDefinedFunction) {
            userFunction = new UserInputMathFunction();
            userFunction.getUserInput();
        }
    }

    public double[] generateRandomPoint() {
        double[] point = new double[dimensionsNumber];
        for (int i = 0; i < dimensionsNumber; i++)
            point[i] = generateRandomPosition(complexFunctionIdx);

        return point;
    }

    public double generateRandomPosition(int complexFunctionIdx) {
        if (!this.userDefinedFunction) {
            if (complexFunctionIdx == 0)
                return ((Math.random() * ((rastriginRange - (-rastriginRange)))) - rastriginRange);
        }

        return (( Math.random() * ( ( userRandomRange-(-userRandomRange) ) )) - userRandomRange);
    }

    public double calculateScore(int complexFunctionIdx, double[] positions) {
        if (!this.userDefinedFunction) {
            if (complexFunctionIdx == 0) {
                double fitness = 0;
                for (int i = 0; i < dimensionsNumber; i++) {
                    fitness = fitness + (Math.pow(positions[i], 2) - (10*Math.cos(2*Math.PI*positions[i])));
                }

                fitness = fitness + (10 * dimensionsNumber);
                return fitness;
            }
        }

        return userFunction.getArgumentValue("x = " + positions[0]);
    }
}