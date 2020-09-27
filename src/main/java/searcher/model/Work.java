package searcher.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@EqualsAndHashCode(of = "mal_id")
public class Work {
	private final int mal_id;
	private final String image_url;
	private final String title;
	private final String type;
	private final int score;
	private final String synopsis;
	
	@JsonCreator
	public Work(
			@JsonProperty("mal_id") int mal_id,
			@JsonProperty("image_url") String image_url,
			@JsonProperty("title") String title,
			@JsonProperty("type") String type,
			@JsonProperty("score") int score,
			@JsonProperty("synopsis") String synopsis) {
		this.mal_id = mal_id;
		this.image_url = image_url;
		this.title = title;
		this.type = type;
		this.score = score;
		this.synopsis = synopsis;
	}
	
	public StartEndDates getDates() {
		throw new UnsupportedOperationException("this doesn't have any date fields");
	}
}