drop database if exists touristguide;

create database touristguide;

use touristguide;

create table location (
                          id int auto_increment,
                          city_name varchar(100) not null unique,
                          primary key (id)
);

create table attraction (
                            id int auto_increment,
                            attraction_name varchar(100) not null,
                            description varchar(1000),
                            location_id int not null,
                            primary key (id),
                            foreign key (location_id) references location (id)


);

create table tags (
                      id int auto_increment primary key,
                      tag varchar(200) unique not null
);

create table attraction_tag (
                                attraction_id int not null,
                                tag_id int not null,
                                primary key (attraction_id, tag_id),
                                foreign key (attraction_id) references attraction (id) on delete cascade,
                                foreign key (tag_id) references tags (id) on delete restrict
);