public class Taula {
    private Filosof[] comensals;
    private Forquilla[] forquilles;

    public Taula(int numFilosofs) {
        comensals = new Filosof[numFilosofs];
        forquilles = new Forquilla[numFilosofs];

        // Crear forquilles
        for (int i = 0; i < numFilosofs; i++) {
            forquilles[i] = new Forquilla(i);
        }

        // Crear filòsofs i assignar forquilles
        for (int i = 0; i < numFilosofs; i++) {
            comensals[i] = new Filosof("fil" + i);
            // Forquilla esquerra: la del seu índex
            comensals[i].setForquillaEsquerra(forquilles[i]);
            // Forquilla dreta: la del filòsof de la dreta (índex + 1, circular)
            comensals[i].setForquillaDreta(forquilles[(i + 1) % numFilosofs]);
        }
    }

    public void showTaula() {
        for (Filosof f : comensals) {
            System.out.println("Comensal:" + f.getNom()
                    + " esq:" + f.getForquillaEsquerra().getNumero()
                    + " dret:" + f.getForquillaDreta().getNumero());
        }
    }

    public void cridarATaula() {
        for (Filosof f : comensals) {
            Thread t = new Thread(f);
            t.start();
        }
    }

    public static void main(String[] args) {
        Taula taula = new Taula(4);
        taula.showTaula();
        taula.cridarATaula();
    }
}
