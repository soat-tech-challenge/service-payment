package br.com.grupo63.techchallenge.payment.gateway.payment;

import br.com.grupo63.techchallenge.common.gateway.repository.entity.PersistenceEntity;
import br.com.grupo63.techchallenge.payment.domain.Payment;
import br.com.grupo63.techchallenge.payment.domain.PaymentMethod;
import br.com.grupo63.techchallenge.payment.domain.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Table(name = "pay_payment", indexes = {})
public class PaymentPersistenceEntity extends PersistenceEntity {

    @Basic
    @Column(name = "qr_data", nullable = false)
    private String qrData;

    @Basic
    @Column(name = "pay_order", nullable = false)
    private Long orderId;


    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;


    public PaymentPersistenceEntity(Payment payment) {
        super(payment);
        this.qrData = payment.getQrData();
        this.orderId = payment.getOrderId();
        this.method = payment.getMethod();
        this.status = payment.getStatus();
    }

    public Payment toModel() {
        return new Payment(this.getId(),
                this.isDeleted(),
                this.getStatus(),
                this.getMethod(),
                this.getQrData(),
                this.getOrderId());
    }
}
