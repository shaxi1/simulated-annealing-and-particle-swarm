import java.io.IOException;

public class PSO {
    /* random vectors */
    double[] r1;
    double[] r2;

    double[] best; /* best fitting particle */
    double bestValue;
    PSO_Particle[] particles;
    PSO_Helper helper;

    int dimensionsNumber;
    int particlesNumber;
    int maxIterations;
    Boolean userDefinedFunction;

    public PSO(int dimensionsNumber, int particlesNumber, int maxIterations, Boolean userDefinedFunction) throws IOException {
        this.dimensionsNumber = dimensionsNumber;
        this.particlesNumber = particlesNumber;
        this.maxIterations = maxIterations;
        this.userDefinedFunction = userDefinedFunction;

        this.particles = new PSO_Particle[particlesNumber];
        this.helper = new PSO_Helper(dimensionsNumber, particlesNumber, maxIterations, userDefinedFunction);

        helper.initialize(particles);

        this.r1 = new double[dimensionsNumber];
        this.r2 = new double[dimensionsNumber];
    }

    public double[] runPSO() {
        for (int i = 0; i < maxIterations; i++) {
            /* calculate fitness for all the particles */
            for (int j = 0; j < particlesNumber; j++) {
                particles[j].fitness = helper.calculateFitness_Particle(particles[j].position);

                if (particles[j].fitness <= helper.calculateFitness_Particle(particles[j].personalBest))
                    particles[j].personalBest = particles[j].position.clone();
            }

            best = helper.findBest(particles);

            /* get new random vectors */
            generateRandomVectors();

            for (int j = 0; j < particlesNumber; j++) {
                helper.updateVelocity(particles[j], best, r1, r2);
                helper.updatePosition(particles[j]);
            }
        }

        this.bestValue = helper.calculateFitness_Particle(best);

        System.out.println("PSO result: " + bestValue);
        return best;
    }

    private void generateRandomVectors() {
        for (int j = 0; j < dimensionsNumber; j++) {
            this.r1[j] = Math.random();
            this.r2[j] = Math.random();
        }
    }
}
