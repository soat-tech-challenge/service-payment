package br.com.grupo63.techchallenge.payment.controller.dto;

import br.com.grupo63.techchallenge.common.controller.dto.AbstractControllerDTO;
import br.com.grupo63.techchallenge.payment.domain.PaymentMethod;
import br.com.grupo63.techchallenge.payment.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentControllerDTO extends AbstractControllerDTO {

    private PaymentStatus status;

    private PaymentMethod method;

    private String qrData;

}
