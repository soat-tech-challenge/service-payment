package br.com.grupo63.techchallenge.payment.gateway.payment;

import br.com.grupo63.techchallenge.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentJpaAdapter implements IPaymentGateway {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment saveAndFlush(Payment payment) {
        PaymentPersistenceEntity entity = new PaymentPersistenceEntity(payment);

        entity = paymentJpaRepository.saveAndFlush(entity);

        return entity.toModel();
    }

    @Override
    public Optional<Payment> findByOrderId(Long id) {
        return paymentJpaRepository.findByOrderId(id).map(PaymentPersistenceEntity::toModel);
    }
}
