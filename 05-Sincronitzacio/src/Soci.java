import java.util.Random;

public class Soci extends Thread {
    private static Compte compte;
    private final float aportacio = 10f;
    private final int esperaMax = 1000;
    private final int maxAnys = 10;
    private final Random rnd;

    public Soci() {
        compte = Compte.getInstance();
        rnd= new Random();
    }

    public Compte getCompte(){
        return compte;
    }

      @Override
    public void run() {
        for (int any = 0; any < maxAnys; any++) {
            for (int mes = 0; mes < 12; mes++) {
              
                synchronized (compte) {
                    if (mes%2 == 0) {
                        compte.setSaldo(aportacio);
                    } else {
                        compte.setSaldo(-aportacio);
                    }
                }
                try {
                    sleep(rnd.nextInt(esperaMax));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


