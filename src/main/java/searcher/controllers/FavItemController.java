package searcher.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import searcher.config.ViewTypes;
import searcher.model.FavWork;
import searcher.service.TransferService;
import searcher.util.UILoader;

public class FavItemController {
	@FXML private Text title;
	@FXML private Label type;
	@FXML private Label year;
	@FXML private AnchorPane pane;
	
	private final FavWork favWork;
	private final UILoader uiLoader;
	private final TransferService transferService;
	private final ViewTypes viewTypes;
	
	public FavItemController(FavWork favWork, UILoader uiLoader, TransferService transferService, ViewTypes viewTypes) {
		this.favWork = favWork;
		this.uiLoader = uiLoader;
		this.transferService = transferService;
		this.viewTypes = viewTypes;
	}
	
	public void initialize() {
		this.pane.setOnMouseClicked(this::handleClickEvent);
		this.title.setText(this.favWork.getTitle());
		this.type.setText(this.favWork.getType());
		this.year.setText(String.valueOf(this.favWork.getYear()));
	}
	
	private void handleClickEvent(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			this.transferService.setCurrentId(favWork.getMal_id());
			String type = this.viewTypes.contains(favWork.getType()) ?
					ViewTypes.MANGA_VIEW : ViewTypes.ANIME_VIEW;
			final var root = this.uiLoader.load(type);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.show();
		}
	}
}
