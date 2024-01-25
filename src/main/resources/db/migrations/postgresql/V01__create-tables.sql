CREATE TABLE IF NOT EXISTS pay_payment (
    id               BIGSERIAL PRIMARY KEY,
    deleted          BOOLEAN NOT NULL,
    creation_date    TIMESTAMP(6) NOT NULL,
    last_update_date TIMESTAMP(6) NOT NULL,
    pay_order        BIGINT NOT NULL UNIQUE,
    qr_data          VARCHAR(200),
    method           VARCHAR(255) NOT NULL CHECK ((method)::TEXT = 'MERCADO_PAGO_QR_CODE'::TEXT),
    status           VARCHAR(255) NOT NULL CHECK ((status)::TEXT = ANY (ARRAY['PENDING'::CHARACTER VARYING, 'PAID'::CHARACTER VARYING]::TEXT[]))
);
