package org.springframework.samples.petclinic.test;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/debug/leak")
public class LeakController {

	// 기존 필드 유지(호출 인터페이스 깨지지 않게). 다만 더 이상 누적 저장에는 쓰지 않음.
	private static final List<byte[]> LEAK = new ArrayList<>();

	// 요청마다 큰 메모리를 "할당"만 하고, 저장(참조 유지)하지 않아서 GC가 회수 가능
	@PostMapping
	public String leak(@RequestParam(defaultValue = "10") int sizeMb) {
		if (sizeMb <= 0) {
			return "sizeMb must be > 0";
		}
		if (sizeMb > 100) {
			return "sizeMb too large (max 100MB)";
		}

		byte[] tmp = new byte[sizeMb * 1024 * 1024];
		int checksum = tmp[0]; // 최적화 방지용

		return "allocated=" + sizeMb + "MB (not retained, checksum=" + checksum + ")";
	}

	// 기존 엔드포인트 유지
	@DeleteMapping
	public String clear() {
		LEAK.clear();
		return "cleared";
	}
}
