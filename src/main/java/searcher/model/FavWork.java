package searcher.model;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class FavWork implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final int mal_id;
	private final String title;
	private final String type;
	private final int year;
	
	public FavWork(Work work) {
		this.mal_id = work.getMal_id();
		this.title = work.getTitle();
		this.type = work.getType();
		this.year = work.getDates().getStart().getYear();
	}
}