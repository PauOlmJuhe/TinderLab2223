package cat.tecnocampus.tinder.application;

import cat.tecnocampus.tinder.application.dto.quote.QuoteDTO;
import cat.tecnocampus.tinder.application.dto.quote.ValueDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteController {
    private WebClient webClient;

    public QuoteController(WebClient webClient) {
        this.webClient = webClient;
    }

    public ValueDTO getSingleQuote(Long id) {
        var quote = webClient.get()
                .uri(String.join("", "/", id.toString()))
                .retrieve()
                .bodyToMono(QuoteDTO.class)
                .block();
        return quote.getValue();
    }

    public ValueDTO getRandomQuote() {
        var quote = webClient.get()
                .uri(String.join("", "/random"))
                .retrieve()
                .bodyToMono(QuoteDTO.class)
                .block();
        return quote.getValue();
    }

    public List<ValueDTO> getQuotes() {
        List<QuoteDTO> quotes = webClient.get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<QuoteDTO>>() {})
                .block();
        return quotes.stream().map(QuoteDTO::getValue).collect(Collectors.toList());
    }
}
