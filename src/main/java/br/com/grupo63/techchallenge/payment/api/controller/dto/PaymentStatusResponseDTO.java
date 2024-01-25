package br.com.grupo63.techchallenge.payment.api.controller.dto;

import br.com.grupo63.techchallenge.payment.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusResponseDTO {

    private PaymentStatus status;

}
