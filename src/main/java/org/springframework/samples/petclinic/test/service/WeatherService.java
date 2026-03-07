package org.springframework.samples.petclinic.test.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
public class WeatherService {

	private final WebClient webClient;

	public WeatherService(WebClient webClient) {
		this.webClient = webClient;
	}

	@Value("${owm.api-key}")
	private String apiKey;

	// 보호 없이 호출(지연 전파 확인용)
	public String weatherRaw() {
		return webClient.get()
			.uri(uriBuilder -> uriBuilder.scheme("https")
				.host("api.openweathermap.org")
				.path("/data/2.5/weather")
				.queryParam("q", "Seoul")
				.queryParam("appid", apiKey)
				.build())
			.retrieve()
			.bodyToMono(String.class)
			.block();
	}

	// 보호 버전(Timeout + CircuitBreaker)
	@CircuitBreaker(name = "weatherApi", fallbackMethod = "fallback")
	@TimeLimiter(name = "weatherApi")
	public CompletableFuture<String> weatherProtected() {
		return webClient.get()
			.uri(uriBuilder -> uriBuilder.scheme("https")
				.host("api.openweathermap.org")
				.path("/data/2.5/weather")
				.queryParam("q", "Seoul")
				.queryParam("appid", apiKey)
				.build())
			.retrieve()
			.bodyToMono(String.class)
			.toFuture();
	}

	private CompletableFuture<String> fallback(Throwable t) {
		return CompletableFuture.completedFuture("fallback: weather degraded (" + t.getClass().getSimpleName() + ")");
	}

	@PostConstruct
	public void checkKey() {
		System.out.println("OWM key loaded = " +
			(apiKey == null ? "null" : (apiKey.isBlank() ? "blank" : apiKey.substring(0, 4) + "****")));
	}

}
