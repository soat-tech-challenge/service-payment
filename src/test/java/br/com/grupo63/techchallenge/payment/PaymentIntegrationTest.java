package br.com.grupo63.techchallenge.payment;

import br.com.grupo63.techchallenge.payment.api.controller.PaymentAPIController;
import br.com.grupo63.techchallenge.payment.api.controller.dto.PaymentStatusResponseDTO;
import br.com.grupo63.techchallenge.payment.api.controller.dto.QRCodeResponseDTO;
import br.com.grupo63.techchallenge.payment.controller.PaymentController;
import br.com.grupo63.techchallenge.payment.domain.Payment;
import br.com.grupo63.techchallenge.payment.domain.PaymentMethod;
import br.com.grupo63.techchallenge.payment.domain.PaymentStatus;
import br.com.grupo63.techchallenge.payment.gateway.order.IOrderGateway;
import br.com.grupo63.techchallenge.payment.gateway.order.dto.OrderDTO;
import br.com.grupo63.techchallenge.payment.gateway.payment.*;
import br.com.grupo63.techchallenge.payment.gateway.status.IStatusGateway;
import br.com.grupo63.techchallenge.payment.usecase.PaymentUseCase;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

class PaymentIntegrationTest {

    @Mock
    private PaymentJpaRepository paymentJpaRepository;
    @Mock
    private IOrderGateway orderGateway;
    @Mock
    private IStatusGateway statusGateway;

    @InjectMocks
    private PaymentJpaAdapter paymentJpaAdapter;
    private PaymentUseCase paymentUseCase;
    private PaymentController paymentController;
    private PaymentAPIController paymentAPIController;
    private MercadoPagoGateway mercadoPagoGateway;
    @Mock
    private SqsTemplate sqsTemplate;



    private final Long defaultOrderId = 1L;
    private final String qrData = UUID.randomUUID().toString();
    private final Double totalPrice = 100.00;
    private final OrderDTO orderDTO = new OrderDTO(totalPrice);
    private final Payment payment = new Payment(1L, false, PaymentStatus.PENDING, PaymentMethod.MERCADO_PAGO_QR_CODE, qrData, defaultOrderId);
    private final PaymentPersistenceEntity defaultPaymentPersistenceEntity =
            new PaymentPersistenceEntity(payment);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mercadoPagoGateway = new MercadoPagoGateway();
        paymentUseCase = new PaymentUseCase(mercadoPagoGateway, orderGateway, paymentJpaAdapter, sqsTemplate);
        paymentController = new PaymentController(paymentUseCase);
        paymentAPIController = new PaymentAPIController(paymentController);
    }

    @SneakyThrows
    @Test
    void testGetPaymentStatus_EndToEnd() {
        when(paymentJpaRepository.findByOrderId(defaultOrderId)).thenReturn(Optional.of(defaultPaymentPersistenceEntity));
        when(orderGateway.getOrderById(defaultOrderId)).thenReturn(Optional.of(orderDTO));

        ResponseEntity<PaymentStatusResponseDTO> response = paymentAPIController.getStatusByOrderId(defaultOrderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getStatus(), PaymentStatus.PENDING);
    }

    @SneakyThrows
    @Test
    void testStartPayment_EndToEnd() {
        when(paymentJpaRepository.save(any())).thenReturn(new PaymentPersistenceEntity(qrData, defaultOrderId, PaymentMethod.MERCADO_PAGO_QR_CODE, PaymentStatus.PENDING));
        when(orderGateway.getOrderById(defaultOrderId)).thenReturn(Optional.of(orderDTO));

        ResponseEntity<QRCodeResponseDTO> response = paymentAPIController.startPayment(defaultOrderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @SneakyThrows
    @Test
    void testFinishPayment_EndToEnd() {
        when(paymentJpaRepository.findByOrderId(defaultOrderId)).thenReturn(Optional.of(defaultPaymentPersistenceEntity));

        payment.setStatus(PaymentStatus.PAID);
        when(paymentJpaRepository.save(any())).thenReturn(defaultPaymentPersistenceEntity);

        ResponseEntity response = paymentAPIController.confirmPaymentFromOrderId(defaultOrderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
