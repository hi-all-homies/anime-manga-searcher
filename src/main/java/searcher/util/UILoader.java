package searcher.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class UILoader {
    private final ApplicationContext context;

    public UILoader(ApplicationContext context) {
        this.context = context;
    }

    public Parent load(String sourceName) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource(sourceName));
            loader.setControllerFactory(context::getBean);
            return loader.load();
        } catch (IOException e) {
        	e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
