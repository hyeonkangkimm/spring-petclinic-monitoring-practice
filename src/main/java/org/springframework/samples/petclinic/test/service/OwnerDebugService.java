package org.springframework.samples.petclinic.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.test.dto.OwnerN1Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerDebugService {

	private final OwnerRepository ownerRepository;

	public OwnerDebugService(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@Transactional(readOnly = true)
	public List<OwnerN1Response> getOwnersN1Fixed() {
		List<OwnerN1Response> result = new ArrayList<>();

		for (Owner owner : ownerRepository.findAllWithPets()) {
			result.add(new OwnerN1Response(
				owner.getId(),
				owner.getFirstName(),
				owner.getLastName(),
				owner.getPets().size()
			));
		}

		return result;
	}
}
