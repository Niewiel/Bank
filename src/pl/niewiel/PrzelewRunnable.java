package pl.niewiel;

import java.util.Random;

public class PrzelewRunnable implements Runnable {

    private Bank b;
    private int konto;

    PrzelewRunnable(Bank b, int konto) {
        this.b = b;
        this.konto = konto;
    }

    @Override
    public void run() {
        int na, kwota;
        while (true) {
            Random r = new Random();
            na = r.nextInt(Bank.LICZBA_KONT);
            kwota = r.nextInt(Bank.SALDO_POCZATKOWE);
            try {
                b.przelew(konto, na, kwota);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
