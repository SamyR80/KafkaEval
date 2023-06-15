import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

public class ProducerApplication {
    public static void main(String[] args) {
        //  Kafka Producer
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        int numTemperatures = 90;
        float minTemperature = -10;
        float maxTemperature = 50;

        // Kafka Producer
        Producer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 0; i < numTemperatures; i++) {
            float temperature = (float) (minTemperature + Math.random() * (maxTemperature - minTemperature));
            String recordValue = String.valueOf(temperature);
            ProducerRecord<String, String> record = new ProducerRecord<>("Temperature_Celsius", null, recordValue);
            producer.send(record);
            System.out.println("Temperature envoyé: " + temperature);
        }
        producer.flush();
        producer.close();
    }
}