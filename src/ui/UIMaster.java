package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UIMaster {
	public static List<UIElement> ui = new ArrayList<UIElement>();
	public static List<Button> buttons = new ArrayList<Button>();
	public static Map<UIElement, List<Button>> uis = new HashMap<UIElement, List<Button>>();
	
	public static void loadUI(UIElement uie){
		ui.add(uie);
		uis.put(uie, uie.getButtons());
		for(Button b: uie.getButtons()){
			buttons.add(b);
		}
	}
	public static void removeUI(UIElement uie){
		ui.remove(uie);
		uis.remove(uie, uie.getButtons());
		for(Button b: uie.getButtons()){
			buttons.remove(b);
		}
	}
	
	public static void render(){
		UIRenderer.render(uis);
	}
	public static void cleanUp(){
		UIRenderer.cleanUp();
	}
	
	
}
