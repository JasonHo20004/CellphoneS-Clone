-- Create table for brands
CREATE TABLE cellphonesclone_brands (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Create table for products
CREATE TABLE cellphonesclone_products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(300) NOT NULL,
    price FLOAT NOT NULL,
    thumbnail VARCHAR(350),
    description LONGTEXT,
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
    release_date INT,
    FOREIGN KEY (brands_id) REFERENCES cellphonesclone_brands(id)
);

-- Create table for product images
CREATE TABLE cellphonesclone_product_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    image_url VARCHAR(300),
    FOREIGN KEY (product_id) REFERENCES cellphonesclone_products(id)
);

-- Create table for users
CREATE TABLE cellphonesclone_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(100) NOT NULL,
    phone_number VARCHAR(10),
    user_address VARCHAR(200),
    user_password VARCHAR(100),
    created_at DATETIME,
    updated_at DATETIME,
    is_active TINYINT(1),
    date_of_birth DATETIME,
    facebook_account_id INT,
    google_account_id INT,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES cellphonesclone_roles(id)
);

-- Create table for roles
CREATE TABLE cellphonesclone_roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

-- Create table for social accounts
CREATE TABLE cellphonesclone_social_account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    provider VARCHAR(20),
    providerid VARCHAR(50),
    email VARCHAR(150),
    name VARCHAR(100),
    user_id INT,
    provider_id VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES cellphonesclone_users(id)
);

-- Create table for orders
CREATE TABLE cellphonesclone_orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    fullname VARCHAR(100),
    email VARCHAR(100),
    phone_number VARCHAR(20),
    address VARCHAR(200),
    note VARCHAR(100),
    order_date DATETIME,
    status VARCHAR(255),
    total_money FLOAT,
    shipping_method VARCHAR(100),
    shipping_address VARCHAR(200),
    shipping_date DATE,
    tracking_number VARCHAR(100),
    payment_method VARCHAR(100),
    active TINYINT(1),
    FOREIGN KEY (user_id) REFERENCES cellphonesclone_users(id)
);

-- Create table for order details
CREATE TABLE cellphonesclone_order_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    price FLOAT,
    number_of_products INT,
    total_money FLOAT,
    color VARCHAR(20),
    FOREIGN KEY (order_id) REFERENCES cellphonesclone_orders(id),
    FOREIGN KEY (product_id) REFERENCES cellphonesclone_products(id)
);

-- Create table for reviews
CREATE TABLE cellphonesclone_reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    score INT,
    comment LONGTEXT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES cellphonesclone_users(id),
    FOREIGN KEY (product_id) REFERENCES cellphonesclone_products(id)
);

-- Create table for tokens
CREATE TABLE cellphonesclone_tokens (
    token_id INT AUTO_INCREMENT PRIMARY KEY,
    token_value VARCHAR(255),
    token_type VARCHAR(50),
    expiration_date DATETIME,
    revoked TINYINT(1),
    expired TINYINT(1),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES cellphonesclone_users(id)
);
