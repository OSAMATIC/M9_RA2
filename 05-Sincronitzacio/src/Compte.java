public class Compte {

    private static Compte instancia;

    private float saldo;

    private Compte(){
       saldo = 0.0f;
    }

    public static Compte getInstance(){
        if(instancia == null){
            instancia = new Compte();
        }
        return instancia;
    }


    public float getSaldo() {return saldo;}

    public float setSaldo(float quantitat) {
        return this.saldo = saldo ;
    }


   
}
