package plugin;

import app.App;

/**
 * 插件包主类
 * 
 * @author pjia
 *
 */
public class Main {

	/**
	 * 调试入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		run(args);
	}
	
	/**
	 * 主方法, 由插件加载器调用
	 * 
	 * @param args
	 */
	public static void run(String[] args) {
		App.launch(App.class, args);
	}
}
