package m19.app.main;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import m19.app.exception.FileOpenFailedException;
import java.io.FileNotFoundException;
import java.io.IOException;
import m19.core.exception.MissingFileAssociationException;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<LibraryManager> {
  /** Name of the Savings File*/
  Input<String> _filename;
  /**
   * @param receiver
   */
  public DoSave(LibraryManager receiver) {
    super(Label.SAVE, receiver);
    if(receiver.getFilename() == null){
      _filename = _form.addStringInput(Message.newSaveAs());
    }
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
   try {
      if(_receiver.getFilename() != null){
        _receiver.save();
      }
      else{
        _form.parse();
        if(_filename.value().equals("")){
          throw new FileOpenFailedException(_filename.value());
        }
        _receiver.saveAs(_filename.value());
      }
    } catch (FileNotFoundException fnfe) {
      throw new FileOpenFailedException(_filename.value()/* fill with the missing file name*/);
    } catch (MissingFileAssociationException | IOException e) {
      e.printStackTrace();
    }
  }
}
