import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreatePlot {
    UserInputMathFunction function;
    double bounds;
    List<Double> x;
    List<Double> y;
    Plot plt;

    final int plotMinusAndPlusArgs = 2;
    int stretchGraph = 1;
    final int SA_stretch = 1;
    final int PSO_stretch = 16;
    final double offset = 0.1;

    public CreatePlot(UserInputMathFunction function, double bounds) {
        this.function = function;
        this.bounds = bounds;

        this.x = new ArrayList<>();
        this.y = new ArrayList<>();
    }

    private void generatePointsToPlot() {
        int boundsInt = (int)this.bounds;
        boundsInt *= this.stretchGraph;

        this.x = NumpyUtils.linspace(-boundsInt, boundsInt, boundsInt*plotMinusAndPlusArgs);
        for (int i = -boundsInt; i < boundsInt; i+=1)
            this.y.add(function.getArgumentValue("x = " + i));

    }

    private void plotFunction(String algoName) {
        this.plt = Plot.create();
        generatePointsToPlot();
        this.plt.title(algoName);

        this.plt.plot()
                .add(this.x, this.y)
                .linestyle("-");
    }

    public void plotPSO_Particles(String filenamePrefix, PSO_Particle[] particles, int iteration) throws PythonExecutionException, IOException {
        this.stretchGraph = PSO_stretch;
        plotFunction(filenamePrefix);

        for (PSO_Particle particle : particles) {
            List<Double> particlesX = new ArrayList<>();
            List<Double> particlesY = new ArrayList<>();

            double particleX = particle.position[0];
            particlesX.add(particleX);
            double evaluation = function.getArgumentValue("x = " + particleX);
            particlesY.add(evaluation);

            /* matplotlib4j does not seem to be able to plot single points
             * thus a small offset is added, to plot a micro line, making it
             * thick enough to simulate a point */
            particlesX.add(particleX + offset);
            particlesX.add(particleX - offset);
            particlesY.add(evaluation + offset);
            particlesY.add(evaluation - offset);

            this.plt.plot()
                    .add(particlesX, particlesY)
                    .color("red")
                    .linewidth(4);
        }

        savePlot(filenamePrefix, iteration);
    }

    public void plotSA_Point(String filenamePrefix, double[] point, int iteration) throws PythonExecutionException, IOException {
        List<Double> pointX = new ArrayList<>();
        List<Double> pointY = new ArrayList<>();

        pointX.add(point[0]);
        double evaluation = function.getArgumentValue("x = " + point[0]);
        pointY.add(evaluation);

        /* matplotlib4j does not seem to be able to plot single points
        * thus a small offset is added, to plot a micro line, making it
        * thick enough to simulate a point */
        pointX.add(point[0] + offset);
        pointX.add(point[0] - offset);
        pointY.add(evaluation + offset);
        pointY.add(evaluation - offset);

        this.stretchGraph = SA_stretch;
        plotFunction(filenamePrefix);

        this.plt.plot()
                .add(pointX, pointY)
                .color("red")
                .linewidth(3);

        savePlot(filenamePrefix, iteration);
    }

    private void savePlot(String filenamePrefix, int iteration) throws PythonExecutionException, IOException {
        plt.savefig(filenamePrefix + "_" + iteration + ".png");
        plt.executeSilently();
        plt.close();
    }
}
