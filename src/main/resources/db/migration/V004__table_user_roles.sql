-- cardeneta.user_roles definition

-- Drop table

-- DROP TABLE cardeneta.user_roles;

CREATE TABLE cardeneta.user_roles (
    role_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT cardeneta.user_roles_pkey PRIMARY KEY (role_id, user_id),
    FOREIGN KEY (role_id) REFERENCES cardeneta.roles(id),
    FOREIGN KEY (user_id) REFERENCES cardeneta.users(id),
);


-- cardeneta.user_roles foreign keys

ALTER TABLE cardeneta.user_roles ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES cardeneta.roles(id);
ALTER TABLE cardeneta.user_roles ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES cardeneta.users(id);