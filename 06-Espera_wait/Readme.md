## 1 Per què s'atura l'execució al cap d'un temps?

L'execució s'atura perquè tots els fils poden quedar bloquejats en espera. Quan l'esdeveniment està ple, els fils que intenten fer una reserva entren en wait() i es queden esperant. Si en aquell moment ningú cancel·la una reserva, no es crida a notifyAll() i els fils no es desperten.

El codi que provoca aquest comportament és:

public synchronized void ferReserva(Assistent assistent) {
    while (llistaAssistents.size() >= placesMaximes) {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    llistaAssistents.add(assistent);
}

## 2 Què passaria si en lloc de una probabilitat de 50%-50% fora de 70%(ferReserva)-30%(cancel·lar)? I si foren al revés les probabilitats?

70% reservar - 30% cancel·lar:
El programa es bloquejaria molt ràpid, perquè es farien moltes més reserves que cancel·lacions. Les places s'omplirien de seguida i molts fils entrarien en wait() sense que quasi ningú cancel·lara.

El codi modificat seria:

if (random.nextInt(100) < 70) {
    esdeveniment.ferReserva(this);
} else {
    esdeveniment.cancelaReserva(this);
}


30% reservar - 70% cancel·lar:

 El programa funcionaria millor i durant més temps, perquè es cancel·len moltes reserves i s'alliberen places constantment. Això fa que es cride sovint a notifyAll() i els fils no queden bloquejats.

El codi modificat seria:

if (random.nextInt(100) < 30) {
    esdeveniment.ferReserva(this);
} else {
    esdeveniment.cancelaReserva(this);
}

## 3 Per què creus que fa falta la llista i no valdria només amb una variable sencera de reserves?

La llista és necessària per controlar quins assistents tenen realment una reserva. D'aquesta manera es pot comprovar si un assistent pot cancel·lar o no.

El codi que ho permet és:

private List<Assistent> llistaAssistents;

if (llistaAssistents.contains(assistent)) {
    llistaAssistents.remove(assistent);
}


Si només s'utilitzara una variable sencera (int), un assistent que no tinguera reserva podria cancel·lar igualment i el comptador quedaria incorrecte. La llista evita aquest problema i assegura que només cancel·len els assistents que han reservat.