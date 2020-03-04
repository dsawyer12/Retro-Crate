import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Map;

public class DirectoryListItem extends ListCell<Console> {

    @FXML
    Label title;

    @FXML
    HBox root;

    @FXML
    ImageView consoleImg;

    private Map<Console, Image> imgCollection;

    public DirectoryListItem(Map<Console, Image> imgCollection) {
        this.imgCollection = imgCollection;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/directory_item.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Console item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            consoleImg.setImage(null);

            setGraphic(null);
            setText(null);
        }
        else {
            title.setText(item.getName());
            consoleImg.setImage(imgCollection.get(item));
//            if (item.getImgPath() != null) {
//                try {
//                    consoleImg.setImage(new Image(new FileInputStream(item.getImgPath())));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//            else {
//                System.out.println("graphic null");
//                setGraphic(null);
//            }

            setText(null);
            setGraphic(root);
        }
    }


}
