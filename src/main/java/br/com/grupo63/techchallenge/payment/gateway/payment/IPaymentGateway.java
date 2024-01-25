package br.com.grupo63.techchallenge.payment.gateway.payment;

import br.com.grupo63.techchallenge.common.gateway.IPersistenceEntityGateway;
import br.com.grupo63.techchallenge.payment.domain.Payment;

import java.util.Optional;

public interface IPaymentGateway extends IPersistenceEntityGateway<Payment> {

    Payment saveAndFlush(Payment payment);

    Optional<Payment> findByOrderId(Long id);

}
