package com.app.controller;

import com.app.dto.BalanceResponse;
import com.app.dto.CashFlowResponse;
import com.app.dto.CreateExpenseRequest;
import com.app.service.CashFlowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CashFlowController {

    private final CashFlowService cashFlowService;

    @Autowired
    public CashFlowController(CashFlowService cashFlowService) {
        this.cashFlowService = cashFlowService;
    }

    @GetMapping("/cash-flows")
    public ResponseEntity<List<CashFlowResponse>> getCashFlows(
            @RequestParam(name = "type", required = false) String type) {
        List<CashFlowResponse> cashFlows = cashFlowService.getCashFlows(type);
        return ResponseEntity.ok(cashFlows);
    }

    @GetMapping("/users/{id}/cash-flows")
    public ResponseEntity<List<CashFlowResponse>> getCashFlowsByUser(
            @PathVariable("id") String userId) {
        List<CashFlowResponse> cashFlows = cashFlowService.getCashFlowsByUser(userId);
        return ResponseEntity.ok(cashFlows);
    }

    @PostMapping("/expenses")
    public ResponseEntity<CashFlowResponse> createExpense(
            @Valid @RequestBody CreateExpenseRequest request) {
        CashFlowResponse created = cashFlowService.createExpense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getBalance() {
        BalanceResponse balance = cashFlowService.getBalance();
        return ResponseEntity.ok(balance);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
