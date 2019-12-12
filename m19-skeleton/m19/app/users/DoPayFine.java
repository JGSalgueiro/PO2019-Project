package m19.app.users;

import m19.core.LibraryManager;
import m19.core.User;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;
import m19.app.exception.NoSuchUserException;

/**
 * 4.2.5. Settle a fine.
 */
public class DoPayFine extends Command<LibraryManager> {
  private Input<Integer> _uId;

  /**
   * @param receiver
   */
  public DoPayFine(LibraryManager receiver) {
    super(Label.PAY_FINE, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try{
      _form.clear();
      _uId = _form.addIntegerInput(Message.requestUserId());
      _form.parse();
      User _user = _receiver.getUser(_uId.value());
      _receiver.payAllFine(_uId.value());

    }catch(NullPointerException e){
      throw new NoSuchUserException(_uId.value());
    }
  }
}
