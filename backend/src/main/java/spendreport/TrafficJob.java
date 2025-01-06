package spendreport;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;

public class TrafficJob {
	public static void main(String[] args) throws Exception {
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		DataStream<VehicleData> vehicleDataStream = env
				.socketTextStream("python-socket", 9999)
				.map((MapFunction<String, VehicleData>) value -> {
                    String[] tokens = value.split(",");
                    return new VehicleData(
                            Long.parseLong(tokens[0]),     // vehicle_id
                            Double.parseDouble(tokens[1]), // latitude
                            Double.parseDouble(tokens[2]), // longitude
                            Double.parseDouble(tokens[3])  // speed
                    );
                });

		vehicleDataStream.addSink(JdbcSink.sink(
				"INSERT INTO traffic_data (vehicle_id, latitude, longitude, speed) VALUES (?, ?, ?, ?)",
				(statement, vehicle) -> {
					statement.setLong(1, vehicle.getVehicleId());
					statement.setDouble(2, vehicle.getLatitude());
					statement.setDouble(3, vehicle.getLongitude());
					statement.setDouble(4, vehicle.getSpeed());
				},
				JdbcExecutionOptions.builder()
						.withBatchIntervalMs(200) // Execute batch every 200ms, even if batch size not reached
						.withBatchSize(1000)      // Maximum rows in a batch before execution
						.withMaxRetries(5)        // Retry up to 5 times on failure
						.build(),
				new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
						.withUrl("jdbc:postgresql://db:5432/dev")
						.withDriverName("org.postgresql.Driver")
						.withUsername("postgres")
						.withPassword("postgres")
						.build()

		));

		vehicleDataStream.print();

		env.execute("Traffic Data Ingestion");
	}
}

