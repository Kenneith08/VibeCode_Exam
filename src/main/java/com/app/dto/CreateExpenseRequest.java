package com.app.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateExpenseRequest {

    @NotBlank(message = "userId est obligatoire")
    private String userId;

    @NotNull(message = "amount est obligatoire")
    @DecimalMin(value = "0.01", message = "amount doit être positif")
    private BigDecimal amount;

    @NotBlank(message = "reason est obligatoire")
    private String reason;

    private String frequency = "NONE";


    public String     getUserId()                { return userId; }
    public void       setUserId(String v)        { this.userId = v; }

    public BigDecimal getAmount()                { return amount; }
    public void       setAmount(BigDecimal v)    { this.amount = v; }

    public String     getReason()                { return reason; }
    public void       setReason(String v)        { this.reason = v; }

    public String     getFrequency()             { return frequency; }
    public void       setFrequency(String v)     { this.frequency = v; }
}
