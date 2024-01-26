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

    private final PaymentUseCase useCase;

    public QRCodeResponseDTO startPayment(Long orderId) throws NotFoundException {
        return PaymentPresenter.toDto(useCase.startPayment(orderId));
    }

    public void finishPayment(Long orderId) throws NotFoundException {
        useCase.finishPayment(orderId);
    }

    public PaymentStatusResponseDTO getPaymentStatus(Long orderId) throws NotFoundException {
        return PaymentPresenter.toDto(useCase.getPaymentStatus(orderId));
    }

}
