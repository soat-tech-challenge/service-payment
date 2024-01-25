package br.com.grupo63.techchallenge.payment.gateway.payment;

import br.com.grupo63.techchallenge.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentJpaAdapter implements IPaymentGateway {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public List<Payment> findByDeletedFalse() {
        throw new UnsupportedOperationException("This operation is not supported.");
    }

    @Override
    public Payment saveAndFlush(Payment payment) {
        PaymentPersistenceEntity entity = new PaymentPersistenceEntity(payment);

        entity = paymentJpaRepository.saveAndFlush(entity);

        return entity.toModel();
    }

    @Override
    public Optional<Payment> findByIdAndDeletedFalse(Long id) {
        throw new UnsupportedOperationException("This operation is not supported.");
    }

    @Override
    public Optional<Payment> findByOrderId(Long id) {
        return paymentJpaRepository.findByOrderId(id).map(PaymentPersistenceEntity::toModel);
    }
}
