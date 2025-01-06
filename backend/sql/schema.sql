CREATE TABLE IF NOT EXISTS traffic_data (
      vehicle_id BIGINT,
      latitude DOUBLE PRECISION,
      longitude DOUBLE PRECISION,
      speed DOUBLE PRECISION,
      timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      location GEOGRAPHY(POINT, 4326) GENERATED ALWAYS AS (
          ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)
          ) STORED
);

ALTER ROLE postgres WITH PASSWORD 'postgres';