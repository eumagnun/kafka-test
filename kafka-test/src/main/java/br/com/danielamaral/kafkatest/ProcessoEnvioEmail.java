package br.com.danielamaral.kafkatest;

import java.time.Duration;
import java.util.Collections;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ProcessoEnvioEmail implements ConsumidorFila {

	public static void main(String[] args) {
		new ProcessoEnvioEmail().processarNovaMensagem();
	}

	@Override
	public void processarNovaMensagem() {

		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(
				KafkaProperties.getProperties());
		kafkaConsumer.subscribe(Collections.singletonList("RECEPCAO_PEDIDOS"));

		ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
		if (!records.isEmpty()) {

			for (ConsumerRecord<String, String> record : records) {
				imprimir(kafkaConsumer.toString());
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// ignoring
					e.printStackTrace();
				}
			}
		}
	}

}
