CREATE TABLE IF NOT EXISTS user_data (
    user_id         BIGINT PRIMARY KEY,
    public_name     VARCHAR(64) NOT NULL,
    pfp_path        VARCHAR(256),
    ban_status      INT,
    email           VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS location_data (
    uid            VARCHAR(64) references user_data(user_id) PRIMARY KEY,
    location        GEOGRAPHY(POINT, 4326) GENERATED ALWAYS AS (
                        ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)
                    ) STORED
);

CREATE TABLE IF NOT EXISTS room_data (
    room_id         INT PRIMARY KEY,
    host            VARCHAR(64) references user_data(user_id),
    location        GEOGRAPHY(POINT, 4326)
);

CREATE TABLE IF NOT EXISTS room_users (
    room_id         INT references room_data(room_id),
    uid             INT references user_data(user_id) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS chat_data (
    user            VARCHAR(64) references user_data(hidden_uid),
    watermark       TIMESTAMP NOT NULL,
    rid             INT references room_data(room_id),
    message         VARCHAR(512),
    img_path        VARCHAR(256),
    PRIMARY KEY(user, watermark)
);