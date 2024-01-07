
ALTER TABLE cardeneta.transaction
ADD COLUMN paid CHAR(1) DEFAULT 'Y';

ALTER TABLE cardeneta.transaction
ADD COLUMN active CHAR(1) DEFAULT 'Y';
