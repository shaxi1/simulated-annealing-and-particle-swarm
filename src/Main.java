import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            PSO pso = new PSO(30, 30, 10000, false);
            pso.runPSO();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
