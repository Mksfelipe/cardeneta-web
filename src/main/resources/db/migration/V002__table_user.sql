-- cardeneta.users definition

-- Drop table

-- DROP TABLE cardeneta.users;

CREATE TABLE cardeneta.users (
    id bigserial NOT NULL,
    first_name varchar(20) NULL,
    last_name varchar(20) NULL,
    email varchar(50) NULL,
    "password" varchar(120) NULL,
    cpf varchar(255) NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    account_id int8 NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_account_id_key UNIQUE (account_id),
    CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT users_cpf_key UNIQUE (cpf)
);



-- cardeneta.users foreign keys

ALTER TABLE cardeneta.users ADD CONSTRAINT fk3pwaj86pwopu3ot96qlrfo2up FOREIGN KEY (account_id) REFERENCES cardeneta.account(id);