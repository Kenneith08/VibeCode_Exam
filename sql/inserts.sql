INSERT INTO users (id, ref, first_name, last_name, email, phone) VALUES
    ('u1-0000-0000-0000-000000000001', 'USR-001', 'Alice',   'Martin',  'alice@example.com',   '+261340000001'),
    ('u2-0000-0000-0000-000000000002', 'USR-002', 'Bob',     'Dupont',  'bob@example.com',     '+261340000002'),
    ('u3-0000-0000-0000-000000000003', 'USR-003', 'Chantal', 'Rakoto',  'chantal@example.com', '+261340000003');

INSERT INTO cash_flows (id, user_id, created_at, amount, type, comment) VALUES
    ('cf-d001-0000-0000-000000000001', 'u1-0000-0000-0000-000000000001', '2026-01-10 08:00:00', 500.00,  'DONATION', 'Don pour les sinistrés'),
    ('cf-d002-0000-0000-000000000002', 'u2-0000-0000-0000-000000000002', '2026-02-14 09:30:00', 1200.00, 'DONATION', 'Sponsoring école'),
    ('cf-d003-0000-0000-000000000003', 'u3-0000-0000-0000-000000000003', '2026-03-01 11:00:00', 300.00,  'DONATION', NULL);

INSERT INTO cash_flows (id, user_id, created_at, amount, type, reason, frequency) VALUES
    ('cf-e001-0000-0000-000000000004', 'u1-0000-0000-0000-000000000001', '2026-01-15 10:00:00', 150.00,  'EXPENSE', 'Loyer bureau',        'MONTHLY'),
    ('cf-e002-0000-0000-000000000005', 'u2-0000-0000-0000-000000000002', '2026-01-20 14:00:00', 45.00,   'EXPENSE', 'Abonnement internet',  'MONTHLY'),
    ('cf-e003-0000-0000-000000000006', 'u1-0000-0000-0000-000000000001', '2026-02-01 08:30:00', 2500.00, 'EXPENSE', 'Matériel informatique','NONE'),
    ('cf-e004-0000-0000-000000000007', 'u3-0000-0000-0000-000000000003', '2026-03-05 09:00:00', 80.00,   'EXPENSE', 'Transport',            'WEEKLY');
