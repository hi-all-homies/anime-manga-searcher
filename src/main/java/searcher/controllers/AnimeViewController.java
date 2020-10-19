package searcher.controllers;

import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
import searcher.model.FavWork;
import searcher.service.FavoritesService;
import searcher.service.TransferService;
import searcher.service.WorkService;
import searcher.util.UIElementFactory;

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
	@FXML private ImageView fav_img;
	@FXML private Button addFav;
	
	private final TransferService transferService;
	private final WorkService workService;
	private UIElementFactory itemFactory;
	private final FavoritesService favService;
	
	public AnimeViewController(TransferService transferService,
			@Qualifier("anime") WorkService workService,
			UIElementFactory itemFactory, FavoritesService favService) {
		this.transferService = transferService;
		this.workService = workService;
		this.itemFactory = itemFactory;
		this.favService = favService;
	}

	public void initialize() {
		this.addFav.setOnAction(this::addToFavorites);
		
		Mono.just(this.transferService.getCurrentId())
			.subscribeOn(Schedulers.single())
			.flatMap(workService::getResponseItemById)
			.cast(Anime.class)
			.subscribe(addAnimeToView);
	}
	
	
	private void addToFavorites(ActionEvent event) {
		var favWork = (FavWork) ((Button) event.getSource()).getUserData();
		if (this.favService.isFavorite(favWork.getMal_id())) {
			this.addFav.setText("add to favs");
			this.fav_img.setVisible(false);
			this.favService.removeFav(favWork);
		}
		else {
			this.fav_img.setVisible(true);
			this.addFav.setText("out of favs");
			this.favService.addFav(favWork);
		}
	}
	
	
	private final Consumer<Anime> addAnimeToView =
			anime -> Platform.runLater(() -> {
				this.addFav.setUserData(new FavWork(anime));
				this.title.setText(anime.getTitle());
				this.img.setImage(new Image(anime.getImage_url()));
				Font font  = new Font(14d);
				setSynopsis(anime, font);
				setInfoTextFlow(anime, font);
				setMusicInfo(anime, font);
				setFavs(anime);
				((AnchorPane)this.progress.getParent()).getChildren().remove(this.progress);
				this.hbox.setVisible(true);
			});
			
			private void setFavs(Anime anime) {
				if (this.favService.isFavorite(anime.getMal_id())) {
					this.fav_img.setVisible(true);
					this.addFav.setText("out of favs");
				}
				else
					this.addFav.setText("add to favs");
			}
			
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
				Text startYear = this.itemFactory.createTextNode(
						String.format("release year: %d", anime.getDates().getStart().getYear()), font);
				
				infoChildren.addAll(score, this.itemFactory.newLine(),
						type, this.itemFactory.newLine(), rate, this.itemFactory.newLine(),
						episodes, this.itemFactory.newLine(), dur, this.itemFactory.newLine(), startYear);
			}
}