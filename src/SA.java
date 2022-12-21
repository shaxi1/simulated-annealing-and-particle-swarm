import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.Random;

public class SA {
    int dimensionsNumber;
    int maxIterations;
    double currentTemp;
    double coolingRate;

    Boolean userDefinedFunction;
    Min_Max which;
    int complexFunctionIdx;

    SA_Helper helper;
    SA_Point bestPoint;

    public SA(int dimensionsNumber, int maxIterations, Boolean userDefinedFunction, double startingTemp, double coolingRate, int complexFunctionIdx, Min_Max which) throws IOException {
        this.dimensionsNumber = dimensionsNumber;
        this.maxIterations = maxIterations;
        this.userDefinedFunction = userDefinedFunction;
        this.complexFunctionIdx = complexFunctionIdx;
        this.currentTemp = startingTemp;
        this.coolingRate = coolingRate;
        this.which = which;

        this.helper = new SA_Helper(dimensionsNumber, maxIterations, userDefinedFunction, complexFunctionIdx);
    }

    public double[] runSA() throws PythonExecutionException, IOException {
        double[] temp = helper.generateRandomPoint();
        bestPoint = new SA_Point(temp, helper.calculateScore(complexFunctionIdx, temp));

        System.out.println("Processing...");
        for (int i = 0; i < maxIterations; i++) {
            temp = helper.generateRandomPoint();
            SA_Point testPoint = new SA_Point(temp, helper.calculateScore(complexFunctionIdx, temp));

            /* calculate the cost between two solutions */
            double cost = bestPoint.score - testPoint.score;

            Random random = new Random();
            Boolean acceptNewSolution = helper.acceptSolution(cost, random, which, this.currentTemp);

            if (acceptNewSolution) {
                bestPoint.score = testPoint.score;
                bestPoint.position = testPoint.position;

                if (userDefinedFunction)
                    helper.plotIteration(bestPoint.position, i);
            }

            currentTemp *= coolingRate;
        }

        System.out.println("SA result: " + this.bestPoint.score);
        return this.bestPoint.position;
    }

}
