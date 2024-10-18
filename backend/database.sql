CREATE DATABASE CellphoneSClone
USE CellphoneSClone

-- Bang Users
CREATE TABLE users(
    id INT NOT NULL AUTO_INCREMENT,
    fullname VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL,
    user_address VARCHAR(200) DEFAULT '',
    user_password VARCHAR(100) NOT NULL DEFAULT '', -- mật khẩu đã được mã hóa
    created_at DATETIME,
    updated_at DATETIME,
    is_active TINYINT(1) DEFAULT 1,
    date_of_birth DATETIME,
    facebook_account_id INT DEFAULT 1,
    google_account_id INT DEFAULT 1,
    role_id INT, -- Thêm cột role_id
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES roles(id) -- Thêm ràng buộc khóa ngoại cho role_id
);

--Bang Roles
CREATE TABLE roles(
    id INT NOT NULL,
    name VARCHAR(20) NOT NULL
    PRIMARY KEY(id),
);

--Bang Tokens
CREATE TABLE tokens (
    token_id INT NOT NULL AUTO_INCREMENT,
    token_value VARCHAR(255) NOT NULL UNIQUE, -- Thêm ràng buộc UNIQUE trực tiếp vào cột token_value
    token_type VARCHAR(50) NOT NULL,
    expiration_date DATETIME,
    revoked TINYINT(1) NOT NULL,
    expired TINYINT(1) NOT NULL,
    user_id INT,
    PRIMARY KEY (token_id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

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
CREATE TABLE products (
    product_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(300),
    price FLOAT NOT NULL CHECK (price >= 0),
    thumbnail VARCHAR(350),
    description LONGTEXT DEFAULT '',
    created_at DATETIME,
    updated_at DATETIME,
    brands_id INT,
    in_stock INT,
    operating_system VARCHAR(255),
    screen_size DECIMAL(5,2),
    battery_capacity INT,
    ram INT,
    rom INT,
    front_camera VARCHAR(350),
    main_camera VARCHAR(350),
    color VARCHAR(255),
    release_date DATETIME,
    PRIMARY KEY (product_id),
    FOREIGN KEY (brands_id) REFERENCES brands(id)
);

CREATE TABLE product_images(
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products (product_id),
    CONSTRAINT fk_product_images_product_id
        FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    image_url VARCHAR(300)
);

--Bang orders
CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    fullname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phonenumber VARCHAR(20) NOT NULL,
    address VARCHAR(200) NOT NULL,
    note VARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled'), -- Modified status with ENUM
    total_money FLOAT CHECK (total_money >= 0),
    shipping_method VARCHAR(100), -- Added shipping_method
    shipping_address VARCHAR(200), -- Added shipping_address
    shipping_date DATE, -- Added shipping_date
    tracking_number VARCHAR(100), -- Added tracking_number
    payment_method VARCHAR(100) -- Added payment_method
);


-- Trong bảng orders lưu fullname, email, phonenumber khác vì người dùng có thể nhập khác so với bảng users

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

CREATE TABLE reviews (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT,
    product_id INT,
    score INT,
    comment LONGTEXT,
    date DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
