package m19.app.users;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import m19.core.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import pt.tecnico.po.ui.Display;

/**
 * 4.2.4. Show all users.
 */
public class DoShowUsers extends Command<LibraryManager> {

  /**
   * @param receiver
   */
  public DoShowUsers(LibraryManager receiver) {
    super(Label.SHOW_USERS, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    List<User> userList = new ArrayList<User>(_receiver.getAllUsers().values());
    Collections.sort(userList,new ComparatorName());
    for(User u : userList){
      int id = u.getUserID();
      String name = u.getName();
      String email = u.getEmail();
      String behaviour = u.getUserBehaviour();
      Boolean isSuspended = u.getIsSuspended();
      int fine = u.getFine();
      if(isSuspended == false){
        _display.addLine(id + " - " + name + " - " + email + " - " + behaviour + " - ACTIVO");
      }
      else if (isSuspended == true) {
        _display.addLine(id + " - " + name + " - " + email + " - " + behaviour + " - SUSPENSO" + " - EUR " + fine);
      }
    }
    _display.display();
  }
}