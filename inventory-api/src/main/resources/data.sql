DELETE
FROM order_item;
DELETE
FROM orders;
DELETE
FROM inventory_item;
DELETE
FROM product;
DELETE
FROM distribution_center;

INSERT INTO distribution_center (name, location)
VALUES ('DC Bucharest', 'Bucharest'),
       ('DC Cluj', 'Cluj-Napoca');

INSERT INTO product (name, sku, price, category)
VALUES ('Milk 1L', 'MILK-001', 10, 'DAIRY'),
       ('Bread', 'BRD-001', 1, 'PRODUCE'),
       ('Eggs 10-pack', 'EGG-010', 20, 'DAIRY');

INSERT INTO inventory_item (product_id, distribution_center_id, quantity, expires_at)
VALUES (1, 1, 100, '2025-08-01'),
       (2, 1, 50, '2025-07-15'),
       (3, 1, 200, '2025-09-01'),
       (1, 2, 80, '2025-07-20'),
       (3, 2, 120, '2025-10-10');

INSERT INTO orders (requested_at, status)
VALUES (CURRENT_TIMESTAMP, 'PENDING'),
       (CURRENT_TIMESTAMP, 'FULFILLED');

INSERT INTO order_item (product_id, order_id, distribution_center_id,
                        quantity_requested, quantity_fulfilled)
VALUES (1, 1, 1, 5, 0),
       (2, 1, 1, 3, 0),
       (3, 2, 2, 10, 10);