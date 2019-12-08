package m19.app.requests;

import m19.core.LibraryManager;
import m19.core.User;
import m19.core.Work;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;
import m19.app.exception.NoSuchUserException;
import m19.app.exception.NoSuchWorkException;

/**
 * 4.4.2. Return a work.
 */
public class DoReturnWork extends Command<LibraryManager> {
  private Input<Integer> _uId;
  private Input<Integer> _wId;
  private Input<String> _wantsInfo;
  private User _user;
  private int _fine;
  private int _numRequests;

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

      _user = _receiver.getUser(_uId.value());
      _fine = _receiver.getUserFine(_user);
      _numRequests = _receiver.getNumberOfUserRequests(_user);

      int returnValue = _receiver.returnWork(uId , wId);

      if(returnValue > 0){
        _form.clear();
        _display.popup(Message.showFine(uId, returnValue + _fine));

        _wantsInfo = _form.addStringInput(Message.requestFinePaymentChoice());
        _form.parse();
        if(_wantsInfo.value().equals("s")){
          if(_numRequests == 0){
            _receiver.payFine(uId, returnValue + _fine);
          }
          else{
            _receiver.payFine(uId, 0);
          }
        }
        else if(_wantsInfo.value().equals("n")){
          if(_numRequests == 0){
            _receiver.updateFine(returnValue + _fine, _user);
          }
          else{
            _receiver.updateFine(returnValue, _user);
          }
        }
      }
      _form.clear();
    }catch(NullPointerException e){
      _form.clear();
      throw new NoSuchUserException(_uId.value());
    }
  }
} 