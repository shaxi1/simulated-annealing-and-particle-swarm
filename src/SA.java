import java.io.IOException;
import java.util.Random;

public class SA {
    int dimensionsNumber;
    int maxIterations;
    double currentTemp;
    double coolingRate;

    Boolean userDefinedFunction;
    int complexFunctionIdx;

    SA_Helper helper;
    SA_Point bestPoint;

    public SA(int dimensionsNumber, int maxIterations, Boolean userDefinedFunction, double startingTemp, double coolingRate, int complexFunctionIdx) throws IOException {
        this.dimensionsNumber = dimensionsNumber;
        this.maxIterations = maxIterations;
        this.userDefinedFunction = userDefinedFunction;
        this.complexFunctionIdx = complexFunctionIdx;
        this.currentTemp = startingTemp;
        this.coolingRate = coolingRate;

        this.helper = new SA_Helper(dimensionsNumber, maxIterations, userDefinedFunction, complexFunctionIdx);
    }

    public double[] runSA() {
        double[] temp = helper.generateRandomPoint();
        bestPoint = new SA_Point(temp, helper.calculateScore(complexFunctionIdx, temp));

        for (int i = 0; i < maxIterations; i++) {
            temp = helper.generateRandomPoint();
            SA_Point testPoint = new SA_Point(temp, helper.calculateScore(complexFunctionIdx, temp));

            /* calculate the cost between two solutions */
            double cost = bestPoint.score - testPoint.score;

            Boolean acceptNewSolution = false;
            Random random = new Random();
            if (cost >= 0)
                acceptNewSolution = true;
            else if (random.nextDouble() < Math.exp(-cost / currentTemp))
                acceptNewSolution = true;

            if (acceptNewSolution) {
                bestPoint.score = testPoint.score;
                bestPoint.position = testPoint.position;
            }

            currentTemp *= coolingRate;
        }

        System.out.println("SA result: " + this.bestPoint.score);
        return this.bestPoint.position;
    }

}
