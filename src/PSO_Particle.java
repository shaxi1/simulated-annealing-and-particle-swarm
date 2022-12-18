public class PSO_Particle {
    double[] position;
    double fitness; /* will determine how good or bad the particle is */
    double[] velocity;
    double[] personalBest;

    public PSO_Particle(double[] position, double[] velocity) {
        this.position = position;
        this.velocity = velocity;
    }
}
