package br.com.zupacademy.armando.propostamicroservice.travels.dtos.request;

import br.com.zupacademy.armando.propostamicroservice.cards.entities.Card;
import br.com.zupacademy.armando.propostamicroservice.core.validations.PresentOrFutureLocalDateTime;
import br.com.zupacademy.armando.propostamicroservice.travels.entities.Travel;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NewTravelRequest {

    private final String PATTERN_LOCALDATETIME = "dd/MM/yyyy HH:mm:ss";

    @JsonProperty
    @NotBlank
    public String destiny;

    @JsonProperty
    @NotBlank @PresentOrFutureLocalDateTime(pattern = PATTERN_LOCALDATETIME)
    public String endDate;

    public Travel toModel(String ip, String userAgent, Card card) {
        LocalDateTime endDate = LocalDateTime.parse(this.endDate, DateTimeFormatter.ofPattern(PATTERN_LOCALDATETIME));
        return new Travel(this.destiny, endDate, ip, userAgent, card);
    }
}
