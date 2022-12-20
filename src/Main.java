import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final int dimensionsNumber = 1;
        final int maxIterations = 100;
        final Boolean userDefinedFunction = false;
        final int complexFunctionIdx = 0;
        final Min_Max which = Min_Max.MIN;

        final int particlesNumber = 30;

        final double startingTemp = 30;
        final double coolingRate = 0.5;
        // TODO: check random bound settings for user inputed function

        try {
//            PSO pso = new PSO(dimensionsNumber, particlesNumber, maxIterations, userDefinedFunction, which);
//            pso.runPSO();
            SA sa = new SA(dimensionsNumber, maxIterations, userDefinedFunction, startingTemp, coolingRate, complexFunctionIdx, which);
            sa.runSA();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
