select * from information_schema.tables;

delete from verification_token;
delete from user_roles;
delete from student;

-- Professor Tags
insert into tag values('1','FEEDBACK');
insert into tag values('2','HOMEWORK');
insert into tag values('3','READING');
insert into tag values('4','RESPECT');
insert into tag values('5','ATTENDANCE');
insert into tag values('6','INSPIRATIONAL');
insert into tag values('7','EASY_GRADE');
insert into tag values('8','TOUGH_GRADE');
insert into tag values('9','TEST_HEAVY');
insert into tag values('10','GROUP_PROJECTS');
insert into tag values('11','HILARIOUS');
insert into tag values('12','POP_QUIZZES');
insert into tag values('13','AMAZING_LECTURES');
insert into tag values('14','LECTURE_HEAVY');
insert into tag values('15','CARING');
insert into tag values('16','BONUS_POINTS');
insert into tag values('17','MANY_PAPERS');

-- User Roles
insert into roles values('1', 'ROLE_ADMIN');
insert into roles values('2', 'ROLE_MODERATOR');
insert into roles values('3', 'ROLE_USER');

-- Courses
insert into course values('1', 'GCHINA 101', 'China in the World');
insert into course values('2', 'GLOCHALL 201', 'Global Challenges in Science, Technology and Health');
insert into course values('3', 'ETHLDR 201', 'Ethics, Citizenship and the Examined Life');
insert into course values('4', 'COMPSCI 201', 'Introduction to Programming and Data Structures');
