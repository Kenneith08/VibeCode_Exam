package com.app.model;

public class Expense extends CashFlow {

    private String          reason;
    private ExpenseFrequency frequency;

    public Expense() {
        setType("EXPENSE");
    }

    public String           getReason()              { return reason; }
    public void             setReason(String r)      { this.reason = r; }

    public ExpenseFrequency getFrequency()            { return frequency; }
    public void             setFrequency(ExpenseFrequency f) { this.frequency = f; }
}
