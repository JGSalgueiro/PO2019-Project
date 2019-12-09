package m19.app.works;

import m19.core.LibraryManager;
import m19.core.Work;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import java.util.List;
import java.util.ArrayList;

/**
 * 4.3.3. Perform search according to miscellaneous criteria.
 */
public class DoPerformSearch extends Command<LibraryManager> {

  private Input<String> _searchTerm;
  private List<Work> _searchedWorks;

  /**
   * @param m
   */
  public DoPerformSearch(LibraryManager m) {
    super(Label.PERFORM_SEARCH, m);
    _searchTerm = _form.addStringInput(Message.requestSearchTerm());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    _form.parse();

    if(!_searchTerm.value().equals("")){
      _searchedWorks = _receiver.worksSearchedByGivenTerm(_searchTerm.value());
    
      for(Work w : _searchedWorks){
        _display.addLine(w.toString());
      }
      _display.display();
    }
  } 
}
