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
        return " Balance " + df.format(getBalance()) + " Interest " + df.format(getInterest()) + " Periods " +
                df.format(getPeriods()) + " Maturity " + df.format(getMaturity()) + " Total Interest " + df.format(getTotalInterest());
    }


}
