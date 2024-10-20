DROP TABLE IF EXISTS prices;

CREATE TABLE prices (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        brand_id INTEGER NOT NULL,
                        start_date TIMESTAMP NOT NULL,
                        end_date TIMESTAMP NOT NULL,
                        price_list BIGINT NOT NULL,
                        product_id BIGINT NOT NULL,
                        priority INTEGER NOT NULL,
                        price DECIMAL(19,2) NOT NULL,
                        curr VARCHAR(3) NOT NULL
);

CREATE INDEX idx_prices_product_brand_dates
    ON prices (product_id, brand_id, start_date, end_date);
