package br.com.grupo63.techchallenge.payment.domain;

import br.com.grupo63.techchallenge.common.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Payment extends Entity {


    private PaymentStatus status;
    private PaymentMethod method;
    private String qrData;
    private Long orderId;

    public Payment(Long id, boolean deleted, PaymentStatus status, PaymentMethod method, String qrData, Long orderId) {
        super(id, deleted);
        this.status = status;
        this.method = method;
        this.qrData = qrData;
        this.orderId = orderId;
    }
}
