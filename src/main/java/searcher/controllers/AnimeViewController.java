package searcher.controllers;

import java.util.function.Consumer;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import searcher.model.Anime;
import searcher.service.TransferService;
import searcher.service.WorkService;

@Component
public class AnimeViewController {
	@FXML private ImageView img;
	@FXML private TextFlow info;
    @FXML private TextFlow synopsis;
    @FXML private Label title;

	
	private final TransferService transferService;
	private final WorkService workService;
	
	
	public AnimeViewController(TransferService transferService,
			@Qualifier("anime") WorkService workService) {
		this.transferService = transferService;
		this.workService = workService;
	}

	public void initialize() {
		Mono.just(this.transferService.getCurrentId())
			.subscribeOn(Schedulers.single())
			.flatMap(workService::getResponseItemById)
			.cast(Anime.class)
			.subscribe(addAnimeToView);
	}
	
	private final Consumer<Anime> addAnimeToView =
			anime -> Platform.runLater(() -> {
				this.title.setText(anime.getTitle());
				this.img.setImage(new Image(anime.getImage_url()));
				
				Text syn = new Text(anime.getSynopsis());
				var synopsisChilderen = this.synopsis.getChildren();
				synopsisChilderen.addAll(syn, newLine());
				
				var op = anime.getOpening_themes().stream();
				var end = anime.getEnding_themes().stream();
				Stream.concat(op, end).forEach(song -> {
					synopsisChilderen.add(new Text(song));
					synopsisChilderen.add(newLine());
				});
				
				var infoChildren = this.info.getChildren();
				Text episodeCount = new Text(String.format("episodes: %d", anime.getEpisodes()));
				Text dur = new Text(String.format("duration: %s", anime.getDuration()));
				Text rate = new Text(String.format("rating: %s", anime.getRating()));
				infoChildren.addAll(rate, newLine(), dur, newLine(), episodeCount);
			});
			
			private Text newLine() {
				return new Text(System.lineSeparator());
			}
}
