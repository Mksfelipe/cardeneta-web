ALTER SEQUENCE cardeneta.roles_id_seq RESTART WITH 1;
ALTER SEQUENCE cardeneta.users_id_seq RESTART WITH 1;
ALTER SEQUENCE cardeneta.account_id_seq RESTART WITH 1;
ALTER SEQUENCE cardeneta.transaction_id_seq RESTART WITH 1;

DELETE FROM cardeneta.transaction;
DELETE FROM cardeneta.user_roles;
DELETE FROM cardeneta.users;
DELETE FROM cardeneta.roles;
DELETE FROM cardeneta.account;

INSERT INTO cardeneta.roles(name) VALUES('ROLE_USER');
INSERT INTO cardeneta.roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO cardeneta.roles(name) VALUES('ROLE_ADMIN');