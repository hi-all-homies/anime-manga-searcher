package searcher.controllers;

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
	@FXML private TextFlow music;
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
		var id = this.transferService.getCurrentId();
		Mono.just(id)
			.subscribeOn(Schedulers.single())
			.flatMap(workService::getResponseItemById)
			.cast(Anime.class)
			.subscribe(anime -> Platform.runLater(() -> {
				this.title.setText(anime.getTitle());
				this.img.setImage(new Image(anime.getImage_url()));
				this.synopsis.getChildren().add(new Text(anime.getSynopsis()));
				
				var music1 = anime.getOpening_themes().stream();
				var music2 = anime.getEnding_themes().stream();
				Stream.concat(music1, music2).forEach(item -> this.music.getChildren().add(new Text(item)));
			}));
		
	}
}
