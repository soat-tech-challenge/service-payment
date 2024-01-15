package br.com.grupo63.techchallenge.payment.adapter;

import br.com.grupo63.techchallenge.payment.controller.dto.PaymentControllerDTO;
import br.com.grupo63.techchallenge.payment.domain.Payment;

public class PaymentAdapter {

    public static void fillEntity(PaymentControllerDTO dto, Payment entity) {
        entity.setStatus(dto.getStatus());
        entity.setMethod(dto.getMethod());
        entity.setQrData(dto.getQrData());
    }

}
