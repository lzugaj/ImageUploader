create table "role" (
	id_role bigserial not null,
	name varchar(255),
	primary key(id_role)
);

create table "user_profile" (
	id_user_profile bigserial not null,
	user_description varchar(4096),
	country_name varchar(255),
	city_name varchar(255),
	position_name varchar(255),
	company_name varchar(255),
	start_year int,
	end_year int,
	is_working_here boolean,
	school_name varchar(255),
	concentration_name varchar(255),
	secondary_concentration_name varchar(255),
	degree_type_name varchar(255),
	graduation_year int,
	profile_image bytea,
	primary key(id_user_profile)
);

create table "image_size" (
	id_image_size bigserial not null,
	width int,
	height int,
	primary key(id_image_size)
);

create table "image_format" (
	id_image_format bigserial not null,
	extension_name varchar(50),
	primary key(id_image_format)
);

create table "package" (
	id_package bigserial not null,
	name varchar(255),
	upload_size int,
	daily_upload_limit int,
	primary key(id_package)
);

create table "user" (
	id_user bigserial not null,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	email varchar(255) not null,
	user_name varchar(255) not null,
	user_password varchar(80) not null,
	number_of_posts int,
	user_profile_id bigint,
	package_id bigint,
	primary key(id_user),
	constraint fk_user_profile
	foreign key(user_profile_id)
	references "user_profile"(id_user_profile)
	on delete cascade,
	constraint fk_user_package
	foreign key(package_id)
	references "package"(id_package)
	on delete cascade
);

create table "user_roles" (
	user_id bigint not null,
	role_id bigint not null,
	primary key(user_id, role_id),
	constraint fk_user_user_role
	foreign key(user_id)
	references "user" (id_user)
	on delete cascade,
	constraint fk_role_user_roles
	foreign key(role_id)
	references "role" (id_role)
	on delete cascade
);

create table "package_image_format" (
	package_id bigint not null,
	image_format_id bigint not null,
	primary key(package_id, image_format_id),
	constraint fk_package_image_format
	foreign key(package_id)
	references "package" (id_package)
	on delete cascade,
	constraint fk_image_format_package
	foreign key(image_format_id)
	references "image_format" (id_image_format)
	on delete cascade
);

create table "hash_tag" (
	id_hash_tag bigserial not null,
	name varchar(255) not null,
	primary key(id_hash_tag)
);

create table "post" (
	id_post bigserial not null,
	description varchar(1024) not null,
	post_image bytea not null,
	date_of_post timestamp not null,
	number_of_likes int,
	number_of_downloads int,
	user_id bigint not null,
	image_size_id bigint not null,
	image_format_id bigint not null,
	primary key(id_post),
	constraint fk_user_post
	foreign key(user_id)
	references "user"(id_user),
	constraint fk_image_size_post
	foreign key(image_size_id)
	references "image_size"(id_image_size)
	on delete cascade,
	constraint fk_image_format_post
	foreign key(image_format_id)
	references "image_format"(id_image_format)
	on delete cascade
);

create table "post_hash_tag" (
	post_id bigint not null,
	hash_tag_id bigint not null,
	primary key(post_id, hash_tag_id),
	constraint fk_post_hash_tag
	foreign key(post_id)
	references "post" (id_post)
	on delete cascade,
	constraint fk_hash_tag_post
	foreign key(hash_tag_id)
	references "hash_tag" (id_hash_tag)
	on delete cascade
);

create table "comment" (
	id_comment bigserial not null,
	description varchar(1024) not null,
	number_of_likes int,
	number_of_dislikes int,
	date_of_post timestamp not null,
	user_id bigint not null,
	post_id bigint not null,
	primary key(id_comment),
	constraint fk_user_comment
	foreign key(user_id)
	references "user"(id_user)
	on delete cascade,
	constraint fk_post_comment
	foreign key(post_id)
	references "post"(id_post)
	on delete cascade
);

create table "download_image" (
    id_download_image bigserial not null,
    date_of_download timestamp not null,
    user_id bigint not null,
    post_id bigint not null,
    primary key(id_download_image),
    constraint fk_user_download_image
	foreign key(user_id)
	references "user"(id_user)
	on delete cascade,
	constraint fk_post_download_image
	foreign key(post_id)
	references "post"(id_post)
	on delete cascade
);