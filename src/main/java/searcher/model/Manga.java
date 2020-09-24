package searcher.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Manga extends Work{
	
	@JsonCreator
	public Manga(
			@JsonProperty("mal_id") int mal_id,
			@JsonProperty("image_url") String image_url,
			@JsonProperty("title") String title,
			@JsonProperty("type") String type,
			@JsonProperty("score") int score,
			@JsonProperty("synopsis") String synopsis) {
		super(mal_id, image_url, title, type, score, synopsis);
	}

}
