CREATE TABLE tool (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tool_code VARCHAR(50) NOT NULL UNIQUE,
    tool_type VARCHAR(50) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    daily_charge DECIMAL(10, 2) NOT NULL,
    is_weekday_charged BOOLEAN NOT NULL,
    is_weekend_charged BOOLEAN NOT NULL,
    is_holiday_charged BOOLEAN NOT NULL
);
