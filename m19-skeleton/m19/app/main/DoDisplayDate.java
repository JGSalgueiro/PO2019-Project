package m19.app.main;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import m19.core.Date;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Display;

/**
 * 4.1.2. Display the current date.
 */
public class DoDisplayDate extends Command<LibraryManager> {
  /**
   * @param receiver
   */
  public DoDisplayDate(LibraryManager receiver) {
    super(Label.DISPLAY_DATE, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    Date date = _receiver.getDate();
    _display.add(Message.currentDate(date.getTime()));
    _display.display();
    _display.clear();
  }
  
}
