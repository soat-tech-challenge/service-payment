package br.com.grupo63.techchallenge.payment.gateway.status;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "status", url = "${urls.baseurl-production}")
public interface IStatusGateway {

    @RequestMapping(method = RequestMethod.POST, value = "${urls.status}/advance-status", consumes = "application/json")
    void advanceStatus(@RequestParam("orderId") Long orderId);

}
