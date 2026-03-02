package org.springframework.samples.petclinic.test;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PoolTestController {

	private final EntityManager em;

	@GetMapping("/test/pool-hold")
	@Transactional
	public String hold(@RequestParam(defaultValue = "5000") long ms) throws Exception {
		em.createNativeQuery("SELECT 1").getSingleResult(); // 커넥션 확보
		Thread.sleep(ms); // 트랜잭션 유지
		return "held " + ms;
	}
}
