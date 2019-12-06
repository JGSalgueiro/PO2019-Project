package m19.app.users;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import m19.app.exception.UserRegistrationFailedException;
import pt.tecnico.po.ui.Input;

/**
 * 4.2.1. Register new user.
 */
public class DoRegisterUser extends Command<LibraryManager> {
  /** Name and Email of the User to be Registered*/
  private Input<String> _uName;
  private Input<String> _uMail;
  /**
   * @param receiver
   */
  public DoRegisterUser(LibraryManager receiver) {
    super(Label.REGISTER_USER, receiver);
    _uName = _form.addStringInput(Message.requestUserName());
    _uMail = _form.addStringInput(Message.requestUserEMail());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      if(!_uName.value().equals("") && !_uMail.value().equals("")){
        _receiver.registerUser(_uName.value(), _uMail.value());
        _display.add(Message.userRegistrationSuccessful(_receiver.getUserNum() - 1));
        _display.display();
      }
    } catch(NullPointerException e){
      throw new UserRegistrationFailedException(_uName.value(), _uMail.value());
    }
  }
}