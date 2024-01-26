package br.com.grupo63.techchallenge.payment.api.controller;

import br.com.grupo63.techchallenge.common.api.controller.AbstractAPIController;
import br.com.grupo63.techchallenge.common.exception.NotFoundException;
import br.com.grupo63.techchallenge.common.exception.ValidationException;
import br.com.grupo63.techchallenge.payment.api.controller.dto.PaymentStatusResponseDTO;
import br.com.grupo63.techchallenge.payment.api.controller.dto.QRCodeResponseDTO;
import br.com.grupo63.techchallenge.payment.controller.PaymentController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pagamentos", description = "Gerencia o processo de pagamento de um pedido")
@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentAPIController extends AbstractAPIController {

    private final PaymentController controller;

    @Operation(
            tags = "3ª chamada - Fluxo principal - Pagamento",
            summary = "Fake checkout: Confirma pedido",
            description = "Registra um pedido e o associa a um pedido. Retorna o QRCode gerado via Mercado Pago para exibição ao cliente.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/initialize")
    public ResponseEntity<QRCodeResponseDTO> startPayment(
            @Parameter(description = "Id do pedido") @RequestParam Long orderId
    ) throws NotFoundException {
        return ResponseEntity.ok(controller.startPayment(orderId));
    }

    @Operation(
            tags = "4ª chamada - Fluxo principal - Pagamento",
            summary = "Fake checkout: Finalizar pagamento (Utilizado pelo Mercado Pago)",
            description = "Atualiza o status do pagamento e do pedido. Seria utilizado pelo sistema externo Mercado Pago para simular uma integração de Webhook IPN para notificar o sistema que o pagamento foi concluido.")
    @PostMapping("/finalize")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity confirmPaymentFromOrderId(@Parameter(description = "Id do pedido associado ao pagamento")
                                          @RequestParam Long orderId) throws NotFoundException {
        controller.finishPayment(orderId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            tags = "4ª chamada - Fluxo principal - Pagamento",
            summary = "Recuperar status do pagamento",
            description = "Recupera o status atual do pagamento. Seria utilizado na tela de pagamento do cliente para verificar se o pagamento foi realizado.")
    @GetMapping("/status")
    public ResponseEntity<PaymentStatusResponseDTO> getStatusByOrderId(@Parameter(description = "Id do pedido associado ao pagamento")
                                                       @RequestParam Long orderId) throws NotFoundException {
        return ResponseEntity.ok(controller.getPaymentStatus(orderId));
    }

}
