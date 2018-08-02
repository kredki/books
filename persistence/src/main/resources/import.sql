insert into book (id, title, authors, status) values (1, 'First book', 'Jan Kowalski', 'FREE');
insert into book (id, title, authors, status) values (2, 'Second book', 'Zbigniew Nowak', 'FREE');
insert into book (id, title, authors, status) values (3, 'Third book', 'Janusz Jankowski', 'FREE');
insert into book (id, title, authors, status) values (4, 'Starter kit book', 'Kacper Ossoliński', 'FREE');
insert into book (id, title, authors, status) values (5, 'Z kamerą wśród programistów', 'Krystyna Czubówna', 'MISSING');

insert into user (id, user_name, password, role) values (1, 'admin', '$2a$10$J6UMSPzpMJSUcDf.RW4nBOSpDpSdPVInV1BdKU0998fO9FbKkYTKi', 'ROLE_ADMIN');
insert into user (id, user_name, password, role) values (2, 'user', '$2a$10$lvTQf/haMh25t3qDGNaWg.ILTKQdQlh/T06rqVmS3TO/iq/5oOcmW', 'ROLE_USER');