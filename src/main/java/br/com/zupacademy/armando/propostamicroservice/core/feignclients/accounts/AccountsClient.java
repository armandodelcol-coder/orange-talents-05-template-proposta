package br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts;

import br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos.CardBlockadeRequest;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos.CardBlockadeResponse;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos.CardResponse;
import br.com.zupacademy.armando.propostamicroservice.core.feignclients.accounts.dtos.CardTravelNotifyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "accounts", url = "${service.accounts}")
public interface AccountsClient {

    @RequestMapping(method = RequestMethod.GET, path = "/api/cartoes?idProposta={idProposta}", consumes = "application/json")
    CardResponse getByProposalId(@PathVariable("idProposta") Long idProposta);

    @RequestMapping(method = RequestMethod.POST, path = "/api/cartoes/{id}/bloqueios", consumes = "application/json")
    CardBlockadeResponse blockingCard(@PathVariable("id") String id, @RequestBody CardBlockadeRequest cardBlockadeRequest);

    @RequestMapping(method = RequestMethod.POST, path = "/api/cartoes/{id}/avisos", consumes = "application/json")
    ResponseEntity<?> travelNotify(@PathVariable("id") String id, @RequestBody CardTravelNotifyRequest cardTravelNotifyRequest);

}
