import java.util.Random;

public class Assistent implements Runnable {
    private String nom;
    private Esdeveniment esdeveniment;
    private Random random;

    public Assistent(String nom, Esdeveniment esdeveniment) {
        this.nom = nom;
        this.esdeveniment = esdeveniment;
        this.random = new Random();
    }

    public String getNom() {
        return nom;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(1001));

                if (random.nextBoolean()) {
                    esdeveniment.ferReserva(this);
                } else {
                    esdeveniment.cancelaReserva(this);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
