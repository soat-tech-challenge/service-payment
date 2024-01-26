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
import br.com.grupo63.techchallenge.payment.usecase.PaymentUseCase;
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

    @InjectMocks
    private PaymentJpaAdapter paymentJpaAdapter;
    @Mock
    private PaymentUseCase paymentUseCase;
    @Mock
    private IOrderGateway orderGateway;
    @Mock
    private IMercadoPagoGateway mercadoPagoGateway;
    @Mock
    private IPaymentGateway paymentGateway;
    private PaymentController paymentController;
    private PaymentAPIController paymentAPIController;

    private final Long defaultOrderId = 1L;
    private final String qrData = UUID.randomUUID().toString();
    private final Double totalPrice = 100.00;
    private final OrderDTO orderDTO = new OrderDTO(defaultOrderId, totalPrice);
    private final Payment payment = new Payment(1L, false, PaymentStatus.PENDING, PaymentMethod.MERCADO_PAGO_QR_CODE, qrData, defaultOrderId);
    private final PaymentPersistenceEntity defaultPaymentPersistenceEntity =
            new PaymentPersistenceEntity(payment);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentUseCase = new PaymentUseCase(mercadoPagoGateway, orderGateway, paymentGateway);
        paymentController = new PaymentController(paymentUseCase);
        paymentAPIController = new PaymentAPIController(paymentController);
    }

    @SneakyThrows
    @Test
    public void testGetPaymentStatus_EndToEnd() {
        when(paymentGateway.findByOrderId(defaultOrderId)).thenReturn(Optional.of(payment));
        when(orderGateway.getOrderById(defaultOrderId)).thenReturn(Optional.of(orderDTO));

        ResponseEntity<PaymentStatusResponseDTO> response = paymentAPIController.getStatusByOrderId(defaultOrderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getStatus(), PaymentStatus.PENDING);
    }

    @SneakyThrows
    @Test
    public void testStartPayment_EndToEnd() {
        when(paymentJpaRepository.saveAndFlush(any())).thenReturn(defaultPaymentPersistenceEntity);
        when(orderGateway.getOrderById(defaultOrderId)).thenReturn(Optional.of(orderDTO));

        ResponseEntity<QRCodeResponseDTO> response = paymentAPIController.startPayment(defaultOrderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @SneakyThrows
    @Test
    public void testfinishPayment_EndToEnd() {
        when(paymentGateway.findByOrderId(defaultOrderId)).thenReturn(Optional.of(payment));

        ResponseEntity response = paymentAPIController.confirmPaymentFromOrderId(defaultOrderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

//    @SneakyThrows
//    @Test
//    public void testUpdate_EndToEnd() {
//        when(clientJpaRepository.findByIdAndDeletedFalse(defaultClientPersistenceEntity.getId())).thenReturn(Optional.of(defaultClientPersistenceEntity));
//
//        ClientControllerDTO clientControllerDTO = new ClientControllerDTO();
//
//        clientControllerDTO.setId(defaultClientPersistenceEntity.getId());
//        clientControllerDTO.setNationalId("11111111111");
//
//        when(clientJpaRepository.save(any())).thenReturn(new ClientPersistenceEntity(defaultClientPersistenceEntity.getId(), defaultClientPersistenceEntity.isDeleted(), clientControllerDTO.getNationalId()));
//
//        ResponseEntity<ClientControllerDTO> response = clientAPIController.update(clientControllerDTO, defaultClientPersistenceEntity.getId());
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(response.getBody().getNationalId(), clientControllerDTO.getNationalId());
//        verify(clientJpaRepository, times(1)).findByIdAndDeletedFalse(any());
//        verify(clientJpaRepository, times(1)).save(any());
//    }
}
