-- PostgreSQL Development Test Data: 50,000 Products
-- Uses generate_series to generate rows dynamically

INSERT INTO products (id, name, description, price, category, created_at)
SELECT gen_random_uuid(),
       'Product ' || g,
       'Description ' || g,
       random() * 500,
       CASE
           WHEN g % 5 = 0 THEN 'electronics'
           WHEN g % 5 = 1 THEN 'fashion'
           WHEN g % 5 = 2 THEN 'sports'
           WHEN g % 5 = 3 THEN 'home'
           ELSE 'misc'
       END,
       NOW()
FROM generate_series(1, 50000) g;
