package pl.niewiel;


import org.jetbrains.annotations.Contract;

class Bank {
    static final int LICZBA_KONT = 100;
    static final int SALDO_POCZATKOWE = 100;
    private int[] konta;

    Bank() {
        konta = new int[LICZBA_KONT];
        for (int i = 0; i < LICZBA_KONT; i++) {
            konta[i] = SALDO_POCZATKOWE;
        }
    }

    synchronized void przelew(int z, int na, int kwota) throws InterruptedException{
        while (kwota>getSaldo(z))
            wait();
        assert konta[z] >= kwota : "Saldo= " + getSaldo(z) + " kwota= " + kwota;
        System.out.println(Thread.currentThread());
        konta[z] -= kwota;
        System.out.println("\nz: " + z + "\nna: " + na + "\nkwota: " + kwota);
        konta[na] += kwota;
        System.out.println("Saldo ogolne: " + getSaldoOgolne());
        notifyAll();
    }

    @Contract(pure = true)
    private synchronized int getSaldoOgolne() {
        int saldoOg = 0;
        for (int i = 0; i < LICZBA_KONT; i++) {
            saldoOg += konta[i];
        }
        return saldoOg;
    }

    @Contract(pure = true)
    private synchronized int getSaldo(int nr) {
        return konta[nr];

    }
}
