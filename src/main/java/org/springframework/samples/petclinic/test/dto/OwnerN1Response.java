package org.springframework.samples.petclinic.test.dto;

public record OwnerN1Response(
	Integer ownerId,
	String firstName,
	String lastName,
	int petCount
) {
}
