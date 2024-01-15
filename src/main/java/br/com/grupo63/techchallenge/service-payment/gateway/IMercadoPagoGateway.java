package br.com.grupo63.techchallenge.payment.gateway;

public interface IMercadoPagoGateway {

    String generateQRCode(Long id, Double transactionAmount);

}
