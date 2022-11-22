package com.bank;

public class CertificateOfDeposit extends Account {
    public int Maturity;

    public int getMaturity() {
        return Maturity;
    }

    public void setMaturity(int maturity) {
        Maturity = maturity;
    }

    @Override
    public String toString() {
        return " Balance " + getBalance() + " Interest " + getInterest() + " Periods " + getPeriods() +
                " Maturity " + getMaturity();
    }


}
