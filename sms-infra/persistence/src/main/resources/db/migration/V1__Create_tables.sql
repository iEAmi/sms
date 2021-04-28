CREATE TABLE receivers
(
    id           BIGSERIAL PRIMARY KEY,
    phone_number VARCHAR NOT NULL UNIQUE
);

CREATE TABLE providers
(
    id      VARCHAR(11) PRIMARY KEY,
    address VARCHAR NOT NULL
);

CREATE TABLE messages
(
    id                  BIGSERIAL PRIMARY KEY,
    receiver_id         BIGINT      NOT NULL REFERENCES receivers (id),
    text                TEXT        NOT NULL,
    provider_id         VARCHAR(11) NOT NULL REFERENCES providers (id),
    status_type         VARCHAR(7)  NOT NULL CHECK ( status_type in ('QUEUED', 'SENDING', 'DONE', 'FAILED') ),
    status_queued_retry INT         NOT NULL DEFAULT 0
);