CREATE TABLE clan_members
(
    member_id uuid UNIQUE NOT NULL,
    clan_id   integer     NOT NULL,
    role      varchar(16) NOT NULL,
    PRIMARY KEY (member_id, clan_id),
    CONSTRAINT fk_clan_id FOREIGN KEY (clan_id) REFERENCES clans (id),
    CONSTRAINT fk_member_id FOREIGN KEY (member_id) REFERENCES users (id)
)