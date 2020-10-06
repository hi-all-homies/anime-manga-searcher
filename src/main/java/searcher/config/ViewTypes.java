package searcher.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ViewTypes {
	private final Set<String> types = new HashSet<>();
	public static final String ANIME_VIEW = "/views/Anime.fxml";
	public static final String MANGA_VIEW = "/views/Manga.fxml";
	
	public ViewTypes() {
		types.addAll(List.of("Manga", "Novel", "One-shot", "Manhwa", "Doujinshi"));
	}
	
	public boolean contains(String type) {
		return this.types.contains(type);
	}
}