CREATE TABLE users
(
    id            uuid UNIQUE PRIMARY KEY NOT NULL,
    hexes         integer                 NOT NULL default 0,
    last_username varchar(16)             NOT NULL default '',
    last_seen     timestamp               NOT NULL default now(),
    created_at    timestamp               NOT NULL default now(),
    updated_at    timestamp               NOT NULL default now()
)