use sap_library;

insert into users (username, phone) values ('Ivan Ivanov', '0893601678'),
('Georgi Georgiev', '0890890891'),
('Dimitar Dimitrov', '0891234567');

insert into books (author, title, datepublished, quantity) values
('Ivan Vazov', 'Pod Igoto', '1894-05-06', 10),
('Unknown', 'SomeBook', '2000-01-01', 10),
('George R. R. Martin', 'A song of Ice and Fire', '2002-03-05', 10);

insert into bookstaken (book_id, user_id, deadline) values 
(9, 1, '2018-12-31');