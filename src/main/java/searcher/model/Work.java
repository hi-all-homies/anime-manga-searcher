package searcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class Work {
	private int mal_id;
	private String image_url;
	private String title;
	private String type;
	private int score;
	private String synopsis;
}
