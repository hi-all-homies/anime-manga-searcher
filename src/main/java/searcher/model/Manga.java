package searcher.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Manga extends Work{
	private final int volumes;
	private final int chapters;
	private final String background;
	private final Published published;
	
	@JsonCreator
	public Manga(
			@JsonProperty("mal_id") int mal_id,
			@JsonProperty("image_url") String image_url,
			@JsonProperty("title") String title,
			@JsonProperty("type") String type,
			@JsonProperty("score") int score,
			@JsonProperty("synopsis") String synopsis,
			@JsonProperty("volumes") int volumes,
			@JsonProperty("chapters") int chapters,
			@JsonProperty("background") String background,
			@JsonProperty("published") Published published) {
		super(mal_id, image_url, title, type, score, synopsis, 0, "", "");
		
		this.volumes = volumes;
		this.chapters = chapters;
		this.background = background;
		this.published = published;
	}

	@Override
	public StartEndDates getDates() {
		return this.published;
	}
}
