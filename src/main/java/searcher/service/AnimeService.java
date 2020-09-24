package searcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import searcher.model.Anime;
import searcher.model.Work;

@Service(value = "anime")
public class AnimeService extends WorkService {
	private final String URL = "anime/{mal_id}";
	
	@Autowired
	public AnimeService(WebClient webClient) {
		super(webClient);
	}
	
	
	@Override
	public Mono<Work> getResponseItemById(int mal_id) {
		return this.webClient.get()
				.uri(builder -> builder.path(URL).build(mal_id))
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Anime.class)
				.cast(Work.class)
				.doOnError(err -> System.out.println(err.getMessage()));
	}

	
}
