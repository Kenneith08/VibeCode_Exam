package com.app.repository;

import com.app.dto.BalanceResponse;
import com.app.dto.CashFlowResponse;
import com.app.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CashFlowRepository {

    private final DataSource dataSource;

    @Autowired
    public CashFlowRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<CashFlowResponse> findAll(String type) {
        String sql = "SELECT id, user_id, created_at, amount, type, " +
                     "       comment, reason, frequency " +
                     "FROM cash_flows ";

        if (type != null && !type.isBlank()) {
            sql += "WHERE type = ? ";
        }
        sql += "ORDER BY created_at DESC";

        List<CashFlowResponse> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (type != null && !type.isBlank()) {
                ps.setString(1, type.toUpperCase());
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC findAll : " + e.getMessage(), e);
        }

        return results;
    }

    public List<CashFlowResponse> findByUserId(String userId) {
        String sql = "SELECT id, user_id, created_at, amount, type, " +
                     "       comment, reason, frequency " +
                     "FROM cash_flows " +
                     "WHERE user_id = ? " +
                     "ORDER BY created_at DESC";

        List<CashFlowResponse> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC findByUserId : " + e.getMessage(), e);
        }

        return results;
    }

    public CashFlowResponse saveExpense(Expense expense) {
        String id = UUID.randomUUID().toString();
        Instant now = Instant.now();

        String sql = "INSERT INTO cash_flows " +
                     "  (id, user_id, created_at, amount, type, reason, frequency) " +
                     "VALUES (?, ?, ?, ?, 'EXPENSE', ?, ?::expense_frequency)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, expense.getUserId());
            ps.setTimestamp(3, Timestamp.from(now));
            ps.setBigDecimal(4, expense.getAmount());
            ps.setString(5, expense.getReason());
            ps.setString(6, expense.getFrequency() != null
                            ? expense.getFrequency().name()
                            : "NONE");

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC saveExpense : " + e.getMessage(), e);
        }

        CashFlowResponse dto = new CashFlowResponse();
        dto.setId(id);
        dto.setUserId(expense.getUserId());
        dto.setCreatedAt(now);
        dto.setAmount(expense.getAmount());
        dto.setType("EXPENSE");
        dto.setReason(expense.getReason());
        dto.setFrequency(expense.getFrequency() != null
                         ? expense.getFrequency().name()
                         : "NONE");
        return dto;
    }

    public BalanceResponse computeBalance() {
        String sql = "SELECT " +
                     "  COALESCE(SUM(CASE WHEN type = 'DONATION' THEN amount ELSE 0 END), 0) AS total_donations, " +
                     "  COALESCE(SUM(CASE WHEN type = 'EXPENSE'  THEN amount ELSE 0 END), 0) AS total_expenses " +
                     "FROM cash_flows";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                BigDecimal donations = rs.getBigDecimal("total_donations");
                BigDecimal expenses  = rs.getBigDecimal("total_expenses");
                BigDecimal balance   = donations.subtract(expenses);
                return new BalanceResponse(donations, expenses, balance);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC computeBalance : " + e.getMessage(), e);
        }

        return new BalanceResponse(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    private CashFlowResponse mapRow(ResultSet rs) throws SQLException {
        CashFlowResponse dto = new CashFlowResponse();

        dto.setId(rs.getString("id"));
        dto.setUserId(rs.getString("user_id"));

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) dto.setCreatedAt(ts.toInstant());

        dto.setAmount(rs.getBigDecimal("amount"));
        dto.setType(rs.getString("type"));

        String type = rs.getString("type");
        if ("DONATION".equals(type)) {
            dto.setComment(rs.getString("comment"));
        } else if ("EXPENSE".equals(type)) {
            dto.setReason(rs.getString("reason"));
            dto.setFrequency(rs.getString("frequency"));
        }

        return dto;
    }
}
