package br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts;

import br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos.CardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "accounts", url = "${service.accounts}")
public interface AccountsClient {

    @RequestMapping(method = RequestMethod.GET, path = "/api/cartoes?idProposta={idProposta}", consumes = "application/json")
    CardResponse getByProposalId(@PathVariable("idProposta") Long idProposta);

}
