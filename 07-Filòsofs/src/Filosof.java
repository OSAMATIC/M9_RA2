import java.util.Random;

public class Filosof implements Runnable {
    private String nom;
    private Forquilla forquillaEsquerra;
    private Forquilla forquillaDreta;
    private int gana;
    private Random random;

    public Filosof(String nom) {
        this.nom = nom;
        this.gana = 0;
        this.random = new Random();
    }

    public void setForquillaEsquerra(Forquilla f) {
        this.forquillaEsquerra = f;
    }

    public void setForquillaDreta(Forquilla f) {
        this.forquillaDreta = f;
    }

    public Forquilla getForquillaEsquerra() {
        return forquillaEsquerra;
    }

    public Forquilla getForquillaDreta() {
        return forquillaDreta;
    }

    public String getNom() {
        return nom;
    }

    private void pensar() {
        System.out.println("Filòsof: " + nom + " pensant");
        try {
            // Pensa entre 1s i 2s
            Thread.sleep(1000 + random.nextInt(1001));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void menjar() {
        boolean menjant = false;
        while (!menjant) {
            // Intenta agafar la forquilla esquerra
            boolean agafadaEsquerra = false;
            synchronized (forquillaEsquerra) {
                if (!forquillaEsquerra.isEnUs()) {
                    forquillaEsquerra.setEnUs(true);
                    agafadaEsquerra = true;
                    System.out.println(
                            "Filòsof: " + nom + " agafa la forquilla esquerra " + forquillaEsquerra.getNumero());
                }
            }

            if (agafadaEsquerra) {
                // Intenta agafar la forquilla dreta
                boolean agafadaDreta = false;
                synchronized (forquillaDreta) {
                    if (!forquillaDreta.isEnUs()) {
                        forquillaDreta.setEnUs(true);
                        agafadaDreta = true;
                        System.out
                                .println("Filòsof: " + nom + " agafa la forquilla dreta " + forquillaDreta.getNumero());
                    }
                }

                if (agafadaDreta) {
                    // Té les dues forquilles: menja
                    System.out.println("Filòsof: " + nom + " menja");
                    try {
                        // Menja entre 1s i 2s
                        Thread.sleep(1000 + random.nextInt(1001));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("Filòsof: " + nom + " ha acabat de menjar");

                    // Deixa les forquilles
                    synchronized (forquillaDreta) {
                        forquillaDreta.setEnUs(false);
                    }
                    synchronized (forquillaEsquerra) {
                        forquillaEsquerra.setEnUs(false);
                    }
                    menjant = true;
                } else {
                    // No pot agafar la dreta, deixa l'esquerra i espera
                    synchronized (forquillaEsquerra) {
                        forquillaEsquerra.setEnUs(false);
                    }
                    gana++;
                    System.out.println("Filòsof: " + nom + " deixa l'esquerra(" + forquillaEsquerra.getNumero()
                            + ") i espera (dreta ocupada)");
                    System.out.println("Filòsof: " + nom + " gana=" + gana);
                    try {
                        // Espera entre 0,5s i 1s
                        Thread.sleep(500 + random.nextInt(501));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            } else {
                // No pot agafar ni l'esquerra
                gana++;
                System.out.println("Filòsof: " + nom + " no pot agafar l'esquerra(" + forquillaEsquerra.getNumero()
                        + ") i espera");
                System.out.println("Filòsof: " + nom + " gana=" + gana);
                try {
                    Thread.sleep(500 + random.nextInt(501));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            pensar();
            menjar();
        }
    }
}
