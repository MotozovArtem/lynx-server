CREATE TABLE ps_role(
    id  VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT ps_role_pkey_id PRIMARY KEY(id)
);

INSERT INTO ps_role(id, name)
VALUES ('bdb17a9c-4f92-4ef1-a4e2-1a441a94f2b1', 'ROLE_USER');

CREATE TABLE ps_user (
    id  VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    "domain" VARCHAR(255),
    last_time_active TIMESTAMP NOT NULL,
    online BOOLEAN NOT NULL DEFAULT TRUE,
    creation_time TIMESTAMP NOT NULL,
    ts BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT ps_user_pkey_id PRIMARY KEY(id)
);

ALTER TABLE ps_user
ADD CONSTRAINT ps_user_unq_name UNIQUE(name);

ALTER TABLE ps_user
ADD CONSTRAINT ps_user_unq_login UNIQUE(login);

ALTER TABLE ps_user
ADD CONSTRAINT ps_user_unq_domain UNIQUE("domain");

-- TODO: Delete after creating on user
INSERT INTO ps_user(id, login, name, password, "domain", last_time_active, online, creation_time, ts)
VALUES ('c52236a3-7a27-496c-bf58-6c4667ead322', 'admin', 'admin',
        '$2a$10$PSJTkavSZJ95Q9mONZ.6n.Iq6uvgRAO9jLX7G8pSH/BnAv60ICS7i', 'a23sdf1as44dfasasdf1324af736d9sc6vcy4b52384.onion', now(), false, now(), 0),
    ('c52236a3-7a27-496c-bf58-6c4667ead323', 'Ben', 'Ben',
        '$2a$10$PSJTkavSZJ95Q9mONZ.6n.Iq6uvgRAO9jLX7G8pSH/BnAv60ICS7i', 'slxtxi5jxly6zzlj7wpjp3hpycql22miymxzvzfumakzfiapeat45did.onion', now(), true, now(), 0),
    ('c52236a3-7a27-496c-bf58-6c4667ead324', 'Alice', 'Alice',
        '$2a$10$PSJTkavSZJ95Q9mONZ.6n.Iq6uvgRAO9jLX7G8pSH/BnAv60ICS7i', 'ppgkb5rpev3dwc76xkqcmjp2we3cwmb3gnddlqk6ofnmmqzpxtnuziyd.onion', now(), true, now(), 0);