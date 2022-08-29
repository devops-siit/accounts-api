
insert into account ( created_at, updated_at, uuid, followers_count, following_count, biography, date_of_birth, email, gender, is_public, name, phone, username) values ( '2022-07-15 14:52:49.065758', '2022-07-15 14:52:49.065813', '7c20fb12-40d8-4322-ba33-9c05203868e9', 5, 6, 'living in Belgrade', '1990-05-08 00:00', 'strawberry@gmail.com', 'FEMALE', true, 'Jane J', '0123456', 'strawberry');

insert into account (created_at, updated_at, uuid, followers_count, following_count, biography, date_of_birth, email, gender, is_public, name, phone, username) values ( '2022-07-15 14:52:49.065758', '2022-07-15 14:52:49.065813', '7c20fb12-50d8-4322-ba33-9c05203868e9', 7, 8, 'living my best life', '1991-06-10 00:00', 'coco@gmail.com', 'FEMALE', true, 'Claudia C', '0123456', 'coco');

insert into account (created_at, updated_at, uuid, followers_count, following_count, biography, date_of_birth, email, gender, is_public, name, phone, username) values ( '2022-07-15 14:52:49.065758', '2022-07-15 14:52:49.065813', '7c20fb12-60d8-4322-ba33-9c05203868e9', 9, 10, 'living in Space', '1992-04-21 00:00', 'spacex@gmail.com', 'MALE', false, 'Elon Musk', '0123456', 'spacex');

insert into account (created_at, updated_at, uuid, followers_count, following_count, biography, date_of_birth, email, gender, is_public, name, phone, username) values ( '2022-07-15 14:52:49.065758', '2022-07-15 14:52:49.065813', '7c20fb12-70d8-4322-ba33-9c05203868e9', 9, 10, 'animal lover', '1992-04-21 00:00', 'animal@gmail.com', 'MALE', false, 'Mark Dog', '0123456', 'animal');

insert into education ( created_at, updated_at, uuid, name, title, description, start_date, end_date, account_id) values ('2022-07-15 14:52:49.065758', '2022-07-15 14:52:49.065813', '7c50fb12-50d8-4322-ba33-9c05203868e9', 'High school', 'none', '4 years', '2005-04-21 00:00', '2009-04-21 00:00', 1);

insert into work ( created_at, updated_at, uuid,  company_name, position, description, start_date, end_date, account_id) values ( '2022-07-15 14:52:49.065758', '2022-07-15 14:52:49.065813','7c10fb12-50d8-4322-ba33-9c05203868e9', 'SoftCompany', 'programmer', '4 years', '2005-04-21 00:00', '2009-04-21 00:00', 1);

insert into follow (created_at, updated_at, uuid, source_id, target_id) values  ('2022-07-15 14:52:49.065758', '2022-07-15 14:52:49.065813', '7c50fb12-50d8-4322-ba33-0c05203868e9', 1, 3);

insert into follow_request (created_at, updated_at, uuid, source_id, target_id) values  ('2022-07-15 14:52:49.065758', '2022-07-15 14:52:49.065813', '7c50fb12-50d8-4322-ba33-0c05203868e9', 2, 3);


