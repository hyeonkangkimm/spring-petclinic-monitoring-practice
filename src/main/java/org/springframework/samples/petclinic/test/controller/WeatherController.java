package org.springframework.samples.petclinic.test.controller;

import org.springframework.samples.petclinic.test.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/debug/weather")
public class WeatherController {

	private final WeatherService weatherService;

	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@GetMapping("/raw")
	public String raw() {
		return weatherService.weatherRaw();
	}

	@GetMapping("/protected")
	public CompletableFuture<String> protectedCall() {
		return weatherService.weatherProtected();
	}

	// “외부가 느려진 상황”을 통제해서 재현(요청 지연 주입)
	@GetMapping("/raw-delay")
	public String rawDelay(@RequestParam(defaultValue = "2000") long delayMs) throws InterruptedException {
		Thread.sleep(delayMs);
		return weatherService.weatherRaw();
	}

	@GetMapping("/protected-delay")
	public CompletableFuture<String> protectedDelay(@RequestParam(defaultValue = "2000") long delayMs)
			throws InterruptedException {
		Thread.sleep(delayMs);
		return weatherService.weatherProtected();
	}

}
