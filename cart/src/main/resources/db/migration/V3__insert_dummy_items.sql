-- Insert 1000 cart items associated to the cart we just created.
-- We assume only one cart exists for the test member above.
-- The script finds the cart id for the fixed member id and inserts 1000 items.

WITH target_cart AS (
    SELECT id as cart_id
    FROM cart
    WHERE member_id = '11111111-1111-1111-1111-111111111111'::uuid
    LIMIT 1
)
INSERT INTO cart_item (id, cart_id, product_id, quantity)
SELECT
    gen_random_uuid(),
    tc.cart_id,
    gen_random_uuid(),
    (floor(random() * 5) + 1)::int
FROM generate_series(1, 1000) s
CROSS JOIN target_cart tc;
