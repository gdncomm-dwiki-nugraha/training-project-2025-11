-- H2 Development Test Data: 50,000 Products
-- Uses SYSTEM_RANGE to generate rows dynamically

CREATE ALIAS IF NOT EXISTS UUID FOR "java.util.UUID.randomUUID";

INSERT INTO products (id, name, description, price, category, created_at)
SELECT UUID(),
       CONCAT('Product ', x),
       CONCAT('Description ', x),
       RAND() * 500,
       CASE MOD(x, 5)
           WHEN 0 THEN 'electronics'
           WHEN 1 THEN 'fashion'
           WHEN 2 THEN 'sports'
           WHEN 3 THEN 'home'
           ELSE 'misc'
       END,
       CURRENT_TIMESTAMP
FROM SYSTEM_RANGE(1, 50000) x;
