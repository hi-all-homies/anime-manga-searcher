package searcher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;
import searcher.service.WorkService;

@SpringBootTest
public class MangaServiceTest {
	@Autowired
	@Qualifier(value = "manga")
	WorkService mangaService;
	
	@Test
	public void shoudRetrieveMangaItem() {
		var mangaMono = this.mangaService.getResponseItemById(1);
		
		StepVerifier.create(mangaMono)
			.consumeNextWith(manga -> System.out.println(
					String.format("title: %s, type: %s, published: %d - %d",
							manga.getTitle(), manga.getType(),
							manga.getDates().getStart().getYear(), manga.getDates().getEnd().getYear())))
			.verifyComplete();
	}
}
