package m19.app.main;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import m19.core.Date;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

/**
 * 4.1.3. Advance the current date.
 */
public class DoAdvanceDate extends Command<LibraryManager> {
  /* Time to be advanced*/
  Input<Integer> _time;
  /**
   * @param receiver
   */
  public DoAdvanceDate(LibraryManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    _time = _form.addIntegerInput(Message.requestDaysToAdvance());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    _form.parse();
    Date date = _receiver.getDate();
    date.advanceTime(_time.value());
  } 
}
