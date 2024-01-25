package br.com.grupo63.techchallenge.payment.gateway.order;

import br.com.grupo63.techchallenge.payment.gateway.order.dto.OrderDTO;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Optional;

@FeignClient("order")
public interface IOrderGateway {

    @RequestLine("GET /orders/{orderId}")
    Optional<OrderDTO> getOrderById(@Param("orderId") Long orderId);

}
