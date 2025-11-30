-- PostgreSQL Development Test Data: 5,000 Members
-- Uses generate_series to generate rows dynamically

INSERT INTO members (id, email, password_hash, created_at)
SELECT gen_random_uuid(),
       'user' || g || '@example.com',
       '$2a$10$abcdefghijklmnopqrstuvxyzABCDE1234567890abcd123456',
       NOW()
FROM generate_series(1, 5000) g;
