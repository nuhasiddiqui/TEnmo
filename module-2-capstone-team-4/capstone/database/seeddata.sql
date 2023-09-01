-- seeddata.sql

-- Insert data for transfer_status table
INSERT INTO transfer_status (transfer_status_desc) VALUES ('Pending');
INSERT INTO transfer_status (transfer_status_desc) VALUES ('Approved');
INSERT INTO transfer_status (transfer_status_desc) VALUES ('Rejected');

-- Insert data for transfer_type table
INSERT INTO transfer_type (transfer_type_id, transfer_type_desc) VALUES (1, 'Request');
INSERT INTO transfer_type (transfer_type_id, transfer_type_desc) VALUES (2, 'Send');

-- Insert data for tenmo_user table
INSERT INTO tenmo_user (username, password_hash, role) VALUES ('user1', 'hashed_password1', 'user');
INSERT INTO tenmo_user (username, password_hash, role) VALUES ('user2', 'hashed_password2', 'user');
INSERT INTO tenmo_user (username, password_hash, role) VALUES ('user3', 'hashed_password3', 'user');
INSERT INTO tenmo_user (username, password_hash, role) VALUES ('user4', 'hashed_password4', 'user');
INSERT INTO tenmo_user (username, password_hash, role) VALUES ('user5', 'hashed_password5', 'user');

-- Insert data for account table
INSERT INTO account (user_id, balance) VALUES (1001, 1000.00);
INSERT INTO account (user_id, balance) VALUES (1002, 500.00);
INSERT INTO account (user_id, balance) VALUES (1003, 750.00);
INSERT INTO account (user_id, balance) VALUES (1004, 300.00);
INSERT INTO account (user_id, balance) VALUES (1005, 2000.00);

-- Insert data for transfer table
INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (1, 1, 2001, 2002, 100.00);
INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (2, 2, 2002, 2001, 50.00);

-- END OF FILE
