drop table if exists attraction_tag;
drop table if exists attraction;
drop table if exists location;
drop table if exists tags;

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
                      tag varchar(200) unique
);

create table attraction_tag (
                                attraction_id int not null,
                                tag_id int not null,
                                primary key (attraction_id, tag_id),
                                foreign key (attraction_id) references attraction (id) on delete cascade,
                                foreign key (tag_id) references tags (id) on delete restrict
);

INSERT INTO location (city_name) VALUES ('København V');
INSERT INTO location (city_name) VALUES ('Køge');
INSERT INTO location (city_name) VALUES ('Roskilde');


INSERT INTO attraction (attraction_name, description, location_id) VALUES ('Tivoli Gardens', 'An amusement park in the center of Copenhagen', 1);
INSERT INTO attraction (attraction_name, description, location_id) VALUES ('The Viking Ship Museum',
                                                                               'With the sound of the waves and smell of wet wood, the Viking Ship Museum is located right by Roskilde Fjord', 3);

INSERT INTO tags (tag) VALUES ('Culture');
INSERT INTO tags (tag) VALUES ('History');

INSERT INTO attraction_tag (attraction_id, tag_id) VALUES (1,1);
INSERT INTO attraction_tag (attraction_id, tag_id) VALUES (2,2);