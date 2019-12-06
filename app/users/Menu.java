package m19.app.users;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;

/** 4.2. Users menu. */
public class Menu extends pt.tecnico.po.ui.Menu {

  /**
   * @param receiver
   */
  public Menu(LibraryManager receiver) {
    super(Label.TITLE, new Command<?>[] { //4.2
      new DoRegisterUser(receiver), //4.2.1
      new DoShowUser(receiver), //4.2.2
      new DoShowUsers(receiver), //4.2.4
      new DoShowUserNotifications(receiver), // Not Concretized
      new DoPayFine(receiver), // Not Concretized
    });
  }

}


