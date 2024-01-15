package br.com.grupo63.techchallenge.payment.usecase;

import br.com.grupo63.techchallenge.common.exception.NotFoundException;
import br.com.grupo63.techchallenge.common.exception.ValidationException;
import br.com.grupo63.techchallenge.payment.domain.PaymentStatus;

public interface IPaymentUseCase {

    String startPayment(Long orderId) throws NotFoundException, ValidationException;

    void finishPayment(Long orderId) throws NotFoundException, ValidationException;

    PaymentStatus getPaymentStatus(Long orderId) throws NotFoundException, ValidationException;

}
