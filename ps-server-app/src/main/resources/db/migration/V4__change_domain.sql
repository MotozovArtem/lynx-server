ALTER TABLE ps_user
DROP COLUMN "domain";

INSERT INTO lynx_domain(id, "domain", "user")
VALUES
('a596043d-2d1c-4bc5-9d7d-801965e39060', '6ithqitxlxpetikemlsd5w3seqt5leg6au6isej7v3zfpl2f6nwazoqd.onion', 'c52236a3-7a27-496c-bf58-6c4667ead323'),
('0b0c709e-8210-4931-90ef-be9dfffdaa86', 'hcj63rhnat26ed2r5h3rqi45wbvste46ztxg3x5sihcbrjxr5zktyuyd.onion', 'c52236a3-7a27-496c-bf58-6c4667ead324');