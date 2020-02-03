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
public class CriadorPedidos {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String value = "{PedidoNumero: 1123,item:havaina, valor:1050,00}";

		new CriadorPedidos().postarMensagem(value);
	}

	private void postarMensagem(String msg) throws InterruptedException, ExecutionException {
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(KafkaProperties.getProperties());

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

}
