public class PSO_Helper {
    /* van den Bergh has shown that these values of
    * inertia weight, social and cognitive components
    * provide the best results for a variety of problems */
    final public double INERTIA = 0.729844;
    final public double COGNITIVE = 1.496180;
    final public double SOCIAL = 1.496180;

    int dimensionsNumber;
    int particlesNumber;
    int maxIterations;

    public PSO_Helper(int dimensionsNumber, int particlesNumber, int maxIterations) {
        this.dimensionsNumber = dimensionsNumber;
        this.particlesNumber = particlesNumber;
        this.maxIterations = maxIterations;
    }

    public void initalizeParticles(PSO_Particle[] particles) {
        for (int i = 0; i < particles.length; i++) {
            double[] positions = new double[dimensionsNumber];
            double[] velocities = new double [dimensionsNumber];

            // TODO: For each dimension of the particle assign a random x value [-5.12,5.12] and velocity=0
            for (int j = 0; j < dimensionsNumber; j++) {
                // TODO: setInitialPositions
                positions[j] = ((Math.random() * ((5.12-(-5.12)))) - 5.12);
                velocities[j] = 0;
            }

            particles[i] = new PSO_Particle(positions, velocities);
            particles[i].personalBest = particles[i].position.clone();
        }
    }

    /* vᵢ(t + 1) = INERTIA*vᵢ(t) + COGNITIVE*r1(y(t) − xᵢ(t)) + SOCIAL*r2(z(t) − xᵢ(t)) */
    public void updateVelocity(PSO_Particle particle, double[] best, double[] r1, double[] r2) {
        double[] velocities = particle.velocity.clone();
        double[] personalBest = particle.personalBest.clone();
        double[] positions = particle.position.clone();

        /* Calculate inertia component
         * personal best - current pos
         * COGNITIVE * r1
         * COGNITIVE * r1 * diff = cognitive term
         * neighbourhood best - current position
         * SOCIAL * r2
         * SOCIAL * r2 * diff2 = social component
         * Update particle velocity */
        double[] inertiaTerm = new double[dimensionsNumber];
        double[] diff1 = new double[dimensionsNumber];
        double[] cognitive_r1_product = new double[dimensionsNumber];
        double[] cognitiveTerm = new double[dimensionsNumber];
        double[] bestNeighbour = best.clone();
        double[] diff2 = new double[dimensionsNumber];
        double[] social_r2_product = new double[dimensionsNumber];
        double[] socialComp = new double[dimensionsNumber];
        for (int i = 0; i < dimensionsNumber; i++) {
            inertiaTerm[i] = INERTIA * velocities[i];
            diff1[i] = personalBest[i] - positions[i];
            cognitive_r1_product[i] = COGNITIVE * r1[i];
            cognitiveTerm[i] = cognitive_r1_product[i] * diff1[i];
            diff2[i] = bestNeighbour[i] - positions[i];
            social_r2_product[i] = SOCIAL * r2[i];
            socialComp[i] = social_r2_product[i] * diff2[i];
            particle.velocity[i] = inertiaTerm[i] + cognitiveTerm[i] + socialComp[i];
        }
    }

    public void updatePosition(PSO_Particle particle) {
        for (int i = 0; i < dimensionsNumber; i++)
            particle.position[i] = particle.position[i] + particle.velocity[i];
    }

    /* find the best fitting particle from the whole set of particles */
    public double[] findBest(PSO_Particle[] particles) {
        double[] best = particles[0].personalBest;
        double bestFitness = calculateFitness(particles[0].personalBest);
        for(int i = 1; i < particlesNumber; i++) {
            if (calculateFitness(particles[i].personalBest) <= bestFitness) {
                bestFitness = calculateFitness(particles[i].personalBest);
                best = particles[i].personalBest;
            }
        }

        return best;
    }

    /* calculate fitness based on a given test function */
    public double calculateFitness(double[] positions) {
        double fitness = 0;
        for (int i = 0; i < dimensionsNumber; i++) {
            // TODO: USERINPUTFUNCTION
            fitness = fitness + (Math.pow(positions[i],2)-(10*Math.cos(2*Math.PI*positions[i])));
        }

        fitness = fitness + (10 * dimensionsNumber);
        return fitness;
    }
}
