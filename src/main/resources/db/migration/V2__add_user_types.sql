CREATE TABLE challenge.user_types (
    id   UUID         NOT NULL,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT pk_user_types PRIMARY KEY (id),
    CONSTRAINT uq_user_types_name UNIQUE (name)
);

ALTER TABLE challenge.users
    ADD COLUMN user_type_id UUID,
    ADD CONSTRAINT fk_users_user_types FOREIGN KEY (user_type_id) REFERENCES challenge.user_types (id);
