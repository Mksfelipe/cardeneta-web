ALTER SEQUENCE cardeneta.roles_id_seq RESTART WITH 1;
ALTER SEQUENCE cardeneta.users_id_seq RESTART WITH 1;
ALTER SEQUENCE cardeneta.account_id_seq RESTART WITH 1;
ALTER SEQUENCE cardeneta.transaction_id_seq RESTART WITH 1;

DELETE FROM cardeneta.transaction;
DELETE FROM cardeneta.user_roles;
DELETE FROM cardeneta.users;
DELETE FROM cardeneta.roles;
DELETE FROM cardeneta.account;

INSERT INTO cardeneta.account (id, balance, created, updated, active) VALUES(nextval('cardeneta.account_id_seq'::regclass), 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Y'::bpchar);

INSERT INTO cardeneta.users (id, first_name, last_name,	email,	"password",	cpf, created, updated,	account_id,	active,	contact) VALUES (nextval('cardeneta.users_id_seq'::regclass),'FELIPE','SOUSA','sousafelipe123@gmail.com','$2a$10$srioWy1sCAQbr7S9mOX2HOnM91PurhxneyX15rJMKbjOUoPkYMouy','07782169348',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,'Y'::bpchar,null::character varying);

INSERT INTO cardeneta.roles(name) VALUES('ROLE_USER');
INSERT INTO cardeneta.roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO cardeneta.roles(name) VALUES('ROLE_ADMIN');

INSERT INTO cardeneta.user_roles (role_id, user_id) VALUES(2, 1);
INSERT INTO cardeneta.user_roles (role_id, user_id) VALUES(3, 1);
INSERT INTO cardeneta.user_roles (role_id, user_id) VALUES(1, 1);