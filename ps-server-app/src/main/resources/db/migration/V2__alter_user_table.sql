CREATE TABLE ps_ftp_user (
    id VARCHAR(256),
    user_id VARCHAR(256) NOT NULL,
    username VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL,
    enabled BOOL NOT NULL DEFAULT FALSE,
    admin BOOL NOT NULL DEFAULT FALSE,
    CONSTRAINT ps_ftp_user_pkey_id PRIMARY KEY (id)
);

ALTER TABLE ps_ftp_user
    ADD CONSTRAINT ps_ftp_user_ps_user_user_id_fkey
        FOREIGN KEY(user_id) REFERENCES ps_user(id);

