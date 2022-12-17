package app;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {

	private static Logger logger = AppLogger.getLogger(App.class);

	private static final String APP_TITLE = "RVS插件应用模板";

    @Override
    public void start(Stage stage) throws Exception {
		logger.info("启动应用");
		logger.log(Level.CONFIG, "RVS Server IP : " + System.getenv("MKSSI_HOST"));
		logger.log(Level.CONFIG, "RVS Server Port : " + System.getenv("MKSSI_PORT"));
		logger.log(Level.CONFIG, "Current User : " + System.getenv("MKSSI_USER"));
        stage.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));
		stage.setTitle(APP_TITLE + "  (" + System.getenv("MKSSI_HOST") + ")");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
		AnchorPane root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("App.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		// 启动后最大化
		stage.setMaximized(true);
		
    }
    
	// 应用控制器
	public static class Controller implements Initializable {

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// 1. 数据绑定
			
			// 2. 注册监听事件
			
			// 3. 启动初始化线程
		}
	}
}
