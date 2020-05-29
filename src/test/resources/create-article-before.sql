delete from article;
delete from personal;
delete from metal_type;

insert into personal(id, login, encoded_password,user_role) values (1, 'master', '123','АДМИНИСТРАТОР');

insert into metal_type(id, metal_type_name, hallmark) values (1, 'gold', 750);
insert into metal_type(id, metal_type_name, hallmark) values (2, 'silver', 750);

insert into article(article_id, article_name, metal_type,dummy_article_name) values (1, '#1', 1,'1gold750');
insert into article(article_id, article_name, metal_type,dummy_article_name) values (2, '#1', 2,'1silver750');
insert into article(article_id, article_name, metal_type,dummy_article_name) values (3, '#2silver', 2,'#2silversilver750');

