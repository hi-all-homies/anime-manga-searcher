package searcher.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Anime extends Work{
	private final int episodes;
	private final String duration;
	private final String rating;
	private final List<String> opening_themes;
	private final List<String> ending_themes;
	private final Aired aired;
	
	@JsonCreator
	public Anime(
			@JsonProperty("mal_id") int mal_id,
			@JsonProperty("image_url") String image_url,
			@JsonProperty("title") String title,
			@JsonProperty("type") String type,
			@JsonProperty("score") int score,
			@JsonProperty("synopsis") String synopsis,
			@JsonProperty("episodes") int episodes,
			@JsonProperty("duration") String duration,
			@JsonProperty("rating") String rating,
			@JsonProperty("opening_themes") List<String> opening_themes,
			@JsonProperty("ending_themes") List<String> ending_themes,
			@JsonProperty("aired") Aired aired) {
		
		super(mal_id, image_url, title, type, score, synopsis);
		this.episodes = episodes;
		this.duration = duration;
		this.rating = rating;
		this.opening_themes = opening_themes;
		this.ending_themes = ending_themes;
		this.aired = aired;
	}

	
	@Override
	public StartEndDates getDates() {
		return this.aired;
	}
}
