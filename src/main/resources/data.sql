-- Insert into table UserRole
insert into "role" values (1, 'ROLE_ADMIN');
insert into "role" values (2, 'ROLE_USER');

-- Insert into table ImageSize
insert into "image_size" values (1, 8, 8);
insert into "image_size" values (2, 16, 16);
insert into "image_size" values (3, 32, 32);
insert into "image_size" values (4, 64, 64);
insert into "image_size" values (5, 128, 128);
insert into "image_size" values (6, 256, 256);
insert into "image_size" values (7, 512, 512);
insert into "image_size" values (8, 1024, 1024);

-- Insert into table ImageFormat
insert into "image_format" values (1, 'jpg');
insert into "image_format" values (2, 'png');
insert into "image_format" values (3, 'gif');
insert into "image_format" values (4, 'jpeg');
insert into "image_format" values (5, 'exif');
insert into "image_format" values (6, 'tiff');
insert into "image_format" values (7, 'bmp');
insert into "image_format" values (8, 'bpg');

-- Insert into table Package
insert into "package" values (1, 'FREE', 1, 10);
insert into "package" values (2, 'PRO', 8, 20);
insert into "package" values (3, 'GOLD', 25, 50);

-- Insert into table PackageImageFormat
insert into "package_image_format" values (1, 1);
insert into "package_image_format" values (1, 4);
insert into "package_image_format" values (2, 1);
insert into "package_image_format" values (2, 2);
insert into "package_image_format" values (2, 3);
insert into "package_image_format" values (2, 4);
insert into "package_image_format" values (3, 1);
insert into "package_image_format" values (3, 2);
insert into "package_image_format" values (3, 3);
insert into "package_image_format" values (3, 4);
insert into "package_image_format" values (3, 5);
insert into "package_image_format" values (3, 6);
insert into "package_image_format" values (3, 7);
insert into "package_image_format" values (3, 8);