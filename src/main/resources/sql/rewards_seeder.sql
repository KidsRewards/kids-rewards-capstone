use springblog_db;

drop table books;
drop table authors;

insert into authors(name) VALUES ('Victor Hugo'), ('George Orwell'), ('John Steinbeck');

insert into books(author_id, title) VALUES (1, 'Notre-Dame de Paris'), (2, 'Animal Farm'), (3, 'Of Mice and Men');

insert into books(title, author_id) VALUES ('Les Miserables', 1);

insert into genres(name) VALUES ('fiction'), ('non-fiction'), ('satire'), ('feel-good'), ('tragedy'), ('has rabbits');

insert into books_genres(book_id, genre_id) VALUES (1, 1), (1, 5), (2, 1), (2, 3), (2, 6), (3, 1), (3, 5), (3, 6), (4, 1), (4, 5);

insert into genres(id, name) VALUES (99, 'ranch dressing');