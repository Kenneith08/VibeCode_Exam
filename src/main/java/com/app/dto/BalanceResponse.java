package com.app.dto;

import java.math.BigDecimal;

/**
 * DTO de réponse pour GET /balance.
 * Retourne le total des donations, le total des dépenses et le solde net.
 */
public class BalanceResponse {

    private BigDecimal totalDonations;
    private BigDecimal totalExpenses;
    private BigDecimal balance;         // totalDonations - totalExpenses

    public BalanceResponse() {}

    public BalanceResponse(BigDecimal totalDonations,
                           BigDecimal totalExpenses,
                           BigDecimal balance) {
        this.totalDonations = totalDonations;
        this.totalExpenses  = totalExpenses;
        this.balance        = balance;
    }

    // ---------- Getters / Setters ----------

    public BigDecimal getTotalDonations()               { return totalDonations; }
    public void       setTotalDonations(BigDecimal v)   { this.totalDonations = v; }

    public BigDecimal getTotalExpenses()                { return totalExpenses; }
    public void       setTotalExpenses(BigDecimal v)    { this.totalExpenses = v; }

    public BigDecimal getBalance()                      { return balance; }
    public void       setBalance(BigDecimal v)          { this.balance = v; }
}
