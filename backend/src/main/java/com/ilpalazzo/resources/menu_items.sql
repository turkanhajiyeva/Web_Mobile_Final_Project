CREATE TABLE menu_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(4,2) NOT NULL,
    category VARCHAR(50)
);

INSERT INTO menu_items (name, description, price, category)
VALUES
    -- Appetizers
    ('Tarkhun French Fries', 'Crispy fries seasoned with Tarkhun herb for a unique flavor', 7.49, 'Appetizers'),
    ('Tarkhun Chicken Wings', 'Spicy buffalo sauce with a side of ranch', 9.99, 'Appetizers'),
    ('Tarkhun Mozzarella Sticks', 'Crispy cheese sticks with marinara dipping sauce', 6.99, 'Appetizers'),
    ('Crispy Calamari', 'Served with a side of tangy marinara sauce', 8.99, 'Appetizers'),
    ('Bruschetta', 'Toasted bread topped with diced tomatoes, basil, and balsamic glaze', 6.99, 'Appetizers'),
    ('Stuffed Mushrooms', 'Mushrooms stuffed with a creamy cheese and herb filling, baked to golden perfection', 7.49, 'Appetizers'),
    ('Spinach and Artichoke Dip', 'Creamy spinach and artichoke dip, served with crispy tortilla chips', 8.49, 'Appetizers'),
    ('Chicken Skewers', 'Grilled chicken skewers marinated in a smoky barbecue sauce, served with a dipping sauce', 9.49, 'Appetizers'),
    ('Fried Zucchini Sticks', 'Lightly battered and fried zucchini sticks, served with marinara sauce', 7.99, 'Appetizers'),
    ('Fish Tacos', 'Crispy battered fish served in soft corn tortillas with shredded cabbage, salsa, and crema', 8.99, 'Appetizers'),
    ('Charcuterie Board', 'An assortment of cured meats, cheeses, olives, and crackers', 12.99, 'Appetizers'),

    -- Main Courses
    ('Tarkhun Steak', 'A tender steak infused with Tarkhun herb, served with mashed potatoes and vegetables', 14.99, 'Main Courses'),
    ('Arizona Hot Pepperoni Pizza', 'A spicy pepperoni pizza with a zesty kick, topped with fresh peppers and mozzarella', 12.99, 'Main Courses'),
    ('Texas BBQ Ribs', 'Slow-cooked ribs with BBQ sauce and coleslaw', 18.99, 'Main Courses'),
    ('Pineapple Jalapeño Pizza', 'Pizza topped with pineapple, jalapeños, and mozzarella', 13.49, 'Main Courses'),
    ('Grilled Salmon Fillet', 'Served with roasted vegetables and a lemon butter sauce', 16.49, 'Main Courses'),
    ('Margarita Pizza', 'Classic pizza with fresh mozzarella, tomatoes, and basil', 11.99, 'Main Courses'),
    ('Spaghetti Carbonara', 'Classic pasta with eggs, pancetta, and parmesan', 13.49, 'Main Courses'),
    ('BBQ Chicken Pizza', 'Pizza with grilled chicken, BBQ sauce, and red onions', 12.49, 'Main Courses'),
    ('Lemon Herb Grilled Chicken', 'Grilled chicken breast marinated in a lemon herb sauce, served with roasted potatoes and steamed broccoli', 15.49, 'Main Courses'),
    ('Beef Wellington', 'Tender beef wrapped in puff pastry with mushroom duxelles, served with a side of mashed potatoes and gravy', 24.99, 'Main Courses'),
    ('Vegetarian Lasagna', 'Layers of pasta, ricotta cheese, spinach, and marinara sauce, baked to perfection', 12.99, 'Main Courses'),
    ('Seafood Paella', 'A traditional Spanish rice dish with shrimp, mussels, clams, and chorizo', 19.99, 'Main Courses'),
    ('Chicken Parmesan', 'Breaded chicken cutlet topped with marinara sauce and melted mozzarella, served with spaghetti', 14.49, 'Main Courses'),
    ('Grilled Ribeye Steak', 'A perfectly grilled ribeye steak, served with garlic butter and a side of crispy fries', 22.99, 'Main Courses'),
    ('Lobster Tail', 'Butter-poached lobster tail, served with a side of asparagus and mashed potatoes', 29.99, 'Main Courses'),

    -- Drinks
    ('Tarkhun Vodka', 'A refreshing vodka cocktail made with Tarkhun herb for a distinctive taste', 1.99, 'Drinks'),
    ('Tremor Cocktail', 'A bold cocktail with freshly squeezed lemon juice for a tangy and invigorating flavor', 2.49, 'Drinks'),
    ('Tarkhun Iced Tea', 'Refreshing sweetened tea served with lemon', 2.99, 'Drinks'),
    ('Sunset Margarita', 'Classic margarita with lime and tequila', 5.49, 'Drinks'),
    ('Tarkhun Lemonade', 'Freshly squeezed lemonade with a hint of mint', 3.49, 'Drinks'),
    ('Berry Fizz Cocktail', 'A mix of fresh berries, sparkling water, and vodka', 4.99, 'Drinks'),
    ('Tarkhun Lemon Iced Tea', 'A refreshing blend of lemon and iced tea', 3.99, 'Drinks'),
    ('Peach Bellini', 'Prosecco with peach puree for a sweet and bubbly treat', 5.99, 'Drinks');
