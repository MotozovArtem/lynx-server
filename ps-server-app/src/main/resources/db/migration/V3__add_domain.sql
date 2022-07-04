CREATE TABLE lynx_domain (
    id  VARCHAR(255) NOT NULL,
    "domain" VARCHAR(255) NOT NULL,
    "user" VARCHAR(255) NOT NULL,
    CONSTRAINT lynx_domain_pkey_id PRIMARY KEY(id)
);

ALTER TABLE lynx_domain
ADD CONSTRAINT lynx_domain_unq_domain UNIQUE("domain");

ALTER TABLE lynx_domain
ADD CONSTRAINT lynx_domain_user_fkey FOREIGN KEY ("user") REFERENCES ps_user(id);