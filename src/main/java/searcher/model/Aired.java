package searcher.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Aired implements StartEndDates{
	private final OffsetDateTime from;
	private final OffsetDateTime to;
	
	@JsonCreator
	public Aired(
			@JsonProperty("from") OffsetDateTime from,
			@JsonProperty("to") OffsetDateTime to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public LocalDate getStart() {
		return this.from.toLocalDate();
	}

	@Override
	public LocalDate getEnd() {
		try {
			return this.to.toLocalDate();
		} catch (NullPointerException e) {
			return LocalDate.MIN;
		}
	}
}
