package br.com.grupo63.techchallenge.payment.controller;

import br.com.grupo63.techchallenge.common.exception.NotFoundException;
import br.com.grupo63.techchallenge.common.exception.ValidationException;
import br.com.grupo63.techchallenge.payment.api.controller.dto.PaymentStatusResponseDTO;
import br.com.grupo63.techchallenge.payment.api.controller.dto.QRCodeResponseDTO;
import br.com.grupo63.techchallenge.payment.presenter.PaymentPresenter;
import br.com.grupo63.techchallenge.payment.usecase.PaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentController {

    private final OrderUseCase orderUseCase;
    private final PaymentUseCase useCase;

    public QRCodeResponseDTO startPayment(Long orderId) throws NotFoundException, ValidationException {
        Order entity = orderUseCase.read(orderId);
        return PaymentPresenter.toDto(useCase.startPayment(entity));
    }

    public void finishPayment(Long orderId) throws NotFoundException, ValidationException {
        Order entity = orderUseCase.read(orderId);
        useCase.finishPayment(entity);
    }

    public PaymentStatusResponseDTO getPaymentStatus(Long orderId) throws NotFoundException, ValidationException {
        Order entity = orderUseCase.read(orderId);
        return PaymentPresenter.toDto(useCase.getPaymentStatus(entity));
    }

}
