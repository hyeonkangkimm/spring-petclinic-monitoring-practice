package org.springframework.samples.petclinic.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debug")
public class DebugPerfController {

	@GetMapping("/slow")
	public String slow() throws InterruptedException {
		Thread.sleep(2000); // 2초 지연
		return "ok";
	}

}
