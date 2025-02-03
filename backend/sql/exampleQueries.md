# User Data Queries
## Modify data
### Add new user with default pfp and ban status (input user_id, username, email)
INSERT INTO user_data (user_id, public_name, pfp_path, ban_status, email)
VALUES (integer, 'username', '/default.png', -1, 'user@email.com');

### Delete user (input user_id)
DELETE FROM user_data
    WHERE user_id = idInteger;

### Change username (input user_id, username)
UPDATE user_data
    SET public_name = 'newUsername'
    WHERE user_id = idInteger;

### Modify pfp (input user_id, image path)
UPDATE user_data
    SET pfp_path = '/newPfp.png'
    WHERE user_id = idInteger;

### Modify email (input user_id, email)
UPDATE user_data
    SET email = 'new@email.com'
    WHERE user_id = idInteger;

### Modify ban status (input user_id, new ban status)
UPDATE user_data
    SET ban_status = banInteger
    WHERE user_id = idInteger;

## Extract data
### Receive whole table
SELECT * FROM user_data;


# User/Room Location Queries
## Update user location (input user_id, location)

## Update room location (input room_id, location)

## Check if user is in range of any rooms (input user_id)

## Clear user location / set location to null (input user_id)



# Chat Queries
## Add new message (text only) (input user_id, text, timestamp, room_id)


## Add new message (image only) (input user_id, image location, timestamp, room_id)


## 



# Chat Room Queries
## Join room (input user_id and room_id)


## Leave room/kick user (input user_id and room_id)


## Transfer host permission (input user_id and room_id)


## Close Room (input room_id)


