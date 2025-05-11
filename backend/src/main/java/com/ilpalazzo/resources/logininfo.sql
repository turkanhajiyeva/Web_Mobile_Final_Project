CREATE TABLE table_information (
    user_id CHAR(128) PRIMARY KEY, 
    username VARCHAR(50) NOT NULL,
    password VARCHAR(20) NOT NULL,
    role VARCHAR(30) NOT NULL
);

INSERT INTO logininfo (user_id, username, password, role)
VALUES 
    ('127a597a-228f-4547-8ab3-e23b951237a6','a_r_i_z_o_na', 'passwordweb', 'Kitchen Staff'),
    ('e3c611d8-5bcb-438c-9ce0-61fb2d4acd87','lilithanchaos', 'passwordmobile', 'Manager'),
    ('41d6dd1d-2e65-4503-b3c2-910e13c6e345','ilpalazz0', 'passwordfinal', 'Kitchen Staff'),
    ('59180e0d-055d-42c0-9944-63ff24e4a3e3','crimsonV0', 'passwordproject', 'Waiter'),
    ('495d1c38-86e7-4bd0-93e9-ebe735d58b22','SevereHedgehog', 'passwordcookie', 'Waiter');