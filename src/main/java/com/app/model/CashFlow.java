package com.app.model;

import java.math.BigDecimal;
import java.time.Instant;

public class CashFlow {

    private String     id;
    private String     userId;
    private Instant    createdAt;
    private BigDecimal amount;
    private String     type;

    public CashFlow() {}

    public String getId()                    { return id; }
    public void   setId(String id)           { this.id = id; }

    public String getUserId()                { return userId; }
    public void   setUserId(String userId)   { this.userId = userId; }

    public Instant getCreatedAt()            { return createdAt; }
    public void    setCreatedAt(Instant v)   { this.createdAt = v; }

    public BigDecimal getAmount()            { return amount; }
    public void       setAmount(BigDecimal v){ this.amount = v; }

    public String getType()                  { return type; }
    public void   setType(String type)       { this.type = type; }
}
