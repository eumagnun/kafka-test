package br.com.danielamaral.kafkatest;

public interface ConsumidorFila {

	public void processarNovaMensagem();
	
	
	public default void imprimir(String msg) {
		System.out.println(msg);
	}
}
