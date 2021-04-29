CREATE TABLE receivers
(
    id            BIGSERIAL PRIMARY KEY,
    phone_number  VARCHAR     NOT NULL UNIQUE,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE providers
(
    id             VARCHAR(11) PRIMARY KEY,
    address        VARCHAR         NOT NULL,
    total_count    BIGINT          NOT NULL DEFAULT 0,
    done_count     BIGINT          NOT NULL DEFAULT 0,
    done_percent   NUMERIC(100, 2) NOT NULL DEFAULT 0,
    failed_count   BIGINT          NOT NULL DEFAULT 0,
    failed_percent NUMERIC(100, 2) NOT NULL DEFAULT 0,
    creation_date  TIMESTAMPTZ     NOT NULL DEFAULT now()
);

CREATE TABLE messages
(
    id            BIGSERIAL PRIMARY KEY,
    receiver_id   BIGINT      NOT NULL REFERENCES receivers (id),
    text          TEXT        NOT NULL,
    status        VARCHAR(7)  NOT NULL CHECK ( status in ('QUEUED', 'SENDING', 'DONE', 'FAILED') ),
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE events
(
    id            UUID PRIMARY KEY,
    message_id    BIGINT      NOT NULL REFERENCES messages (id) ON DELETE RESTRICT,
    payload       JSON        NOT NULL,
    creation_date TIMESTAMPTZ NOT NULL
);