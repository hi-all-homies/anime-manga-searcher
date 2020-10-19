package searcher.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import searcher.config.ViewTypes;
import searcher.model.Work;
import searcher.service.TransferService;
import searcher.util.UILoader;
import searcher.util.UIElementFactory;

public class WorkItemController {
	@FXML private Label score;
	@FXML private ImageView img;
    @FXML private TextFlow synopsis;
    @FXML private Label title;
    
    private final Work work;
    private final UIElementFactory itmFactory;
    private final TransferService transferService;
    private final UILoader uiLoader;
    private final ViewTypes viewTypes;
    
    public WorkItemController(
    		Work work, UIElementFactory itmFactory,
    		TransferService transferService,
    		UILoader uiLoader, ViewTypes viewTypes) {
		this.work = work;
		this.itmFactory = itmFactory;
		this.transferService = transferService;
		this.uiLoader = uiLoader;
		this.viewTypes = viewTypes;
	}

	public void initialize() {
		this.title.setText(this.work.getTitle());
		this.score.setText(String.format("score: %d", this.work.getScore()));
		this.img.setImage(new Image(this.work.getImage_url()));
		this.synopsis.getChildren().add(
				this.itmFactory.createTextNode(this.work.getSynopsis()));
		
		this.img.setOnMouseClicked(this::handleClick);
	}

	private void handleClick(MouseEvent mouseEvent) {
		if (mouseEvent.getButton() == MouseButton.PRIMARY) {
			this.transferService.setCurrentId(this.work.getMal_id());
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			
			String type = this.viewTypes.contains(this.work.getType()) ? 
					ViewTypes.MANGA_VIEW : ViewTypes.ANIME_VIEW;
			var root = this.uiLoader.load(type);
			
			stage.setScene(new Scene(root));
			stage.show();
		}
	}
}
