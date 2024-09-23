CREATE DATABASE CellphoneSClone
USE CellphoneSClone

-- Bang Users
CREATE TABLE Users(
    id INT NOT NULL AUTO_INCREMENT,
    fullname VARCHAR(100) DEFAULT '',
    phonenumber VARCHAR(10) NOT NULL,
    user_address VARCHAR(200) DEFAULT '',
    user_password VARCHAR(100) NOT NULL DEFAULT '', --mật khẩu đã được mã hóa
    created_at DATETIME,
    updated_at DATETIME,
    is_active TINYINT(1) DEFAULT 1,
    date_of_birth DATETIME,
    facebook_account_id INT DEFAULT 1,
    google_account_id INT DEFAULT 1,
    PRIMARY KEY (id)
);
ALTER TABLE users ADD COLUMN role_id INT;
ALTER TABLE users ADD FOREIGN KEY(role_id) REFERENCES roles(id);

--Bang Roles
CREATE TABLE Roles(
    id INT NOT NULL,
    name VARCHAR(20) NOT NULL
    PRIMARY KEY(id),
);

--Bang Tokens
CREATE TABLE Tokens (
    token_id INT NOT NULL AUTO_INCREMENT,
    token_value VARCHAR(255) NOT NULL,
    token_type VARCHAR(50) NOT NULL,
    expiration_date DATETIME,
    revoked TINYINT(1) NOT NULL,
    expired TINYINT(1) NOT NULL,
    user_id INT,
    PRIMARY KEY (token_id),
    FOREIGN KEY (user_id) REFERENCES Users(id)
);
ALTER TABLE Tokens ADD CONSTRAINT UNIQUE (token_value);

--Ho tro dang nhap bang Facebook va Google
CREATE TABLE social_account(
    id INT PRIMARY KEY AUTO_INCREMENT, 
    proivder VARCHAR(20) NOT NULL,
    providerid VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL COMMENT 'Ten tai khoan',
    name VARCHAR(100) NOT NULL COMMENT 'Ten nguoi dung',
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
); 
 
-- Bang danh muc nhan hang
CREATE TABLE brands(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL DEFAULT,
);

--Bang chua san pham
CREATE TABLE products(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(350),
    price FLOAT NOT NULL CHECK(price >= 0),
    thumbnail VARCHAR(350) DEFAULT '',
    DESCRIPTION LONGTEXT DEFAULT '',
    created_at DATETIME,
    updated_at DATETIME,
    brands_id INT,
    FOREIGN KEY (brands_id) REFERENCES brands(id)
);

--Bang orders
CREATE TABLE orders(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    FOREIGN KEY(user_id) REFERENCES users(id),
    fullname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phonenumber VARCHAR(20) NOT NULL,
    address VARCHAR(200) NOT NUll,
    note VARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),
    total_money FLOAT CHECK(total_money >= 0)
);
ALTER TABLE orders ADD COLUMN shipping_method VARCHAR(100); 
ALTER TABLE orders ADD COLUMN shipping_address VARCHAR (200); 
ALTER TABLE orders ADD COLUMN shipping_date DATE;
ALTER TABLE orders ADD COLUMN tracking_number VARCHAR(100);
ALTER TABLE orders ADD COLUMN payment_method VARCHAR(100);
ALTER TABLE orders MODIFY COLUMN status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled')



CREATE TABLE order_details(
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    product_id INT,
    FOREIGN KEY (product_ id) REFERENCES products (id),
    price FLOAT CHECK (price >= 0),
    number_of_products INT CHECK (number_of_products › 0),
    total_money FLOAT CHECK (total_money ›= 0),
    color VARCHAR (20) DEFAULT ''
);