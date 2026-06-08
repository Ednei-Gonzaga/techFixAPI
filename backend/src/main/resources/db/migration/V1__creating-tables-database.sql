CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    login VARCHAR(200) UNIQUE NOT NULL,
    password VARCHAR(300) NOT NULL,
    role VARCHAR(50) NOT NULL,
    status BOOLEAN NOT NULL
);

CREATE TABLE clients(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cpf VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    whatsapp VARCHAR(20)
);

CREATE TABLE service_requests(
    id BIGSERIAL PRIMARY KEY,
    device VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    problem_description VARCHAR(500) NOT NULL,
    id_client BIGSERIAL NOT NULL REFERENCES clients(id)
);

CREATE TABLE service_order(
    id BIGSERIAL PRIMARY KEY,
    id_service_requests BIGSERIAL NOT NULL REFERENCES service_requests(id),
    id_user_technical BIGSERIAL REFERENCES users(id),
    identification_code VARCHAR(12) NOT NULL,
    status VARCHAR(50) NOT NULL,
    date_time_start TIMESTAMP NOT NULL,
    date_time_completed TIMESTAMP NOT NULL
);