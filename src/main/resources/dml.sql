INSERT INTO Category (name)
VALUES
    ('Building Blocks'),
    ('Tools'),
    ('Weapons'),
    ('Armor'),
    ('Food'),
    ('Potions'),
    ('Miscellaneous');

INSERT INTO Item (name, description, price, category_id)
VALUES
    ('Stone', 'A basic building block made from stone.', 1.5, 1),
    ('Oak Wood Planks', 'Wooden planks made from oak trees.', 1.0, 1),
    ('Iron Pickaxe', 'A durable tool used for mining blocks.', 15.0, 2),
    ('Diamond Sword', 'A powerful melee weapon made of diamonds.', 50.0, 3),
    ('Iron Chestplate', 'A piece of iron armor that provides protection.', 25.0, 4),
    ('Bread', 'A simple food item made from wheat.', 2.0, 5),
    ('Potion of Healing', 'Restores health when consumed.', 10.0, 6),
    ('Ender Pearl', 'Used for teleportation over short distances.', 20.0, 7);