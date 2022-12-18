import java.io.IOException;
import java.nio.DoubleBuffer;

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

    Boolean userDefinedFunction;
    UserInputMathFunction userFunction;
    final double userRandomRange = 5;

    /* more complex test functions */
    final int complexFunctionIdx = 0; /* set idx of wanted function and userDefinedFunction to false */
    final double rastriginRange = 5.12; /* Rastrigin function index = 0 */

    public PSO_Helper(int dimensionsNumber, int particlesNumber, int maxIterations, Boolean userDefinedFunction) {
        this.dimensionsNumber = dimensionsNumber;
        this.particlesNumber = particlesNumber;
        this.maxIterations = maxIterations;

        this.userDefinedFunction = userDefinedFunction;
        if (userDefinedFunction)
            userFunction = new UserInputMathFunction();
    }

    public void initialize(PSO_Particle[] particles) throws IOException {
        if (userDefinedFunction)
            userFunction.getUserInput();

        for (int i = 0; i < particles.length; i++) {
            double[] positions = new double[dimensionsNumber];
            double[] velocities = new double [dimensionsNumber];

            // For each particle assign a random x value and velocity = 0
            for (int j = 0; j < dimensionsNumber; j++) {
                positions[j] = generateRandomPosition(complexFunctionIdx);
                velocities[j] = 0;
            }

            particles[i] = new PSO_Particle(positions, velocities);
            particles[i].personalBest = particles[i].position.clone();
        }
    }

    private double generateRandomPosition(int complexFunctionIdx) {
        if (!this.userDefinedFunction) {
            if (complexFunctionIdx == 0)
                return ((Math.random() * ((rastriginRange - (-rastriginRange)))) - rastriginRange);
        }
        return (( Math.random() * ( ( userRandomRange-(-userRandomRange) ) )) - userRandomRange);
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
        double bestFitness = calculateFitness_Particle(particles[0].personalBest);
        for(int i = 1; i < particlesNumber; i++) {
            if (calculateFitness_Particle(particles[i].personalBest) <= bestFitness) {
                bestFitness = calculateFitness_Particle(particles[i].personalBest);
                best = particles[i].personalBest;
            }
        }

        return best;
    }

    /* calculate fitness based on a given test function */
    public double calculateFitness_Particle(double[] positions) {
        return calculateFitness(complexFunctionIdx, positions);
    }

    private double calculateFitness(int complexFunctionIdx, double[] positions) {
        if (!this.userDefinedFunction) {
            if (complexFunctionIdx == 0) {
                double fitness = 0;
                for (int i = 0; i < dimensionsNumber; i++) {
                    fitness = fitness + (Math.pow(positions[i], 2) - (10*Math.cos(2*Math.PI*positions[i])));
                }

                fitness = fitness + (10 * dimensionsNumber);
                return fitness;
            }
        }

        return userFunction.getArgumentValue("x = " + positions[0]);
    }


}
