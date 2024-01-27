package br.com.grupo63.techchallenge.payment.gateway.payment;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MercadoPagoGateway implements IMercadoPagoGateway {

    // Simulates integration with Mercado Pago
    // Would register payment on Mercado Pago side and return the generated QR code
    public String generateQRCode(Long id, Double transactionAmount) {
        String qrCodeData1 = String.format("%020d", id);
        String qrCodeData2 = UUID.randomUUID().toString();
        String qrCodeData3 = transactionAmount.toString();

        return qrCodeData1
                + "BR.GOV.BCB.PIX2572pix-qr.mercadopago.com/instore/o/v2/"
                + qrCodeData2 + "5204" + qrCodeData3
                + "53039865802BR5925Grupo 63 6009SAO PAULO62070503***6304B61D";
    }
}
