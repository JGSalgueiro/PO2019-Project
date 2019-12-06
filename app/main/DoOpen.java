package m19.app.main;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import m19.app.exception.FileOpenFailedException;
import java.io.FileNotFoundException;
import java.io.IOException;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<LibraryManager> {
  /** Name of the input file */
  Input<String> _filename;
  /**
   * @param receiver
   */
  public DoOpen(LibraryManager receiver) {
    super(Label.OPEN, receiver);
    _filename = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    try {
      _form.parse();
      _receiver.load(_filename.value());
    } catch (FileNotFoundException fnfe) {
      throw new FileOpenFailedException(_filename.value()/* fill with the missing file name*/);
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
  }

}
