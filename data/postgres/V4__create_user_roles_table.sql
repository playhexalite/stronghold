CREATE TABLE IF NOT EXISTS user_roles
(
    user_id     uuid                      NOT NULL,
    role_id     varchar(18)               NOT NULL,
    assign_date timestamp                 NOT NULL DEFAULT now(),
    assign_id   serial UNIQUE PRIMARY KEY NOT NULL
)