package m19.app.users;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import m19.core.LibraryManager;
import m19.core.User;
import m19.app.exception.NoSuchUserException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Display;
/**
 * 4.2.2. Show specific user.
 */
public class DoShowUser extends Command<LibraryManager> {
  /** Id of the User to be shown*/
  private Input<Integer> _id;
  /**
   * @param receiver
   */
  public DoShowUser(LibraryManager receiver) {
    super(Label.SHOW_USER, receiver);
    _id = _form.addIntegerInput(Message.requestUserId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException{
    _form.parse();
    try{
      User u = _receiver.getUser(_id.value());
      _display.popup(u.toString());
    }catch(NullPointerException e){
      throw new NoSuchUserException(_id.value());
    };
  }
}
