package com.app.model;

public class Donation extends CashFlow {

    private String comment;

    public Donation() {
        setType("DONATION");
    }

    public String getComment()            { return comment; }
    public void   setComment(String c)    { this.comment = c; }
}
