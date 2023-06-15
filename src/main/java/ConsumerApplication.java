import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import java.util.Collections;
import java.util.Properties;

public class ConsumerApplication {
    public static void main(String[] args) {

        // Kafka consumer
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        Consumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
        consumer.subscribe(Collections.singleton("Temperature_Celsius"));

        // Kafka Streams
        Properties streamsProperties = new Properties();
        streamsProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, "temperature-conversion");
        streamsProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamsProperties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        streamsProperties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Stream & topologie
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> inputStream = builder.stream("Temperature_Celsius");
        KStream<String, String> outputStream = inputStream
                .mapValues(celsius -> {
                    float temperatureCelsius = Float.parseFloat(celsius);
                    float temperatureFahrenheit = ((temperatureCelsius * 9) / 5) + 32;
                    return String.valueOf(temperatureFahrenheit);
                });
        outputStream.to("Temperature_Fahrenheit");

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsProperties);
        streams.start();
    }
}