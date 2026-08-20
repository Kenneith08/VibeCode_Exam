package com.app.service;

import com.app.dto.BalanceResponse;
import com.app.dto.CashFlowResponse;
import com.app.dto.CreateExpenseRequest;
import com.app.model.Expense;
import com.app.model.ExpenseFrequency;
import com.app.repository.CashFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashFlowService {

    private final CashFlowRepository cashFlowRepository;

    @Autowired
    public CashFlowService(CashFlowRepository cashFlowRepository) {
        this.cashFlowRepository = cashFlowRepository;
    }

    public List<CashFlowResponse> getCashFlows(String type) {
        if (type != null && !type.isBlank()) {
            String upper = type.toUpperCase();
            if (!upper.equals("DONATION") && !upper.equals("EXPENSE")) {
                throw new IllegalArgumentException(
                    "type doit être 'donation' ou 'expense', reçu : " + type);
            }
            return cashFlowRepository.findAll(upper);
        }
        return cashFlowRepository.findAll(null);
    }

    public List<CashFlowResponse> getCashFlowsByUser(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId ne peut pas être vide");
        }
        return cashFlowRepository.findByUserId(userId);
    }

    public CashFlowResponse createExpense(CreateExpenseRequest request) {
        Expense expense = new Expense();
        expense.setUserId(request.getUserId());
        expense.setAmount(request.getAmount());
        expense.setReason(request.getReason());

        try {
            ExpenseFrequency freq = ExpenseFrequency.valueOf(
                request.getFrequency() != null
                    ? request.getFrequency().toUpperCase()
                    : "NONE"
            );
            expense.setFrequency(freq);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "frequency invalide : " + request.getFrequency() +
                ". Valeurs acceptées : NONE, MONTHLY, WEEKLY, YEARLY");
        }

        return cashFlowRepository.saveExpense(expense);
    }

    public BalanceResponse getBalance() {
        return cashFlowRepository.computeBalance();
    }
}
