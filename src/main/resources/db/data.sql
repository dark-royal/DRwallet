


INSERT INTO User_Entity(id, name, email,wallet_id_id, password, phone_number,created_on)

   values (100, 'praise', 'praise@gmail.com', 200,'praise123','09023456789','2024-10-23T15:03:03.792009700
'),
          (101, 'michael', 'michael@gmail.com', 201,'michael123','08067843212','2024-10-23T15:03:03.792009700
'),
          (102, 'praise', 'vicky@gmail.com', 202,'vicky123','09123212345','2024-10-23T15:03:03.792009700
');

INSERT INTO Wallet_Entity(id, user_id_id, balance) values
            (200,100,600.00),
            (201,101,0.00),
            (202,102,6000.00);




