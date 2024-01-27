package br.com.grupo63.techchallenge.payment.usecase;

import br.com.grupo63.techchallenge.common.exception.NotFoundException;
import br.com.grupo63.techchallenge.payment.domain.Payment;
import br.com.grupo63.techchallenge.payment.domain.PaymentMethod;
import br.com.grupo63.techchallenge.payment.domain.PaymentStatus;
import br.com.grupo63.techchallenge.payment.gateway.order.IOrderGateway;
import br.com.grupo63.techchallenge.payment.gateway.order.dto.OrderDTO;
import br.com.grupo63.techchallenge.payment.gateway.payment.IMercadoPagoGateway;
import br.com.grupo63.techchallenge.payment.gateway.payment.IPaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentUseCase implements IPaymentUseCase {

    private final IMercadoPagoGateway mercadoPagoService;
    private final IOrderGateway orderGateway;
    private final IPaymentGateway paymentGateway;

    @Override
    public String startPayment(Long orderId) throws NotFoundException {
        OrderDTO orderDTO = orderGateway.getOrderById(orderId).orElseThrow(NotFoundException::new);

        String qrData = mercadoPagoService.generateQRCode(orderDTO.getId(), orderDTO.getTotalPrice());

        Payment payment = new Payment(null, false, PaymentStatus.PENDING, PaymentMethod.MERCADO_PAGO_QR_CODE, qrData, orderDTO.getId());

        paymentGateway.saveAndFlush(payment);

        return qrData;
    }

    @Override
    public void finishPayment(Long orderId) throws NotFoundException {
        Payment payment = paymentGateway.findByOrderId(orderId).orElseThrow(NotFoundException::new);

        payment.setStatus(PaymentStatus.PAID);
        paymentGateway.saveAndFlush(payment);

    }

    @Override
    public PaymentStatus getPaymentStatus(Long orderId) throws NotFoundException {
        Payment payment = paymentGateway.findByOrderId(orderId).orElseThrow(NotFoundException::new);

        return payment.getStatus();
    }
}