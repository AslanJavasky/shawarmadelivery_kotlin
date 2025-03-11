-- V1__Create_tables.sql

-- Create table users
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(255),
    telegram VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

-- Create table menu_items
CREATE TABLE IF NOT EXISTS  menu_items (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    menu_section VARCHAR(255) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

-- Create table orders
CREATE TABLE IF NOT EXISTS  orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL,
    date_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create table orders_menu_items
CREATE TABLE IF NOT EXISTS  orders_menu_items (
    order_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    CONSTRAINT fkdi6e3l6rwcflf0x2r43aiux24 FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fksnnhhjghd1at8679p3ftedvnp FOREIGN KEY (menu_item_id) REFERENCES menu_items(id)
);

-- Create table deliveries
CREATE TABLE IF NOT EXISTS  deliveries (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    date_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT deliveries_order_id_key UNIQUE (order_id),
    CONSTRAINT fk7isx0rnbgqr1dcofd5putl6jw FOREIGN KEY (order_id) REFERENCES orders(id)
);




