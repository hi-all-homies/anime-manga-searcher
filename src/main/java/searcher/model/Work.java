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
	private final int rank;
	private final String start_date;
	private final String end_date;
	
	@JsonCreator
	public Work(
			@JsonProperty("mal_id") int mal_id,
			@JsonProperty("image_url") String image_url,
			@JsonProperty("title") String title,
			@JsonProperty("type") String type,
			@JsonProperty("score") int score,
			@JsonProperty("synopsis") String synopsis,
			@JsonProperty("rank") int rank,
			@JsonProperty("start_date") String start_date,
			@JsonProperty("end_date") String end_date) {
		this.mal_id = mal_id;
		this.image_url = image_url;
		this.title = title;
		this.type = type;
		this.score = score;
		this.synopsis = synopsis;
		this.rank = rank;
		this.start_date = start_date;
		this.end_date = end_date;
	}
	
	public StartEndDates getDates() {
		throw new UnsupportedOperationException("this doesn't have any date fields");
	}
}