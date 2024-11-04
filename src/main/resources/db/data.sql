

INSERT INTO wallet_entity(id,balance)
VALUES
    (301,  600.0),
    (201,  0.0),
    (401,  6000.0);

-- Insert data into the User_Entity table
INSERT INTO User_Entity(id, first_name,last_name, email, wallet_id_id, password, phone_number, created_on)
VALUES
    (501, 'praise','oyewole ','praise@gmail.com', 301, 'praise123', '09023456789', '2024-10-23T15:03:03.792009700'),
    (601,'dominic', 'nicholas ','dominic@gmail.com',401, 'dominic123','09127839212','2024-10-23T15:03:03.792009700' ),
    (701,'goodness1','mercy', 'goodness@gmail.com',201, 'goodness','09145678921','2024-10-23T15:03:03.792009700' );