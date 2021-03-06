package m19.app.works;

import m19.core.LibraryManager;
import m19.core.Work;
import m19.core.Dvd;
import m19.core.Book;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.Display;
import m19.app.exception.NoSuchWorkException;

/**
 * 4.3.1. Display work.
 */
public class DoDisplayWork extends Command<LibraryManager> {
  /** Id to Display */
  private Input<Integer> _id;
  /**
   * @param receiver
   */
  public DoDisplayWork(LibraryManager receiver) {
    super(Label.SHOW_WORK, receiver);
    _id = _form.addIntegerInput(Message.requestWorkId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    int id = _id.value();
    try{
      Work work = _receiver.getWorkbyId(id);
      _display.popup(work.toString());
    }catch(NullPointerException e){
      throw new NoSuchWorkException(id);
    }
  }
}