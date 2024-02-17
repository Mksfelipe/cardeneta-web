-- cardeneta.account definition

-- Drop table

-- DROP TABLE cardeneta.account;

CREATE TABLE account (
    id bigserial NOT NULL,
    balance DECIMAL(10,2) DEFAULT 0,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    active CHAR(1) DEFAULT 'Y',
    CONSTRAINT account_pkey PRIMARY KEY (id)
);

