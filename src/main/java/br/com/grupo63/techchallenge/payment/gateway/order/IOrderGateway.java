package br.com.grupo63.techchallenge.payment.gateway.order;

import br.com.grupo63.techchallenge.payment.gateway.order.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(name = "orders", url = "${app.api-url.order}")
public interface IOrderGateway {

    @RequestMapping(method = RequestMethod.GET, value = "/orders/{orderId}")
    Optional<OrderDTO> getOrderById(@PathVariable("orderId") Long orderId);

}
