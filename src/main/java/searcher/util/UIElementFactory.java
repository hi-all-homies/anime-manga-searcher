package searcher.util;


import org.springframework.stereotype.Service;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

@Service
public class UIElementFactory {
	
	public UIElementFactory() {}
	
	public Text createTextNode(String textString, Font...fonts) {
		Text text = new Text(textString);
		text.getStyleClass().add("my-text");
		if (fonts.length > 0)
			text.setFont(fonts[0]);
		return text;
	}
	
	public Text newLine() {return new Text(System.lineSeparator());}
}