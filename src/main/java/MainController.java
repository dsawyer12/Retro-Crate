import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable, EventHandler<MouseEvent> {

    @FXML
    VBox sideNav;

    @FXML
    TextField searchBar;

    @FXML
    Label topPaneTitle;

    @FXML
    private ListView<Console> listView;

    private ObservableList<Console> projectObservableList;
    private Map<Console, Image> imgCollection;

    public MainController() throws IOException {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("id integer PRIMARY KEY");
        columns.add("name text NOT NULL");
        columns.add("capacity real");
        DatabaseManager.createNewTable("new table", columns);
//        projectObservableList = FXCollections.observableArrayList();
//        projectObservableList.addAll(ContentScraper.getConsoleData());
//        imgCollection = new HashMap<>();
//        for (Console console : projectObservableList) {
//            if (console.getImgPath() != null)
//                imgCollection.put(console, new Image(new FileInputStream(console.getImgPath())));
//        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setCellFactory(projectListView -> new DirectoryListItem(imgCollection));
        listView.setItems(projectObservableList);
        listView.setOnMouseClicked(event -> topPaneTitle.setText(listView.getSelectionModel().getSelectedItem().getName()));
        DragResizer.makeResizable(sideNav, 5);
        DragResizer.makeResizable(listView, 0);
    }

    @Override
    public void handle(MouseEvent event) {

    }
}
