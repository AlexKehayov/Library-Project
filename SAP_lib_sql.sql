use sap_library;

create table staff(
username varchar(50) unique,
password varchar(50));

create table users(
id int auto_increment primary key,
username varchar(50),
phone varchar(15));

create table books(
id int auto_increment primary key,
title varchar(50),
author varchar(50),
datePublished date,
quantity int
);


create table booksTaken(
id int auto_increment,
book_id int,
user_id int,
foreign key (book_id) references books(id) on delete cascade,
foreign key (user_id) references users(id) on delete cascade,
deadline date,
primary key(id, book_id, user_id)
);

