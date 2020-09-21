package searcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import searcher.model.Work;

@Service(value = "anime")
public class AnimeService extends WorkService {
	private final String URL = "anime/%d";
	
	@Autowired
	public AnimeService(WebClient webClient) {
		super(webClient);
	}
	
	
	@Override
	public Mono<Work> getResponseItemById(int mal_id) {
		return null;
	}

	
}
