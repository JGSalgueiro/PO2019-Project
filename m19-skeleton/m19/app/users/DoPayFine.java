package m19.app.users;

import m19.core.LibraryManager;
import m19.core.User;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;
import m19.app.exception.NoSuchUserException;
// FIXME import other core concepts
// FIXME import other ui concepts

/**
 * 4.2.5. Settle a fine.
 */
public class DoPayFine extends Command<LibraryManager> {

  private Input<Integer> _uId;
  private User _user;
  private Input<String> _wantToPay;

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

      _user = _receiver.getUser(_uId.value());

      _receiver.payFine(_uId.value(), _receiver.getUserFine(_user));

    }catch(NullPointerException e){
      throw new NoSuchUserException(_uId.value());
    }
  }
}
