import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final int dimensionsNumber = 1;
        final int maxIterations = 100;
        final Boolean userDefinedFunction = true;
        final int complexFunctionIdx = 0;

        final int particlesNumber = 30;

        final double startingTemp = 30;
        final double coolingRate = 0.5;

        try {
            PSO pso = new PSO(dimensionsNumber, particlesNumber, maxIterations, userDefinedFunction);
            pso.runPSO();
            SA sa = new SA(dimensionsNumber, maxIterations, userDefinedFunction, startingTemp, coolingRate, complexFunctionIdx);
            sa.runSA();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
