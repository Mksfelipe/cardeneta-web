

ALTER TABLE cardeneta.roles
ADD COLUMN active CHAR(1) DEFAULT 'Y';

ALTER TABLE cardeneta.users
ADD COLUMN active CHAR(1) DEFAULT 'Y';

ALTER TABLE cardeneta.users
ADD COLUMN contact VARCHAR(14) DEFAULT null;
