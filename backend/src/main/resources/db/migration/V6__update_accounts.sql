CREATE EXTENSION IF NOT EXISTS pgcrypto;

insert into account (username, password, email, first_name, last_name, phone, city, country, image_id)
values ('user', crypt('P@ssw0rd', gen_salt('bf')), 'dcoil9@ox.ac.uk', 'David', 'Coil',
        '(281) 9437337', 'New York', 'USA', 1);

insert into account (username, password, email, first_name, last_name, phone, city, country, image_id)
values ('jrevett0', crypt('4WadbwFutvYn', gen_salt('bf')), 'jrevett0@g.co', 'Johnathan', 'Revett', '(707) 8552342',
        'Kaka', 'Turkmenistan', 1);

insert into account (username, password, email, first_name, last_name, phone, city, country, image_id)
values ('mrestieaux1', crypt('9aePBjyo', gen_salt('bf')), 'mrestieaux1@gov.uk', 'Marjorie', 'Restieaux',
        '(579) 8749173', 'Kaduna', 'Nigeria',
        1);

insert into account (username, password, email, first_name, last_name, phone, city, country, image_id)
values ('kbloomer2', crypt('sspYXuzLl', gen_salt('bf')), 'kbloomer2@msu.edu', 'Katusha', 'Bloomer', '(120) 3115076',
        'Monsanto', 'Portugal',
        1);

insert into account (username, password, email, first_name, last_name, phone, city, country, image_id)
values ('ldhillon3', crypt('Sw7UnDBWl', gen_salt('bf')), 'ldhillon3@webs.com', 'Liuka', 'Dhillon', '(102) 1273519',
        'Shangju', 'China', 1);

insert into account (username, password, email, first_name, last_name, phone, city, country, image_id)
values ('kpistol4', crypt('U1Hv5IRGGoK', gen_salt('bf')), 'kpistol4@bluehost.com', 'Karel', 'Pistol', '(202) 3004386',
        'Washington',
        'United States', null);

insert into account (username, password, email, first_name, last_name, phone, city, country, image_id)
values ('kstamper5', crypt('LSfHzXPSm86V', gen_salt('bf')), 'kstamper5@typepad.com', 'Kania', 'Stamper',
        '(786) 1467751', 'Miami',
        'United States', null);

insert into account (username, password, email, first_name, last_name, phone, city, country, image_id)
values ('hbodycomb6', crypt('LPzhFIp1@', gen_salt('bf')), 'hbodycomb6@independent.co.uk', 'Hall', 'Bodycomb',
        '(948) 5858767', 'Llano Largo',
        'Panama', 1);

insert into account (username, password, email, first_name, last_name, phone, city, country, image_id)
values ('roffa7', crypt('ihVDQsCUAB', gen_salt('bf')), 'roffa7@tinyurl.com', 'Ramsay', 'Offa', '(502) 9623670',
        'Lidköping', 'Sweden', 1);

insert into account (username, password, email, first_name, last_name, phone, city, country, image_id)
values ('aturmel8', crypt('JlCCynnXXgmD', gen_salt('bf')), 'aturmel8@biblegateway.com', 'Ainslie', 'Turmel',
        '(985) 4486097', null, null,
        null);

