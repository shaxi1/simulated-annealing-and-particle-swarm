import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final int dimensionsNumber = 1;
        final int maxIterations = 40;
        final Boolean userDefinedFunction = true;
        final Min_Max which = Min_Max.MAX;

        /* 0 - Rastrigin Function */
        final int complexFunctionIdx = 0;

        final int particlesNumber = 30;

        final double startingTemp = 30;
        final double coolingRate = 0.5;
        // TODO: attach console to docker, bounds should be fine, possibly add two argument functions

        try {
            PSO pso = new PSO(dimensionsNumber, particlesNumber, maxIterations, userDefinedFunction, complexFunctionIdx, which);
            pso.runPSO();
//            SA sa = new SA(dimensionsNumber, maxIterations, userDefinedFunction, startingTemp, coolingRate, complexFunctionIdx, which);
//            sa.runSA();
        } catch (IOException | PythonExecutionException e) {
            e.printStackTrace();
        }
    }
}
