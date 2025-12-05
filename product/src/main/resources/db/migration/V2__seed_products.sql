-- Insert 1 fixed product for reliable gRPC testing
INSERT INTO products (
    product_id,
    seller_id,
    name,
    description,
    category,
    price,
    stock,
    status,
    created_at
) VALUES (
    '00000000-0000-0000-0000-000000000001'::uuid,
    '11111111-1111-1111-1111-111111111111'::uuid,
    'Test Product',
    'This is a fixed dummy product for gRPC testing.',
    'electronics',
    12345.67,
    99,
    'ACTIVE',
    NOW()
)
ON CONFLICT (product_id) DO NOTHING;

-- Insert 50 random dummy products for development
INSERT INTO products (
    product_id,
    seller_id,
    name,
    description,
    category,
    price,
    stock,
    status,
    created_at
)
SELECT
    gen_random_uuid(),
    gen_random_uuid(),
    'Product ' || seq,
    'This is dummy product #' || seq,
    CASE 
        WHEN seq % 5 = 0 THEN 'electronics'
        WHEN seq % 5 = 1 THEN 'fashion'
        WHEN seq % 5 = 2 THEN 'beauty'
        WHEN seq % 5 = 3 THEN 'sports'
        ELSE 'home'
    END,
    (random() * 200000 + 1000)::numeric(12,2),
    (random() * 100)::int,
    'ACTIVE',
    NOW()
FROM generate_series(1, 50) AS t(seq)
ON CONFLICT (product_id) DO NOTHING;