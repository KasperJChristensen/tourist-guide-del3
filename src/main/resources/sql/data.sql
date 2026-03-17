use touristguide;

INSERT INTO location (city_name) values
                                     ('Copenhagen V'),
                                     ('Copenhagen K'),
                                     ('Frederiksberg'),
                                     ('Aarhus C'),
                                     ('Roskilde'),
                                     ('Lejre');

INSERT INTO attraction (attraction_name, description, city_name) VALUES
                                                                     ('Tivoli Gardens', 'Historic amusement park and pleasure garden opened in 1843. Visitors can enjoy roller coasters, gardens, restaurants, concerts and seasonal events like Christmas and Halloween markets.','Copenhagen V'),
                                                                     ('Nyhavn', 'Colorful waterfront district with historic houses, restaurants and canal tours. A popular place for dining and sightseeing.','Copenhagen K'),
                                                                     ('The Round Tower', 'Historic 17th-century tower with a spiral ramp leading to panoramic views over Copenhagen.','Copenhagen K'),
                                                                     ('Frederiksberg Gardens', 'Large romantic landscape park with lakes, canals and walking paths located in Frederiksberg.','Frederiksberg'),
                                                                     ('ARoS Aarhus Art Museum', 'Modern art museum famous for its rainbow panorama walkway on the roof offering city views.','Aarhus C'),
                                                                     ('City Hall Square', 'Central square in Copenhagen surrounded by historic buildings and a starting point for exploring the city.','Copenhagen V'),
                                                                     ('The Viking Ship Museum', 'With the sound of the waves and smell of wet wood, the Viking Ship Museum is located right by Roskilde Fjord and it is a great visit for anyone with Viking blood in their veins. Here you have plenty of activities, history, atmosphere and beautiful harbor - you can even go sailing.','Roskilde'),
                                                                     ('The Kings Garden', 'A popular green haven with historic surroundings in the middle of Copenhagen.', 'Copenhagen K'),
                                                                     ('Strøget', 'city center to Kongens Nytorv. It is one of Europe''s longest pedestrian streets.', 'Copenhagen K')
;



INSERT INTO tags (tag) VALUES
                           ('Amusement park'),
                           ('Historic'),
                           ('Museum'),
                           ('Park'),
                           ('Architecture'),
                           ('Art'),
                           ('Nature'),
                           ('Landmark');

INSERT INTO attraction_tag (attraction_name, tag) VALUES
       ('Tivoli Gardens', 'Amusement park');

