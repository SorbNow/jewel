delete from article_in_order;
delete from article_order_article_in_order;
delete from article_order_articles;
delete from article_order;

insert into article_order(order_id, order_number_in1c, order_number, priority_id, order_condition, customer_id, comment, count_days_from_add_order, count_days_from_molded_date, count_not_done, done_count, expired_days) values (1, '#1', '1', 1, 2, 2, '', 0, 0, 0, 0, 20);
insert into article_order(order_id, order_number_in1c, order_number, priority_id, order_condition, customer_id, comment, count_days_from_add_order, count_days_from_molded_date, count_not_done, done_count, expired_days) values (2, '#2', '2', 1, 3, 2, '', 0, 0, 0, 0, 20);
