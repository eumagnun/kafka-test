package br.com.danielamaral.kafkatest;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String value = "{PedidoNumero: 1,item:sapato, valor:1050,00}";

		new App().postarMensagem(value);
	}

	private void postarMensagem(String msg) throws InterruptedException, ExecutionException {
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties());

		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("RECEPCAO_PEDIDOS", msg,
				msg);

		kafkaProducer.send(producerRecord, (data, ex) -> {
			if (ex != null) {
				ex.printStackTrace();
				kafkaProducer.close();
				return;
			}
			System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset "
					+ data.offset() + "/ timestamp " + data.timestamp());
			kafkaProducer.close();
		}).get();
	}

	private void processarMensagemEnvioEmail() {

	}

	private void processarMensagemAuditoria() {

	}

	private void processarFatura() {

	}

	private static Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return properties;
	}
}
