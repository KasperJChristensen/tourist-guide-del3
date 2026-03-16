drop database if exists touristguide;

create database touristguide;

use touristguide;

create table location (
                          location_name varchar(100) primary key,
                          unique (location_name)
);

create table attraction (
                            attraction_name varchar(100) not null,
                            description varchar(1000),
                            location_name varchar(100) not null,
                            primary key (attraction_name, location_name),
                            foreign key (location_name) references location (location_name) on delete cascade
);

create table tags (
                      tag varchar(200) primary key
);

create table attraction_tag (
                                attraction_name varchar(100) not null,
                                tag varchar(200) not null,
                                primary key (attraction_name, tag),
                                foreign key (attraction_name) references attraction (attraction_name) on delete cascade,
                                foreign key (tag) references tags (tag) on delete restrict
);