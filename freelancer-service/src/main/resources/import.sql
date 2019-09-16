insert into skill (id,name) values (1,'Java');
insert into skill (id,name) values (2,'Thorntail');
insert into skill (id,name) values (3,'JPA');
insert into freelancer (id,first_name, last_name, email) values (1,'Test', 'tester', 'test@gmail.com');
insert into freelancer (id,first_name, last_name, email) values (2,'Me', 'Myself', 'me@gmail.com');
insert into freelancer_skill (freelancer_id,skill_id) values (1,1);
insert into freelancer_skill (freelancer_id,skill_id) values (1,2);
insert into freelancer_skill (freelancer_id,skill_id) values (1,3);
insert into freelancer_skill (freelancer_id,skill_id) values (2,3);
