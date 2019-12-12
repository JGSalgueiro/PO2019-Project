package m19.app.users;

import m19.core.LibraryManager;
import m19.core.User;
import m19.core.Notification;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import java.util.List;

/**
 * 4.2.3. Show notifications of a specific user.
 */
public class DoShowUserNotifications extends Command<LibraryManager> {
  private Input<Integer> _uId;

  /**
   * @param receiver
   */
  public DoShowUserNotifications(LibraryManager receiver) {
    super(Label.SHOW_USER_NOTIFICATIONS, receiver);
    _uId = _form.addIntegerInput(Message.requestUserId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    List<Notification> _notification = _receiver.warnUserWhenWorkIsAvailable(_uId.value());
    for(Notification n : _notification){
      _display.addLine(n.toString());
    }
    _receiver.clearUserNotifications(_uId.value());
    _display.display();
  }
}
