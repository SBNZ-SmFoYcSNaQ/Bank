INSERT INTO user_ (id, user_type, address, birthdate, email, firstname, lastname, password, phone, role, sex)
VALUES
    ('42f732e5-1b01-4a01-97a3-3f448d1fda24', 'client', 'Bulvr Despota Stefana 5', '1995-02-12', 'client@gmail.com', 'Client', 'Client', '$2a$10$t8xm3Ea2.jNAjFu9c3ygEuzQMXWyl/0yTrP5wIZZlGLDM5GMlzVl.', '0645678901', 0, 0),
    ('de21c32e-ebf8-49f4-97ef-721e4f5ed313', 'client', 'Bulvr Despota Stefana 6', '1938-09-25', 'client3@gmail.com', 'Client3', 'Client3', '$2a$10$t8xm3Ea2.jNAjFu9c3ygEuzQMXWyl/0yTrP5wIZZlGLDM5GMlzVl.', '0631234567', 0, 1),
    ('3af491f3-6216-4f2b-aed2-8d2c9bbbc77c', 'banking_officer', 'Bulvr Despota Stefana 7', '1976-07-05', 'officer@gmail.com', 'Banking', 'Officer', '$2a$10$t8xm3Ea2.jNAjFu9c3ygEuzQMXWyl/0yTrP5wIZZlGLDM5GMlzVl.', '0659876543', 1, 0),
    ('a12a570d-72b6-4c64-8cbf-b0ad0be47a61', 'banking_officer', 'Bulvr Despota Stefana 8', '1982-11-30', 'officer3@gmail.com', 'Banking3', 'Officer3', '$2a$10$t8xm3Ea2.jNAjFu9c3ygEuzQMXWyl/0yTrP5wIZZlGLDM5GMlzVl.', '0623456789', 1, 1),
    ('e87256b2-8ed9-4f08-9e0d-83e7001d7643', 'client', 'Bulvr Despota Stefana 9', '1993-06-18', 'client4@gmail.com', 'Client4', 'Client4', '$2a$10$t8xm3Ea2.jNAjFu9c3ygEuzQMXWyl/0yTrP5wIZZlGLDM5GMlzVl.', '0665432198', 0, 1),
    ('fc8d67f0-01a7-4ce0-ae9d-b04d8364ae16', 'client', 'Bulvr Despota Stefana 10', '1990-04-27', 'client5@gmail.com', 'Client5', 'Client5', '$2a$10$t8xm3Ea2.jNAjFu9c3ygEuzQMXWyl/0yTrP5wIZZlGLDM5GMlzVl.', '0632145698', 0, 0),
    ('a9826c38-612d-4e44-9e74-2f3f518fbb6e', 'banking_officer', 'Bulvr Despota Stefana 11', '1985-08-09', 'officer4@gmail.com', 'Banking4', 'Officer4', '$2a$10$t8xm3Ea2.jNAjFu9c3ygEuzQMXWyl/0yTrP5wIZZlGLDM5GMlzVl.', '0654321987', 1, 0),
    ('2dcdbd3e-18f9-46de-b9e9-4b9180dcebc6', 'banking_officer', 'Bulvr Despota Stefana 12', '1979-03-14', 'officer5@gmail.com', 'Banking5', 'Officer5', '$2a$10$t8xm3Ea2.jNAjFu9c3ygEuzQMXWyl/0yTrP5wIZZlGLDM5GMlzVl.', '0647891234', 1, 1),
    ('625b422d-678a-478e-a184-3d438239063b', 'client', 'Bulvr Despota Stefana 13', '1998-12-22', 'client6@gmail.com', 'Client6', 'Client6', '$2a$10$t8xm3Ea2.jNAjFu9c3ygEuzQMXWyl/0yTrP5wIZZlGLDM5GMlzVl.', '0632198765', 0, 1),
    ('5a84f77d-aa7e-43ea-9dc7-92c7810c50d6', 'client', 'Bulvr Despota Stefana 14', '1991-10-11', 'client7@gmail.com', 'Client7', 'Client7', '$2a$10$t8xm3Ea2.jNAjFu9c3ygEuzQMXWyl/0yTrP5wIZZlGLDM5GMlzVl.', '0623456789', 0, 0);

