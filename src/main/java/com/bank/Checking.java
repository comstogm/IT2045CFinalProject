package com.bank;

public class Checking extends Account {
    private int FEE = 5;

    /**
     * calculates balance total, adding interest to the total balance and subtracting the base $5 Fee.
     */
    public void compute() {
        for (int i = 0; i < getPeriods(); i++) {
            setBalance(
                    (getBalance() +
                            (getBalance() * (getInterest() / 100))
                    ) - FEE
            ); // I thought this was easier to read.
        }
    }
}
