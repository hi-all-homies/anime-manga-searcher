package searcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import searcher.model.ErrorWork;
import searcher.model.Manga;
import searcher.model.Work;

@Service(value = "manga")
public class MangaService extends WorkService {
	private final String URL = "manga/{mal_id}";
	
	@Autowired
	public MangaService(WebClient webClient) {
		super(webClient);
	}

	
	@Override
	public Mono<Work> getResponseItemById(final int mal_id) {
		return this.webClient.get()
				.uri(builder -> builder.path(URL).build(mal_id))
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Manga.class)
				.cast(Work.class)
				.onErrorReturn(new ErrorWork(super.errorUrl));
	}
}