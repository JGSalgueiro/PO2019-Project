package m19.app.main;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;

/** 4.1. Main menu. */
public class Menu extends pt.tecnico.po.ui.Menu {
  /**
   * @param receiver
   */
  public Menu(LibraryManager receiver) {
    super(Label.TITLE, new Command<?>[] { //4.1
      new DoOpen(receiver), // 4.1.1
      new DoSave(receiver), // 4.1.1
      new DoDisplayDate(receiver), // 4.1.2
      new DoAdvanceDate(receiver), // 4.1.3
      new DoOpenUsersMenu(receiver), // 4.1.4
      new DoOpenWorksMenu(receiver), // 4.1.4
      new DoOpenRequestsMenu(receiver), // 4.1.4
    });
  }

}
