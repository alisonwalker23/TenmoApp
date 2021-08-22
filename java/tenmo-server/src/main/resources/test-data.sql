TRUNCATE accounts, transfer_statuses, transfer_types, transfers, users CASCADE;

INSERT INTO users (user_id, username, password_hash)
VALUES (9999, 'harrypotter', 'harrypotter'),
       (9991, 'hermionegranger', 'hermionegranger'),
       (9992, 'dracomalfoy', 'dracomalfoy');

INSERT INTO accounts (account_id, user_id, balance)
VALUES (1234, 9999, 1000),
       (2345, 9991, 1000),
       (3456, 9999, 1000);

ALTER TABLE transfer_statuses ALTER COLUMN transfer_status_id DROP NOT NULL;
ALTER TABLE transfer_statuses ALTER COLUMN transfer_status_desc DROP NOT NULL;
ALTER TABLE transfer_types ALTER COLUMN transfer_type_id DROP NOT NULL;
ALTER TABLE transfer_types ALTER COLUMN transfer_type_desc DROP NOT NULL;

INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)
VALUES (1987, 2, 2, 1234, 2345, 50),
       (9876, 2, 2, 2345, 1234, 90.50),
       (8765, 2, 2, 3456, 2345, 20.23);

INSERT INTO transfer_types (transfer_type_id, transfer_type_desc)
VALUES (2, 'Send');

INSERT INTO transfer_statuses (transfer_status_id, transfer_status_desc)
VALUES (2, 'Approved');