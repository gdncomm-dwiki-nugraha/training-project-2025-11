-- H2 Development Test Data: 5,000 Members
-- Uses SYSTEM_RANGE to generate rows dynamically

CREATE ALIAS IF NOT EXISTS UUID FOR "java.util.UUID.randomUUID";

INSERT INTO members (id, email, password_hash, created_at)
SELECT UUID(),
       CONCAT('user', x, '@example.com'),
       '$2a$10$abcdefghijklmnopqrstuvxyzABCDE1234567890abcd123456',
       CURRENT_TIMESTAMP
FROM SYSTEM_RANGE(1, 5000) x;
