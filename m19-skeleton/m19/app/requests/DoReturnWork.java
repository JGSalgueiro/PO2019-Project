package m19.app.requests;

import m19.core.LibraryManager;
import m19.core.User;
import m19.core.Work;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;
import m19.app.exception.NoSuchUserException;
import m19.app.exception.NoSuchWorkException;
import m19.app.exception.WorkNotBorrowedByUserException;

/**
 * 4.4.2. Return a work.
 */
public class DoReturnWork extends Command<LibraryManager> {
  /** User id and Work id of the Return*/
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
  public final void execute() throws DialogException, NoSuchUserException, NoSuchWorkException, WorkNotBorrowedByUserException {
    try{
      _uId = _form.addIntegerInput(Message.requestUserId());
      _wId = _form.addIntegerInput(Message.requestWorkId());
      _form.parse();
      int uId = _uId.value();
      int wId = _wId.value();

      User _user = _receiver.getUser(_uId.value());
      if(_user == null){
        _form.clear();
        throw new NoSuchUserException(_uId.value());
      }
      int _fine = _receiver.getUserFine(_user);
      int _numRequests = _receiver.getNumberOfUserRequests(_user);

      int returnValue = _receiver.returnWork(_uId.value() , _wId.value());

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
      _form.clear();
      throw new NoSuchUserException(_uId.value());
    }catch(NoSuchWorkException e){
      _form.clear();
      throw new NoSuchWorkException(_wId.value());
    }catch(WorkNotBorrowedByUserException e){
      _form.clear(); 
      throw new WorkNotBorrowedByUserException(_wId.value(), _uId.value());
    }
  }
} 