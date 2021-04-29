CREATE TABLE receivers
(
    id           BIGSERIAL PRIMARY KEY,
    phone_number VARCHAR NOT NULL UNIQUE
);

CREATE TABLE providers
(
    id             VARCHAR(11) PRIMARY KEY,
    address        VARCHAR         NOT NULL,
    total_count    BIGINT          NOT NULL DEFAULT 0,
    done_count     BIGINT          NOT NULL DEFAULT 0,
    done_percent   NUMERIC(100, 2) NOT NULL
        GENERATED ALWAYS AS (
            CASE
                WHEN total_count = 0 THEN 0
                ELSE (done_count / total_count) * 100
                END
            ) STORED,
    failed_count   BIGINT          NOT NULL DEFAULT 0,
    failed_percent NUMERIC(100, 2) NOT NULL
        GENERATED ALWAYS AS (
            CASE
                WHEN total_count = 0 THEN 0
                ELSE (failed_count / total_count) * 100
                END
            ) STORED
);

CREATE TABLE messages
(
    id          BIGSERIAL PRIMARY KEY,
    receiver_id BIGINT     NOT NULL REFERENCES receivers (id),
    text        TEXT       NOT NULL,
    status      VARCHAR(7) NOT NULL CHECK ( status in ('QUEUED', 'SENDING', 'DONE', 'FAILED') )
);