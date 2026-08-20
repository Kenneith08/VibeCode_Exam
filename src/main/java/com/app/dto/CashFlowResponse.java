package com.app.dto;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO de réponse générique pour un CashFlow (Donation ou Expense).
 * Les champs spécifiques sont null selon le type.
 */
public class CashFlowResponse {

    private String     id;
    private String     userId;
    private Instant    createdAt;
    private BigDecimal amount;
    private String     type;       // "DONATION" | "EXPENSE"

    // -- Donation --
    private String     comment;

    // -- Expense --
    private String     reason;
    private String     frequency;  // valeur de l'enum en String

    public CashFlowResponse() {}

    // ---------- Getters / Setters ----------

    public String     getId()                      { return id; }
    public void       setId(String id)             { this.id = id; }

    public String     getUserId()                  { return userId; }
    public void       setUserId(String userId)     { this.userId = userId; }

    public Instant    getCreatedAt()               { return createdAt; }
    public void       setCreatedAt(Instant v)      { this.createdAt = v; }

    public BigDecimal getAmount()                  { return amount; }
    public void       setAmount(BigDecimal v)      { this.amount = v; }

    public String     getType()                    { return type; }
    public void       setType(String type)         { this.type = type; }

    public String     getComment()                 { return comment; }
    public void       setComment(String comment)   { this.comment = comment; }

    public String     getReason()                  { return reason; }
    public void       setReason(String reason)     { this.reason = reason; }

    public String     getFrequency()               { return frequency; }
    public void       setFrequency(String freq)    { this.frequency = freq; }
}
