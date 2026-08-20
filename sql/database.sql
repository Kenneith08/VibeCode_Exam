-- ============================================================
--  DATABASE SCHEMA - CashFlow Application
--  SGBD : PostgreSQL
--  Pas de JPA : tables conçues pour être utilisées en JDBC pur
-- ============================================================

-- Extension pour UUID (optionnel, on utilise VARCHAR(36) sinon)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ============================================================
--  ENUM : ExpenseFrequency
--  En PostgreSQL on crée un vrai type ENUM
-- ============================================================
CREATE TYPE expense_frequency AS ENUM ('NONE', 'MONTHLY', 'WEEKLY', 'YEARLY');

-- ============================================================
--  TABLE : users
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id         VARCHAR(36)  PRIMARY KEY DEFAULT gen_random_uuid()::TEXT,
    ref        VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    phone      VARCHAR(30)
);

-- ============================================================
--  TABLE : cash_flows  (classe parente abstraite)
--  On utilise le pattern "table per hierarchy" (discriminateur)
-- ============================================================
CREATE TABLE IF NOT EXISTS cash_flows (
    id           VARCHAR(36)    PRIMARY KEY DEFAULT gen_random_uuid()::TEXT,
    user_id      VARCHAR(36)    NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at   TIMESTAMP      NOT NULL DEFAULT NOW(),
    amount       NUMERIC(19,2)  NOT NULL,
    -- discriminateur : 'DONATION' | 'EXPENSE'
    type         VARCHAR(20)    NOT NULL CHECK (type IN ('DONATION','EXPENSE')),
    -- champs Donation
    comment      TEXT,
    -- champs Expense
    reason       VARCHAR(255),
    frequency    expense_frequency
);

-- Index pour accélérer les filtres par type et par user
CREATE INDEX IF NOT EXISTS idx_cash_flows_type    ON cash_flows(type);
CREATE INDEX IF NOT EXISTS idx_cash_flows_user_id ON cash_flows(user_id);
