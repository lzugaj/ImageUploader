create table "role" (
    id_role bigserial,
    name varchar(255),
    primary key(id_role)
);

create table "user" (
	id_user bigserial,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	email varchar(255) not null,
	user_name varchar(255) not null,
	user_password varchar(80) not null,
	primary key(id_user)
);

create table "user_roles" (
	user_id bigint not null,
	role_id bigint not null,
	primary key(user_id, role_id),
	constraint fk_user
	foreign key(user_id)
	references "user" (id_user),
	constraint fk_role
	foreign key(role_id)
	references "role" (id_role)
);