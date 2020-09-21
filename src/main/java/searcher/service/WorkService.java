package searcher.service;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import searcher.model.Response;
import searcher.model.Work;

public abstract class WorkService {
	protected final WebClient webClient;
	
	private final String URL = "search/%s";
	private final String PARAM = "q";
	
	public WorkService(WebClient webClient) {
		this.webClient = webClient;
	}
	
	
	public abstract Mono<Work> getResponseItemById(final int mal_id);
	
	
	public Flux<Work> findWorkByItsTitle(final String title, final String type){
		return this.webClient.get()
				.uri(builder -> builder.path(String.format(URL, type))
						.queryParam(PARAM, title)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Response.class)
				.flatMapMany(resp -> Flux.fromIterable(resp.getResults()));
	}
}
