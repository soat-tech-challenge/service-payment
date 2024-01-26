package br.com.grupo63.techchallenge.payment.gateway.payment;

import br.com.grupo63.techchallenge.payment.domain.Payment;

import java.util.Optional;

public interface IPaymentGateway {

    Payment saveAndFlush(Payment payment);

    Optional<Payment> findByOrderId(Long id);

}
