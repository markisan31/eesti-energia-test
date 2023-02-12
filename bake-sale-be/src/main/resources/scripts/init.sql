CREATE TABLE IF NOT EXISTS item
(
    id       uuid not null
        constraint item_pkey
            primary key,
    name     varchar(100),
    price    numeric(5, 2),
    quantity integer,
    image    varchar(100)
);


INSERT INTO item (id, name, price, quantity, image) VALUES (gen_random_uuid(), 'Pants', 3.00, null, 'Pants.jpg');
INSERT INTO item (id, name, price, quantity, image) VALUES (gen_random_uuid(), 'Shirt', 2.00, null, 'Shirt.jpg');
INSERT INTO item (id, name, price, quantity, image) VALUES (gen_random_uuid(), 'Muffin', 1.00, 29, 'Muffin.jpg');
INSERT INTO item (id, name, price, quantity, image) VALUES (gen_random_uuid(), 'Jacket', 4.00, null, 'Jacket.jpg');
INSERT INTO item (id, name, price, quantity, image) VALUES (gen_random_uuid(), 'Toy', 1.00, null, 'Toy.jpg');
INSERT INTO item (id, name, price, quantity, image) VALUES (gen_random_uuid(), 'Brownie', 0.65, 43, 'Brownie.jpg');
INSERT INTO item (id, name, price, quantity, image) VALUES (gen_random_uuid(), 'Cake Pop', 1.35, 22, 'CakePop.jpg');
INSERT INTO item (id, name, price, quantity, image) VALUES (gen_random_uuid(), 'Apple tart', 1.50, 58, 'AppleTart.jpg');
INSERT INTO item (id, name, price, quantity, image) VALUES (gen_random_uuid(), 'Water', 1.50, 21, 'Water.jpg');