package searcher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import searcher.model.Work;
import searcher.service.WorkService;

@SpringBootTest
public class WorkServiceTest {

	@Autowired @Qualifier("anime")
	WorkService workService;
	
	@Test
	void shoudFetchSomeNumberOfItems() {
		Flux<Work> results = workService.findWorkByItsTitle("attack on titan", "anime");
		
		StepVerifier.create(results, 5l)
			.thenRequest(5l)
			.consumeNextWith(w -> System.out.println(w.getTitle()))
			.consumeNextWith(w -> System.out.println(w.getTitle()))
			.consumeNextWith(w -> System.out.println(w.getTitle()))
			.consumeNextWith(w -> System.out.println(w.getTitle()))
			.consumeNextWith(w -> System.out.println(w.getTitle()))
			.thenCancel()
			.verify();
	}
}
