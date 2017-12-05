package pl.niewiel;

public class Main {

    public static void main(String[] args) {
        Bank b=new Bank();
        for (int i=0;i<Bank.LICZBA_KONT;i++){
            Thread t=new Thread(new PrzelewRunnable(b,i));
            t.start();
        }

    }
}
