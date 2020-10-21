package searcher.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import searcher.config.ViewTypes;
import searcher.model.Work;
import searcher.service.TransferService;

@Service
public class UIElementFactory {
	private final TransferService transferService;
    private UILoader uiLoader;
    private final ViewTypes viewTypes;
	
	public UIElementFactory(TransferService transferService, ViewTypes viewTypes) {
		this.transferService = transferService;
		this.viewTypes = viewTypes;
	}

	public Text createTextNode(String textString, Font...fonts) {
		Text text = new Text(textString);
		text.getStyleClass().add("my-text");
		if (fonts.length > 0)
			text.setFont(fonts[0]);
		return text;
	}
	
	public Text newLine() {return new Text(System.lineSeparator());}
	
	
	public Stage createViewHolder(Work work) {
		this.transferService.setCurrentId(work.getMal_id());
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		
		String type = this.viewTypes.contains(work.getType()) ? 
				ViewTypes.MANGA_VIEW : ViewTypes.ANIME_VIEW;
		
		var root = this.uiLoader.load(type);
		stage.setScene(new Scene(root));
		
		return stage;
	}
	
	
	@Autowired
	public void setUiLoader(UILoader uiLoader) {
		this.uiLoader = uiLoader;
	}
}