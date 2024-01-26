package br.com.grupo63.techchallenge.payment.presenter;

import br.com.grupo63.techchallenge.payment.api.controller.dto.PaymentStatusResponseDTO;
import br.com.grupo63.techchallenge.payment.api.controller.dto.QRCodeResponseDTO;
import br.com.grupo63.techchallenge.payment.domain.PaymentStatus;

public class PaymentPresenter {

    public static QRCodeResponseDTO toDto(String qrCode) {
        return new QRCodeResponseDTO(qrCode);
    }

    public static PaymentStatusResponseDTO toDto(PaymentStatus status) {
        return new PaymentStatusResponseDTO(status);
    }


}
