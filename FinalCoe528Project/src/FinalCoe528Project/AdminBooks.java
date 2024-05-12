package FinalCoe528Project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AdminBooks implements LoginState {

    private TableView<Books> table;
    private ObservableList<Books> data;

    final HBox hb = new HBox();

    public AdminBooks() {
        table = new TableView<Books>();
        data = FXCollections.observableArrayList();
        fill();
    }
    
    public void setState(LoginState s, Stage g) {
        Login.State = s;
        s.Display(g);
    }
    
    public void AddBooks(Books B) {
        Login.BookList.add(B);
    }

    public void fill() {
        for (int i = 0; i < Login.BookList.size(); i++) {
            data.add(Login.BookList.get(i));
        }
    }

    public void DeleteBooks(Books B) {
        Login.BookList.remove(B);
    }

    @Override
    public void Display(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setWidth(600);
        stage.setHeight(550);
        final Label label = new Label("Book List");
        label.setFont(new Font("Courier New Bold", 20));

        table.setEditable(true);

        TableColumn BookNameCol = new TableColumn("Name");
        BookNameCol.setMinWidth(225);
        BookNameCol.setCellValueFactory(
                new PropertyValueFactory<Books, String>("name"));

        TableColumn BookPriceCol = new TableColumn("Price");
        BookPriceCol.setMinWidth(225);
        BookPriceCol.setCellValueFactory(
                new PropertyValueFactory<Books, Double>("price"));

        table.setItems(data);
        table.getColumns().addAll(BookNameCol, BookPriceCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        final TextField newBookName = new TextField();
        newBookName.setPromptText("Book Name");
        newBookName.setMaxWidth(BookNameCol.getPrefWidth());
        newBookName.setMinWidth(180);
        final TextField newBookPrice = new TextField();
        newBookPrice.setMaxWidth(BookPriceCol.getPrefWidth());
        newBookPrice.setPromptText("Price");
        newBookPrice.setMinWidth(180);

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Books f = new Books(newBookName.getText(), Double.valueOf(newBookPrice.getText()));
                Boolean drue = true;
                for (int i = 0; i < Login.BookList.size(); i++) {
                    if ((Login.BookList.get(i).getName().equals(f.getName()))) {
                        drue = false;
                        break;
                    }
                }
                if(f.getName().isEmpty())
                    drue = false;
                if (drue) {
                    AddBooks(f);
                    data.add(f);
                }

                newBookName.clear();
                newBookPrice.clear();
            }
        });
        stage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            Customer.fullWrite(Login.myFileCustomer);
            Books.fullWrite(Login.myFileBooks);
        });
        final Button delButton = new Button("Delete");
        delButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ObservableList<Books> row;
                row = table.getSelectionModel().getSelectedItems();
                DeleteBooks(row.get(0));
                data.remove(row.get(0));

            }
        });
        final Button backButton = new Button("Back");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                Login.State = new AdminStartScreen();
                setState(Login.State, stage);
            }
        });
        hb.setMinWidth(505);
        hb.getChildren().addAll(newBookName, newBookPrice, addButton, delButton, backButton);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        vbox.setMinWidth(505);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        for (Node child : vbox.getChildren()) {
            VBox.setVgrow(child, Priority.ALWAYS);
        }
        stage.setScene(scene);
        stage.show();

    }

    
}
