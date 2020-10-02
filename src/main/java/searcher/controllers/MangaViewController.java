package searcher.controllers;

import java.time.LocalDate;
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
import searcher.model.FavWork;
import searcher.model.Manga;
import searcher.service.FavoritesService;
import searcher.service.TransferService;
import searcher.service.WorkService;
import searcher.util.WorkItemFactory;

@Component
public class MangaViewController {
	@FXML private HBox hbox;
	@FXML private ImageView img;
	@FXML private TextFlow info;
	@FXML private ImageView fav_img;
	@FXML private Button addFav;
	@FXML private TextFlow synopsis;
	@FXML private Label title;
	@FXML private ProgressBar progress;
	
	private final WorkService mangaService;
	private final TransferService  transferService;
	private final FavoritesService favService;
	private final WorkItemFactory itemFactory;
	
	public MangaViewController(
			@Qualifier("manga") WorkService mangaService,
			TransferService transferService,
			FavoritesService favService,
			WorkItemFactory itemFactory) {
		this.mangaService = mangaService;
		this.transferService = transferService;
		this.favService = favService;
		this.itemFactory = itemFactory;
	}


	public void initialize() {
		this.addFav.setOnAction(this::addToFavorites);
		
		Mono.just(this.transferService.getCurrentId())
			.subscribeOn(Schedulers.single())
			.flatMap(mangaService::getResponseItemById)
			.cast(Manga.class)
			.subscribe(addMangaToView);
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
	
	private final Consumer<Manga> addMangaToView =
			manga -> Platform.runLater(() -> {
				this.addFav.setUserData(new FavWork(manga));
				this.img.setImage(new Image(manga.getImage_url()));
				this.title.setText(manga.getTitle());
				Font font = new Font(14d);
				setSynopsis(manga, font);
				setInfoTextFlow(manga, font);
				((AnchorPane) this.progress.getParent()).getChildren().remove(this.progress);
				setFavs(manga);
				this.hbox.setVisible(true);
			});
	
			private void setFavs(Manga manga) {
				if (this.favService.isFavorite(manga.getMal_id())) {
					this.fav_img.setVisible(true);
					this.addFav.setText("out of favs");
				}
				else
					this.addFav.setText("add to favs");
			}
			
			private void setSynopsis(Manga manga, Font font) {
				Text syn = this.itemFactory.createTextNode(manga.getSynopsis(), font);
				Text back = this.itemFactory.createTextNode(String.format("Background: \n%s", manga.getBackground()), font);
				this.synopsis.getChildren().addAll(syn, this.itemFactory.newLine(), back);
			}
			
			private void setInfoTextFlow(Manga manga, Font font) {
				var infoChildren = this.info.getChildren();
				Text volumes = this.itemFactory.createTextNode(
						String.format("volumes: %d", manga.getVolumes()), font);
				Text chapters = this.itemFactory.createTextNode(
						String.format("chapters: %d", manga.getChapters()), font);
				Text startYear = this.itemFactory.createTextNode(
						String.format("start year: %d", manga.getDates().getStart().getYear()), font);
				
				var end = manga.getDates().getEnd();
				String endStr = end == LocalDate.MIN ?
						"end year: doens't finish yet" : String.format("end year: ", end.getYear());
				Text endYear = this.itemFactory.createTextNode(endStr, font);
				
				Text score = this.itemFactory.createTextNode(
						String.format("score: %d", manga.getScore()), font);
				Text type = this.itemFactory.createTextNode(
						String.format("type: %s", manga.getType()), font);
				
				infoChildren.addAll(score, this.itemFactory.newLine(),
						volumes, this.itemFactory.newLine(), chapters, this.itemFactory.newLine(),
						type, this.itemFactory.newLine(),startYear, this.itemFactory.newLine(), endYear);
			}
	
}
