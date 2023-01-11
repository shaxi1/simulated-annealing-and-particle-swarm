import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.Random;

public class SA_Helper {
    int dimensionsNumber;
    int maxIterations;

    Boolean userDefinedFunction;
    UserInputMathFunction userFunction;
    final double userRandomRange = 50;

    /* more complex test functions */
    int complexFunctionIdx; /* set idx of wanted function and userDefinedFunction to false */
    final double rastriginRange = 5.12; /* Rastrigin function index = 0 */
    final double rosenbrockRange = 10000;
    final double sphereRange = 10000;

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
            else if (complexFunctionIdx == 1)
                return ((Math.random() * ((rosenbrockRange - (-rosenbrockRange)))) - rosenbrockRange);
            else if (complexFunctionIdx == 2)
                return ((Math.random() * ((sphereRange - (-sphereRange)))) - sphereRange);
        }

        return (( Math.random() * ( ( userRandomRange-(-userRandomRange) ) )) - userRandomRange);
    }

    public double calculateScore(int complexFunctionIdx, double[] positions) {
        if (!this.userDefinedFunction) {
            if (complexFunctionIdx == 0) {
                double fitness = 0;
                final int Aconst = 10;
                for (int i = 0; i < dimensionsNumber; i++) {
                    fitness = fitness + (Math.pow(positions[i], 2) - (Aconst*Math.cos(2*Math.PI*positions[i])));
                }

                fitness = fitness + (Aconst * dimensionsNumber);
                return fitness;
            }
            else if (complexFunctionIdx == 1) {
                double fitness = 0;
                for (int i = 0; i < dimensionsNumber - 1; i++) {
                    fitness = fitness + (100 * (positions[i+1] - Math.pow(positions[i], 2)) + Math.pow((1 - positions[i]), 2) );
                }

                return fitness;
            }
            else if (complexFunctionIdx == 2) {
                double fitness = 0;
                for (int i = 0; i < dimensionsNumber; i++) {
                    fitness = fitness + (Math.pow(positions[i], 2));
                }

                return fitness;
            }
        }

        return userFunction.getArgumentValue("x = " + positions[0]);
    }

    public Boolean acceptSolution(double cost, Random random, Min_Max which, double currentTemp) {
        if (cost >= 0 && which == Min_Max.MIN)
            return true;
        else if (random.nextDouble() < Math.exp(-cost / currentTemp))
            return true;

        if (cost <= 0 && which == Min_Max.MAX)
            return true;
        else if (random.nextDouble() < Math.exp(-cost / currentTemp))
            return true;

        return false;
    }

    public void plotDrawIteration(double[] point, int iteration) throws PythonExecutionException, IOException {
        CreatePlot createPlot = new CreatePlot(userFunction, userRandomRange);
        createPlot.plotSA_Point("SA", point, iteration);
    }
}
