package br.com.grupo63.techchallenge.payment.gateway.production;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "production", url = "/")
public interface IProductionGateway {

    @RequestMapping(method = RequestMethod.POST, value = "/public/production/advance-status?orderId={orderId}", consumes = "application/json")
    void advanceStatus(@Param("orderId") Long orderId);

}
