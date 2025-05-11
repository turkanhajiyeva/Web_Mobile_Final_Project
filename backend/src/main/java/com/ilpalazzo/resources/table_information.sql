CREATE TABLE table_information (
    table_id CHAR(36) PRIMARY KEY, 
    table_name VARCHAR(50) NOT NULL,
    qr_code_url VARCHAR(255)
);


INSERT INTO table_information (table_id, table_name, qr_code_url)
VALUES 
    ('d7b1a5d2-3c9c-4f2e-bf08-9a9d8f42b1b1', 'Table 1', ''),
    ('a3f7b8d5-c2b9-40b7-bcd5-f8f29a5f3f56', 'Table 2', ''),
    ('56f3e33e-ca89-468c-9ef9-df0a1fdea9ea','Table 3',''),
    ('67078c88-802d-45d0-a091-29060e016299','Table 4','');
