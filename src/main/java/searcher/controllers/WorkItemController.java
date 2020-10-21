package searcher.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import searcher.model.Work;
import searcher.util.UIElementFactory;

public class WorkItemController {
	@FXML private Label score;
	@FXML private ImageView img;
    @FXML private TextFlow synopsis;
    @FXML private Label title;
    
    private final Work work;
    private final UIElementFactory itmFactory;
    
    public WorkItemController(Work work, UIElementFactory itmFactory) {
		this.work = work;
		this.itmFactory = itmFactory;
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
			var stage = this.itmFactory.createViewHolder(this.work);
			stage.show();
		}
	}
}
