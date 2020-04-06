CREATE VIEW statistics AS
SELECT city, sum(studio) as studio, sum(one) as one, sum(two) as two, sum(three) as three
FROM (
         SELECT city,
                (case when num_bedrooms = 0 then total else 0 end) studio,
                (case when num_bedrooms = 1 then total else 0 end) one,
                (case when num_bedrooms = 2 then total else 0 end) two,
                (case when num_bedrooms = 3 then total else 0 end) three
         FROM (SELECT city,
                      num_bedrooms,
                      count(num_bedrooms) as total
               FROM apartment
               WHERE active = true
               GROUP BY city, num_bedrooms) num_bedrooms_by_city) num_bedrooms_type_by_city
GROUP BY city;
