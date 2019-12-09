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
  /** User id and Work id of the Return*/
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
  public final void execute() throws DialogException, NoSuchUserException, NoSuchWorkException {
    try{
      _form.clear();
      _uId = _form.addIntegerInput(Message.requestUserId());
      _wId = _form.addIntegerInput(Message.requestWorkId());
      _form.parse();

      int returnValue = _receiver.returnWork(_uId.value() , _wId.value());

      _user = _receiver.getUser(_uId.value());
      _fine = _receiver.getUserFine(_user);
      _numRequests = _receiver.getNumberOfUserRequests(_user);

      if(returnValue > 0){
        _form.clear();
        _display.popup(Message.showFine(_uId.value(), returnValue + _fine));

        _wantsInfo = _form.addStringInput(Message.requestFinePaymentChoice());
        _form.parse();
        if(_wantsInfo.value().equals("s")){
          if(_numRequests == 0){
            _receiver.payFine(_uId.value(), returnValue + _fine);
          }
          else{
            _receiver.payFine(_uId.value(), 0);
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
    }catch(NoSuchUserException e){
      throw new NoSuchUserException(_uId.value());
    }catch(NoSuchWorkException e){
      throw new NoSuchWorkException(_wId.value());
    }
  }
} 