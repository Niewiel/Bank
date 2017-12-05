package pl.niewiel;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Bank {
    static final int LICZBA_KONT = 100;
    static final int SALDO_POCZATKOWE = 100;
    private int[] konta;
    private ReentrantLock bankLock;
    private Condition wystarczajaceSrodki;

    Bank() {
        konta = new int[LICZBA_KONT];
        for (int i = 0; i < LICZBA_KONT; i++) {
            konta[i] = SALDO_POCZATKOWE;
        }
        bankLock = new ReentrantLock();
        wystarczajaceSrodki=bankLock.newCondition();
    }

    void przelew(int z, int na, int kwota) throws InterruptedException {
        bankLock.lock();
        try {
            while (konta[z]<kwota){
                wystarczajaceSrodki.await();
            }
            assert konta[z]>=kwota:"Saldo= "+getSaldo(z)+" kwota= "+kwota;
            System.out.println(Thread.currentThread());
            konta[z] -= kwota;
            System.out.println("\nz: " + z + "\nna: " + na + "\nkwota: " + kwota);
            konta[na] += kwota;
            System.out.println("Saldo ogolne: " + getSaldoOgolne());
            wystarczajaceSrodki.signalAll();
        } finally {
            bankLock.unlock();
        }
    }

    private int getSaldoOgolne() {

        int saldoOg = 0;
        bankLock.lock();
        try {

            for (int i = 0; i < LICZBA_KONT; i++) {
                saldoOg += konta[i];

            }
        } finally {
            bankLock.unlock();
        }
        return saldoOg;
    }

    private int getSaldo(int nr) {
        bankLock.lock();
        try {
            return konta[nr];
        } finally {
            bankLock.unlock();
        }
    }
}
