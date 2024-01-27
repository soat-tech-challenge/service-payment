package br.com.grupo63.techchallenge.payment.usecase;

import br.com.grupo63.techchallenge.common.exception.NotFoundException;
import br.com.grupo63.techchallenge.payment.domain.PaymentStatus;

public interface IPaymentUseCase {

    String startPayment(Long orderId) throws NotFoundException;

    void finishPayment(Long orderId) throws NotFoundException;

    PaymentStatus getPaymentStatus(Long orderId) throws NotFoundException;

}
