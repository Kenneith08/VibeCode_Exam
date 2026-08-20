package com.app.dto;

import java.math.BigDecimal;

public class BalanceResponse {

    private BigDecimal totalDonations;
    private BigDecimal totalExpenses;
    private BigDecimal balance;

    public BalanceResponse() {}

    public BalanceResponse(BigDecimal totalDonations,
                           BigDecimal totalExpenses,
                           BigDecimal balance) {
        this.totalDonations = totalDonations;
        this.totalExpenses  = totalExpenses;
        this.balance        = balance;
    }



    public BigDecimal getTotalDonations()               { return totalDonations; }
    public void       setTotalDonations(BigDecimal v)   { this.totalDonations = v; }

    public BigDecimal getTotalExpenses()                { return totalExpenses; }
    public void       setTotalExpenses(BigDecimal v)    { this.totalExpenses = v; }

    public BigDecimal getBalance()                      { return balance; }
    public void       setBalance(BigDecimal v)          { this.balance = v; }
}
