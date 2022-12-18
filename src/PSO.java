public class PSO {
    /* random vectors */
    double[] r1;
    double[] r2;

    double[] best; /* best fitting particle */
    double bestValue;
    PSO_Particle[] particles;
    PSO_Helper helper;

    final int dimensionsNumber = 30;
    final int particlesNumber = 30;
    final int maxIterations = 10000;

    public PSO() {
        this.particles = new PSO_Particle[particlesNumber];
        this.helper = new PSO_Helper(dimensionsNumber, particlesNumber, maxIterations);

        helper.initalizeParticles(particles);

        this.r1 = new double[dimensionsNumber];
        this.r2 = new double[dimensionsNumber];
    }

    public double[] runPSO() {
        for (int i = 0; i < maxIterations; i++) {
            /* calculate fitness for all the particles */
            for (int j = 0; j < particlesNumber; j++) {
                particles[j].fitness = helper.calculateFitness(particles[j].position);

                if (particles[j].fitness <= helper.calculateFitness(particles[j].personalBest))
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

        this.bestValue = helper.calculateFitness(best);

        //System.out.println(bestValue);
        return best;
    }

    private void generateRandomVectors() {
        for (int j = 0; j < dimensionsNumber; j++) {
            this.r1[j] = Math.random();
            this.r2[j] = Math.random();
        }
    }
}
