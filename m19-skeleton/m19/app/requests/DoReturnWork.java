package m19.app.requests;

import m19.core.LibraryManager;
import m19.core.User;
import m19.core.Work;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;

/**
 * 4.4.2. Return a work.
 */
public class DoReturnWork extends Command<LibraryManager> {
  private Input<Integer> _uId;
  private Input<Integer> _wId;
  private Input<String> _wantsInfo;

  /**
   * @param receiver
   */
  public DoReturnWork(LibraryManager receiver) {
    super(Label.RETURN_WORK, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try{
      _form.clear();
      _uId = _form.addIntegerInput(Message.requestUserId());
      _wId = _form.addIntegerInput(Message.requestWorkId());
      _form.parse();
      int uId = _uId.value();
      int wId = _wId.value();

      int returnValue = _receiver.returnWork(uId , wId);

      if(returnValue > 0){
        _form.clear();
        _display.popup(Message.showFine(uId, returnValue));
        _wantsInfo = _form.addStringInput(Message.requestFinePaymentChoice());
        _form.parse();
        if(_wantsInfo.value().equals("s")){
          _receiver.payFine(uId);
        }
      }
      _form.clear();
    }catch(NullPointerException e){}
  }
}