import java.util.ArrayList;
import java.util.List;

public class Esdeveniment {
    private List<Assistent> llistaAssistents;
    private final int placesMaximes;

    public Esdeveniment(int placesMaximes) {
        this.placesMaximes = placesMaximes;
        this.llistaAssistents = new ArrayList<>();
    }

    public synchronized void ferReserva(Assistent assistent) {
        while (llistaAssistents.size() >= placesMaximes) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        llistaAssistents.add(assistent);
        System.out.println(assistent.getNom() + " ha fet una reserva. Places disponibles: "
                + (placesMaximes - llistaAssistents.size()));
    }

    public synchronized void cancelaReserva(Assistent assistent) {
        if (llistaAssistents.contains(assistent)) {
            llistaAssistents.remove(assistent);
            System.out.println(assistent.getNom() + " ha cancel·lat una reserva. Places disponibles: "
                    + (placesMaximes - llistaAssistents.size()));
            notifyAll();
        } else {
            System.out
                    .println(assistent.getNom() + " no ha pogut cancel·lar una reserva inexistent. Places disponibles: "
                            + (placesMaximes - llistaAssistents.size()));
        }
    }
}
