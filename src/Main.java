import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final int dimensionsNumber = 30;
        final int PSO_maxIterations = 40;
        final int SA_maxIterations = 100;
        final Boolean userDefinedFunction = false;
        final Min_Max which = Min_Max.MAX;

        /* 0 - Rastrigin Function
        *  1 - Rosenbrock Function
        *  2 - Sphere Function */
        final int complexFunctionIdx = 2;

        final int particlesNumber = 30;

        final double startingTemp = 30;
        final double coolingRate = 0.5;

        try {
            PSO pso = new PSO(dimensionsNumber, particlesNumber, PSO_maxIterations, userDefinedFunction, complexFunctionIdx, which);
            pso.runPSO();
//            SA sa = new SA(dimensionsNumber, SA_maxIterations, userDefinedFunction, startingTemp, coolingRate, complexFunctionIdx, which);
//            sa.runSA();
        } catch (IOException | PythonExecutionException e) {
            e.printStackTrace();
        }
    }
}