INSERT INTO bank_card (id, cvv_cvc, expiration_date, number, owner_name)
VALUES
    ('1e8ce8c9-4df1-42dd-a1e3-7929350518c3', '456', '2024-09-15', '6011111111111117', 'Client Client'),
    ('b56ef3be-37e6-4c2b-a73f-5234627edf8a', '789', '2023-12-31', '3547238487657541', 'Client3 Client3'),
    ('15bcab46-2a59-4be2-92c2-5fc61ff29a8c', '123', '2025-06-20', '5105105105105100', 'Client Client'),
    ('bae836f1-59e1-482c-a826-4d3a3a7c9fb2', '890', '2026-03-05', '378734493671000', 'Client3 Client3'),
    ('1bfc1842-9d8c-4e02-a29d-587a5fbbd11e', '234', '2024-07-29', '3566002020360505', 'Client4 Client4'),
    ('92f4b0de-dfa6-41e4-b7d7-1a649268d137', '567', '2023-11-12', '5555555555554444', 'Client Client'),
    ('e8b4d9da-9f08-4e82-8b20-9982ef88a04e', '901', '2025-02-18', '6759649826438453', 'Client Client'),
    ('4a7486ed-ef03-46c4-95b6-c02e57248c20', '234', '2026-08-08', '4111111111111111', 'Client5 Client5'),
    ('07e8e1a1-6e3d-4a03-81f4-b3086a99e09e', '345', '2024-05-06', '6011000990139424', 'Client Client'),
    ('dcd90a3e-ebd6-4b3e-918f-69e275d06ef9', '678', '2023-10-22', '3530111333300000', 'Client6 Client6');

INSERT INTO employment_info (id, employment_end_date, employment_start_date, salary)
VALUES
    ('f1a5a6e0-5e2a-48ac-9f83-4b2d1e7eb7f1', null, '2022-05-20', 35000),
    ('c5eef964-2ef9-4b5a-8956-41d0ae28a6ee', '2023-11-30', '2021-11-30', 45000),
    ('714d456e-dc3d-43c4-8e8b-6b2c30b08c9b', '2024-08-15', '2020-08-15', 55000),
    ('993edce2-5673-4c48-a31d-69be2e53b8c3', null, '2022-02-28', 40000),
    ('ec68d682-7f61-4c7d-aec0-54d4993d05b2', '2027-07-10', '2021-07-10', 48000),
    ('924cf4a4-1f97-4d6a-aede-0819484de7e3', '2023-09-05', '2020-09-05', 42000),
    ('d3c1392f-7ea5-4496-97a0-62a9fe3c6ab7', '2024-12-01', '2021-12-01', 55000),
    ('b60a429d-9398-47f9-9dc4-1a7b7df13d71', null, '2020-06-18', 40000),
    ('1e6af156-0cbb-4b13-a28c-c9e4e4d442da', '2023-10-30', '2021-10-30', 45000),
    ('77a3ef56-41ae-4c77-b9c6-95e6ee71d5bc', '2026-04-30', '2022-04-30', 42000);


