source libreriadb-schema.sql;
insert into users values('oriol', MD5('oriol'), 'Oriol');
insert into user_roles values ('oriol', 'admin');

insert into users values('darkunito', MD5('darkunito'), 'Darkunito');
insert into user_roles values ('darkunito', 'registered');

insert into authors values('Tolle');
insert into authors values('Casabosch');
insert into authors values('Pamies');

insert into books values(default, 'El poder del ahora', 'Tolle', 'Spanish', 'Quarta', '05/07/2002', '23/05/2013', 'Gaia');
insert into books values(default, 'Un hort a casa', 'Casabosch', 'Catalan', 'Primera', '29/03/2014', '23/02/2015', 'Ara');
insert into books values(default, 'Una dolça revolucio', 'Pamies', 'Catalan', 'Segona', '05/07/2002', '23/05/2013', 'Stevia Editors');

insert into reviews (username, name, book, content) values ('oriol', 'Oriol', 1, 'Excelente libro.');
insert into reviews (username, name, book, content) values ('oriol', 'Oriol', 2, 'Llibre interesant.');
insert into reviews (username, name, book, content) values ('darkunito', 'Darkunito', 2, 'Aporta moltes dades.');
insert into reviews (username, name, book, content) values ('darkunito', 'Darkunito', 3, 'Inspirador.')




