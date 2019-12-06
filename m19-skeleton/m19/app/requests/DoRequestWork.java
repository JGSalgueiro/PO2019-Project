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
 * 4.4.1. Request work.
 */
public class DoRequestWork extends Command<LibraryManager> {
  /** Name of the User and Id of the Work to be requested */
  private Input<Integer> _uId;
  private Input<Integer> _wId;
  private Input<String> _wantsInfo;

  /**
   * @param receiver
   */
  public DoRequestWork(LibraryManager receiver) {
    super(Label.REQUEST_WORK, receiver);
    _uId = _form.addIntegerInput(Message.requestUserId());
    _wId = _form.addIntegerInput(Message.requestWorkId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();

    if(_uId.value() != null && _wId.value() != null){
      int returnValue = _receiver.requestWork(_uId.value() , _wId.value());

      if(returnValue != -1){
        _display.popup(Message.workReturnDay(_wId.value(), returnValue));
      }
      else{
      	int uId = _uId.value();
      	int wId = _wId.value();
        _form.clear();
        _wantsInfo = _form.addStringInput(Message.requestReturnNotificationPreference());
        _form.parse();
        if(_wantsInfo.value().equals("s")){
        	_receiver.addUserReq(uId, wId);
        }
      }
    }
  }
}
