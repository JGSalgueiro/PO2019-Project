package m19.app.works;

import m19.core.LibraryManager;
import java.util.*;
import m19.core.Dvd;
import m19.core.Book;
import m19.core.Work;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Display;

/**
 * 4.3.2. Display all works.
 */
public class DoDisplayWorks extends Command<LibraryManager> {

  /**
   * @param receiver
   */
  public DoDisplayWorks(LibraryManager receiver) {
    super(Label.SHOW_WORKS, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    List<Work> workArrays = new ArrayList<Work>(_receiver.getAllWorks().values());
    for(Work w : workArrays){
    _display.addLine(w.toString());
    }
    _display.display();
  }
}
