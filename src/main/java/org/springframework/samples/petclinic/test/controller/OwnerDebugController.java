package org.springframework.samples.petclinic.test.controller;

import java.util.List;

import org.springframework.samples.petclinic.test.dto.OwnerN1Response;
import org.springframework.samples.petclinic.test.service.OwnerDebugService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OwnerDebugController {

	private final OwnerDebugService ownerDebugService;

	public OwnerDebugController(OwnerDebugService ownerDebugService) {
		this.ownerDebugService = ownerDebugService;
	}

	@GetMapping("/debug/owners-n1")
	public List<OwnerN1Response> ownersN1() {
		return ownerDebugService.getOwnersN1Fixed();
	}
}
