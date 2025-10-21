package kafkaPractice;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;
import java.nio.file.Paths;
import java.nio.file.Files;

public class KafkaJsonProducer {
    public static void main(String[] args) throws Exception {

        // 1️ Kafka config
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // 2️ Read JSON file
        String jsonFilePath = "C:\\Users\\User\\Downloads\\Nagireddy\\Selenium\\BackendTestingProject\\src\\test\\resources\\kafkasample.json"; // provide correct path
        String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

        // 3️ Send JSON as message
        ProducerRecord<String, String> record = new ProducerRecord<>("test-topic", "key1", jsonContent);
        producer.send(record);
        System.out.println("JSON sent to Kafka: " + jsonContent);

        producer.close();
    }
}
