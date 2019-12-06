package m19.app.requests;

import m19.core.LibraryManager;
import m19.core.User;
import m19.core.Work;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;
// FIXME import other core concepts
// FIXME import other ui concepts

/**
 * 4.4.2. Return a work.
 */
public class DoReturnWork extends Command<LibraryManager> {

  // FIXME define input fields
  private Input<Integer> _uId;
  private Input<Integer> _wId;
  private Input<String> _wantsInfo;

  /**
   * @param receiver
   */
  public DoReturnWork(LibraryManager receiver) {
    super(Label.RETURN_WORK, receiver);
    // FIXME initialize input fields
    _uId = _form.addIntegerInput(Message.requestUserId());
    _wId = _form.addIntegerInput(Message.requestWorkId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    // FIXME implement command
    _form.parse();

    if(_uId.value() != null && _wId.value() != null){
      int returnValue = _receiver.returnWork(_uId.value() , _wId.value());

      if(returnValue > 0){
        _form.clear();
        _wantsInfo = _form.addStringInput(Message.requestFinePaymentChoice());
        _form.parse();
      }
    }
  }
}
