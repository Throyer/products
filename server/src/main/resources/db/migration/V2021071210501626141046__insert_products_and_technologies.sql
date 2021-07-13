INSERT INTO "technology"
    ("name")
VALUES
    ('Spring Boot'),
    ('AspNet Core'),
    ('ReactJs'),
    ('PHP'),
    ('AngularJs'),
    ('Angular2+'),
    ('NodeJs'),
    ('MySQL'),
    ('SQL Server'),
    ('PostgreSQL'),
    ('Oracle'),
    ('MongoDB'),
    ('AWS'),
    ('Azure'),
    ('Digital Ocean'),
    ('Heroku');

INSERT INTO "product"
    ("name", "description", "target_market")
VALUES
    ('University management', 'example product', 'Education'),
    ('Crypto dashboard', 'example product', 'Economy');

INSERT INTO "product_technology"
    ("product_id", "technology_id")
VALUES
    (
        (SELECT id FROM "product" WHERE "name" = 'University management'),
        (SELECT id FROM "technology" WHERE "name" = 'Spring Boot')
    ),
    (
        (SELECT id FROM "product" WHERE "name" = 'University management'),
        (SELECT id FROM "technology" WHERE "name" = 'ReactJs')
    ),
    (
        (SELECT id FROM "product" WHERE "name" = 'University management'),
        (SELECT id FROM "technology" WHERE "name" = 'MySQL')
    ),
    (
        (SELECT id FROM "product" WHERE "name" = 'University management'),
        (SELECT id FROM "technology" WHERE "name" = 'Heroku')
    ),
    (
        (SELECT id FROM "product" WHERE "name" = 'Crypto dashboard'),
        (SELECT id FROM "technology" WHERE "name" = 'Angular2+')
    ),
    (
        (SELECT id FROM "product" WHERE "name" = 'Crypto dashboard'),
        (SELECT id FROM "technology" WHERE "name" = 'NodeJs')
    ),
    (
        (SELECT id FROM "product" WHERE "name" = 'Crypto dashboard'),
        (SELECT id FROM "technology" WHERE "name" = 'MongoDB')
    );