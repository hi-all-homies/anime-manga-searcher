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

public class TopWorkController {
	@FXML private TextFlow left_text;
	@FXML private ImageView img;
    @FXML private Label title;
    
    private final Work work;
    private final UIElementFactory itemFactory;
   
	public TopWorkController(Work work, UIElementFactory itemFactory) {
		this.work = work;
		this.itemFactory = itemFactory;
	}

	public void initialize() {
		this.title.setText(this.work.getTitle());
		this.img.setImage(new Image(this.work.getImage_url()));
		this.img.setOnMouseClicked(this::handleClick);
		this.iniitTextFlows();
	}
    
	
	private void handleClick(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			var stage = this.itemFactory.createViewHolder(this.work);
			stage.show();
		}
	}
	
	private void iniitTextFlows() {
		var rank = this.itemFactory.createTextNode(String.format("Rank: %d", this.work.getRank()));
		var type = this.itemFactory.createTextNode(String.format("type: %s", this.work.getType()));
		var score = this.itemFactory.createTextNode(String.format("score: %d", this.work.getScore()));
		var start_date = this.itemFactory.createTextNode(String.format("start date: %s", this.work.getStart_date()));
		var end_date = this.itemFactory.createTextNode(String.format("end date: %s", this.work.getEnd_date()));
		
		this.left_text.getChildren().addAll(
				rank, this.itemFactory.newLine(), type, this.itemFactory.newLine(), score,
				this.itemFactory.newLine(), start_date, this.itemFactory.newLine(), end_date);
	}
}
