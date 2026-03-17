use touristguide;

INSERT INTO location (city_name) values
    ('Copenhagen V');

INSERT INTO attractions (attraction_name, description, city_name) VALUES
    ('Tivoli Gardens', 'Historic amusement park and pleasure garden opened in 1843. Visitors can enjoy roller coasters, gardens, restaurants, concerts and seasonal events like Christmas and Halloween markets.','Copenhagen V');

INSERT INTO tag (tag) VALUES
    ('Amusement park');

INSERT INTO attraction_tag (attraction_name, tag) VALUES
    ('Tivoli Gardens', 'Amusement park', 'Culture', 'Sight seeing');