package m19.app.works;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;

/** 4.3. Works menu. */
public class Menu extends pt.tecnico.po.ui.Menu {

  /**
   * @param receiver
   */
  public Menu(LibraryManager receiver) {
    super(Label.TITLE, new Command<?>[] { // 4.3
      new DoDisplayWork(receiver), // 4.3.1
      new DoDisplayWorks(receiver), // 4.3.2
      new DoPerformSearch(receiver), // 4.3.3
    });
  }

}
