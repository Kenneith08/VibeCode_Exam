CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE expense_frequency AS ENUM ('NONE', 'MONTHLY', 'WEEKLY', 'YEARLY');

CREATE TABLE IF NOT EXISTS users (
    id         VARCHAR(36)  PRIMARY KEY DEFAULT gen_random_uuid()::TEXT,
    ref        VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    phone      VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS cash_flows (
    id           VARCHAR(36)    PRIMARY KEY DEFAULT gen_random_uuid()::TEXT,
    user_id      VARCHAR(36)    NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at   TIMESTAMP      NOT NULL DEFAULT NOW(),
    amount       NUMERIC(19,2)  NOT NULL,
    type         VARCHAR(20)    NOT NULL CHECK (type IN ('DONATION','EXPENSE')),
    comment      TEXT,
    reason       VARCHAR(255),
    frequency    expense_frequency
);

CREATE INDEX IF NOT EXISTS idx_cash_flows_type    ON cash_flows(type);
CREATE INDEX IF NOT EXISTS idx_cash_flows_user_id ON cash_flows(user_id);
