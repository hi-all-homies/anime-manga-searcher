package searcher.service;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import searcher.model.ErrorWork;
import searcher.model.Response;
import searcher.model.Work;

public abstract class WorkService {
	protected final WebClient webClient;
	protected final String errorUrl;
	
	private final String URL = "search/%s";
	private final String PARAM = "q";
	
	public WorkService(WebClient webClient) {
		this.webClient = webClient;
		this.errorUrl = getClass().getResource("/img/err.png").toExternalForm();
	}
	
	public abstract Mono<Work> getResponseItemById(final int mal_id);
	
	
	public Mono<List<Work>> findWorkByItsTitle(final String title, final String type){
		return this.webClient.get()
				.uri(builder -> builder.path(String.format(URL, type))
						.queryParam(PARAM, title)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Response.class)
				.transform(this::mapResponse);
	}
	
	private Mono<List<Work>> mapResponse(Mono<Response> response){
		return response.map(resp -> resp.getResults())
				.onErrorReturn(List.of(new ErrorWork(errorUrl)));
	}

	
	private final String TOP_URL = "top/%s/%d";
	
	public Mono<List<Work>> findTopWorks(String type, int page) {
		return this.webClient.get()
				.uri(builder -> builder.path(String.format(TOP_URL, type, page))
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Response.class)
				.map(resp -> resp.getTop())
				.onErrorReturn(List.of(new ErrorWork(errorUrl)));
	}
}