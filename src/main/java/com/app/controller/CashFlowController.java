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

/**
 * Controller REST - 4 endpoints du diagramme :
 *
 *  GET  /cash-flows?type=donation|expense
 *  GET  /users/{id}/cash-flows
 *  POST /expenses
 *  GET  /balance
 */
@RestController
public class CashFlowController {

    private final CashFlowService cashFlowService;

    @Autowired
    public CashFlowController(CashFlowService cashFlowService) {
        this.cashFlowService = cashFlowService;
    }

    // ---------------------------------------------------------------
    //  GET /cash-flows?type=donation|expense
    //  Retourne tous les cash-flows, filtrables par type
    // ---------------------------------------------------------------
    @GetMapping("/cash-flows")
    public ResponseEntity<List<CashFlowResponse>> getCashFlows(
            @RequestParam(name = "type", required = false) String type) {

        List<CashFlowResponse> cashFlows = cashFlowService.getCashFlows(type);
        return ResponseEntity.ok(cashFlows);
    }

    // ---------------------------------------------------------------
    //  GET /users/{id}/cash-flows
    //  Retourne tous les cash-flows d'un utilisateur
    // ---------------------------------------------------------------
    @GetMapping("/users/{id}/cash-flows")
    public ResponseEntity<List<CashFlowResponse>> getCashFlowsByUser(
            @PathVariable("id") String userId) {

        List<CashFlowResponse> cashFlows = cashFlowService.getCashFlowsByUser(userId);
        return ResponseEntity.ok(cashFlows);
    }

    // ---------------------------------------------------------------
    //  POST /expenses
    //  Crée une nouvelle dépense (Expense)
    // ---------------------------------------------------------------
    @PostMapping("/expenses")
    public ResponseEntity<CashFlowResponse> createExpense(
            @Valid @RequestBody CreateExpenseRequest request) {

        CashFlowResponse created = cashFlowService.createExpense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ---------------------------------------------------------------
    //  GET /balance
    //  Retourne le bilan global : donations totales, dépenses totales, solde
    // ---------------------------------------------------------------
    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getBalance() {
        BalanceResponse balance = cashFlowService.getBalance();
        return ResponseEntity.ok(balance);
    }

    // ---------------------------------------------------------------
    //  Gestion des erreurs métier (IllegalArgumentException)
    // ---------------------------------------------------------------
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
