CREATE TABLE IF NOT EXISTS clans
(
    id        serial UNIQUE PRIMARY KEY NOT NULL,
    name      varchar(18) UNIQUE        NOT NULL,
    tag       varchar(6) UNIQUE         NOT NULL,
    leader_id uuid UNIQUE               NOT NULL,
    CONSTRAINT fk_leader_id FOREIGN KEY (leader_id) REFERENCES users (id)
);