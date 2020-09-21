package searcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import searcher.model.Work;

@Service(value = "manga")
public class MangaService extends WorkService {
	private final String URL = "manga/%d";
	
	@Autowired
	public MangaService(WebClient webClient) {
		super(webClient);
	}

	
	@Override
	public Work getResponseItemById(int mal_id) {
		// TODO Auto-generated method stub
		return null;
	}
}
