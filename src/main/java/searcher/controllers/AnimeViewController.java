package searcher.controllers;

import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import searcher.model.Anime;
import searcher.service.TransferService;
import searcher.service.WorkService;
import searcher.util.WorkItemFactory;

@Component
public class AnimeViewController {
	@FXML private ImageView img;
	@FXML private TextFlow info;
    @FXML private TextFlow synopsis;
    @FXML private Label title;
	@FXML private HBox hbox;
	@FXML private TextFlow op;
	@FXML private TextFlow end;
	@FXML private ProgressBar progress;
	
	private final TransferService transferService;
	private final WorkService workService;
	private WorkItemFactory itemFactory;
	
	public AnimeViewController(TransferService transferService,
			@Qualifier("anime") WorkService workService,
			WorkItemFactory itemFactory) {
		this.transferService = transferService;
		this.workService = workService;
		this.itemFactory = itemFactory;
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
				Font font  = new Font(14d);
				setSynopsis(anime, font);
				setInfoTextFlow(anime, font);
				setMusicInfo(anime, font);
				((AnchorPane)this.progress.getParent()).getChildren().remove(this.progress);
				this.hbox.setVisible(true);
			});
			
			private void setSynopsis(Anime anime, Font font) {
				Text syn = this.itemFactory.createTextNode(anime.getSynopsis(), font);
				this.synopsis.getChildren().add(syn);
			}
			
			private void setMusicInfo(Anime anime, Font font) {
				anime.getOpening_themes()
					.forEach(song -> this.op.getChildren()
							.addAll(this.itemFactory.createTextNode(song, font), this.itemFactory.newLine()));
				
				anime.getEnding_themes()
				.forEach(song -> this.end.getChildren()
						.addAll(this.itemFactory.createTextNode(song, font), this.itemFactory.newLine()));
			}

			private void setInfoTextFlow(Anime anime, Font font) {
				var infoChildren = this.info.getChildren();
				Text episodes = this.itemFactory.createTextNode(
						String.format("episodes: %d", anime.getEpisodes()), font);
				Text dur = this.itemFactory.createTextNode(
						String.format("duration: %s", anime.getDuration()), font);
				Text rate = this.itemFactory.createTextNode(
						String.format("rating: %s", anime.getRating()), font);
				Text score = this.itemFactory.createTextNode(
						String.format("score: %d", anime.getScore()), font);
				Text type = this.itemFactory.createTextNode(
						String.format("type: %s", anime.getType()), font);
				
				infoChildren.addAll(score, this.itemFactory.newLine(),
						type, this.itemFactory.newLine(), rate, this.itemFactory.newLine(),
						episodes, this.itemFactory.newLine(), dur);
			}
}