INSERT INTO credit (id, amount, payments_number, maximum_repayment_period, minimum_repayment_period, status) VALUES
                                                                                                                 ('7914c91a-139f-11ee-be56-0242ac120002', 350000, 24, '2026-04-10', '2024-04-10', 1),
                                                                                                                 ('3e4e0d59-3d63-4b20-b24e-3dd5d8a33a52', 500000, 36, '2027-01-15', '2024-01-15', 0),
                                                                                                                 ('8faef9a7-85b1-4c0b-8d94-eeff28ee228d', 200000, 12, '2026-11-02', '2024-11-02', 1),
                                                                                                                 ('58f97e9e-3d5d-4e8a-8d8a-34d1d52e4554', 800000, 48, '2028-03-20', '2024-03-20', 2),
                                                                                                                 ('c3f41eb1-cd36-4b0a-987a-b824a0c65c7f', 300000, 24, '2026-09-05', '2024-09-05', 1),
                                                                                                                 ('9a3dc0c1-48e0-4ff7-9d2e-76b827e9b8a2', 400000, 24, '2026-06-18', '2024-06-18', 3),
                                                                                                                 ('f41e1a33-b141-48f4-94e9-9c1ae133f8a1', 250000, 18, '2025-12-30', '2024-12-30', 3),
                                                                                                                 ('c8935427-f4b0-4e84-8a38-9c59f39bc954', 600000, 36, '2027-04-10', '2024-04-10', 1),
                                                                                                                 ('5eddbf33-3733-41c2-a39e-5ce7b0ff2e23', 150000, 12, '2026-10-27', '2024-10-27', 2),
                                                                                                                 ('739d3db6-34a0-4eef-b9ae-d56a037e2c88', 700000, 48, '2028-02-05', '2024-02-05', 2),
                                                                                                                 ('4c5c75f7-9f9e-4500-8d25-8d4327da7908', 350000, 24, '2026-07-15', '2024-07-15', 1);

INSERT INTO bank_account (id, balance, number, bank_card_id, client_id) VALUES
    ('ffa7b032-139f-11ee-be56-0242ac120002', 2000, '1700032332018', '1e8ce8c9-4df1-42dd-a1e3-7929350518c3', '42f732e5-1b01-4a01-97a3-3f448d1fda24'),
    ('e702de8a-7e6b-414e-9e96-6282d81d1e12', 15000, '1700032332019', 'b56ef3be-37e6-4c2b-a73f-5234627edf8a', '42f732e5-1b01-4a01-97a3-3f448d1fda24'),
    ('fd20e2d6-1df2-4a5f-b5c4-b5d881aaf846', 50000, '1700032332020', '15bcab46-2a59-4be2-92c2-5fc61ff29a8c', 'de21c32e-ebf8-49f4-97ef-721e4f5ed313'),
    ('105f2f3f-3db1-4b46-bc43-05b5e4e95b81', 100000, '1700032332021', 'bae836f1-59e1-482c-a826-4d3a3a7c9fb2', '42f732e5-1b01-4a01-97a3-3f448d1fda24'),
    ('f1c51cbf-2e51-4d89-88ce-55a2f3ed1371', 75000, '1700032332022', '1bfc1842-9d8c-4e02-a29d-587a5fbbd11e', 'de21c32e-ebf8-49f4-97ef-721e4f5ed313'),
    ('e7a8d7f3-f42d-4a6e-80a9-347c8f892dcd', 40000, '1700032332025', '4a7486ed-ef03-46c4-95b6-c02e57248c20', '42f732e5-1b01-4a01-97a3-3f448d1fda24');

INSERT INTO transaction (id, amount, creation_time, execution_time, location, status, bank_account_id) VALUES
    ('607db692-13a8-11ee-be56-0242ac120002', 3560, '2023-06-28 10:39:37', '2023-04-11 10:39:37', '212.200.65.93', 3, 'ffa7b032-139f-11ee-be56-0242ac120002'),
    ('6e22a872-21d2-4a9d-a2c3-17e485b76dcf', 1500, '2023-04-12 14:20:50', '2023-04-13 14:20:50', '212.200.65.93', 1, 'e702de8a-7e6b-414e-9e96-6282d81d1e12');

INSERT INTO suspicious_transaction (id, message, transaction_id) VALUES
    ('d3df90a2-1591-11ee-be56-0242ac120002', 'Many transactions from different locations in close time.', '607db692-13a8-11ee-be56-0242ac120002');