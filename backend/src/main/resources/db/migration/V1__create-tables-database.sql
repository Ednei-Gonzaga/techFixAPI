CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    login      VARCHAR(20)  NOT NULL UNIQUE,
    password   VARCHAR(300) NOT NULL,
    status     BOOLEAN      NOT NULL,
    last_login TIMESTAMP,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE employees
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    cpf      VARCHAR(20)  NOT NULL UNIQUE,
    phone    VARCHAR(20)  NOT NULL,
    whatsapp VARCHAR(20)  NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    id_user  BIGINT  UNIQUE    NOT NULL REFERENCES users (id)
);

CREATE TABLE clients
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    cpf      VARCHAR(20)  NOT NULL UNIQUE,
    phone    VARCHAR(20)  NOT NULL,
    whatsapp VARCHAR(20)  NOT NULL
);

CREATE TABLE service_requests
(
    id                  BIGSERIAL PRIMARY KEY,
    device              VARCHAR(100) NOT NULL,
    category            VARCHAR(50)  NOT NULL,
    problem_description TEXT         NOT NULL,
    created_at          TIMESTAMP    NOT NULL,
    id_client           BIGINT       NOT NULL REFERENCES clients (id)
);

CREATE TABLE service_orders
(
    id                      BIGSERIAL PRIMARY KEY,
    indentification_code    VARCHAR(9) UNIQUE NOT NULL,
    status                  VARCHAR(40)       NOT NULL,
    date_time_start         TIMESTAMP         NOT NULL,
    date_time_completed     TIMESTAMP,
    date_time_update_status TIMESTAMP,
    service_request         BIGINT            NOT NULL REFERENCES service_requests (id),
    id_user_technical       BIGINT REFERENCES users (id)
);

CREATE TABLE payments
(
    id               BIGSERIAL PRIMARY KEY,
    labor_amount     NUMERIC(10, 2) NOT NULL,
    parts_amount     NUMERIC(10, 2) NOT NULL,
    discount         DECIMAL                 DEFAULT 0.0,
    total_amount     NUMERIC(10, 2) NOT NULL,
    payment_method   VARCHAR(20),
    payment_status   VARCHAR(20)    NOT NULL DEFAULT 'PENDING',
    paid_at          TIMESTAMP,
    id_service_order BIGINT         NOT NULL REFERENCES service_orders (id)
);

CREATE TABLE payments_history
(
    id                 BIGSERIAL PRIMARY KEY,
    id_payments        BIGINT         NOT NULL REFERENCES payments (id),
    id_user            BIGINT         NOT NULL REFERENCES users (id),
    old_status         VARCHAR(20)    NOT NULL,
    new_status         VARCHAR(20)    NOT NULL,
    transaction_amount NUMERIC(10, 2) NOT NULL,
    notes              TEXT,
    created_at         TIMESTAMP      NOT NULL
);

CREATE TABLE parts
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(100)   NOT NULL,
    cost_price     NUMERIC(10, 2) NOT NULL,
    stock_quantity INTEGER        NOT NULL,
    status         BOOLEAN        NOT NULL
);

CREATE TABLE service_catalog
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(100)   NOT NULL,
    cost_price NUMERIC(10, 2) NOT NULL,
    status     BOOLEAN        NOT NULL
);

CREATE TABLE service_order_item
(
    id               BIGSERIAL PRIMARY KEY,
    id_service_order BIGINT         NOT NULL REFERENCES service_orders (id),
    id_part          BIGINT         NOT NULL REFERENCES parts (id),
    name_part        VARCHAR(100)   NOT NULL,
    quantity         INTEGER        NOT NULL,
    unit_price       NUMERIC(10, 2) NOT NULL,
    sub_total        NUMERIC(10, 2) NOT NULL
);

CREATE TABLE service_order_task
(
    id                 BIGSERIAL PRIMARY KEY,
    id_service_order   BIGINT         NOT NULL REFERENCES service_orders (id),
    id_service_catalog BIGINT         NOT NULL REFERENCES service_catalog (id),
    price_applied      NUMERIC(10, 2) NOT NULL
);

CREATE TABLE service_order_history
(
    id               BIGSERIAL PRIMARY KEY,
    id_service_order BIGINT      NOT NULL REFERENCES service_orders (id),
    id_user          BIGINT      NOT NULL REFERENCES users (id),
    old_status       VARCHAR(20) NOT NULL,
    new_status       VARCHAR(20) NOT NULL,
    notes            TEXT,
    created_at       TIMESTAMP   NOT NULL
);

CREATE TABLE verification_codes
(
    id         BIGSERIAL PRIMARY KEY,
    code       VARCHAR(6)  NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    status     VARCHAR(20) NOT NULL,
    expired_at TIMESTAMP   NOT NULL,
    id_user    BIGINT      NOT NULL REFERENCES users (id)
);