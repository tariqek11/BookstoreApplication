package FinalCoe528Project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
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

public class AdminCustomer implements LoginState {

    private TableView<Customer> table;
    private ObservableList<Customer> data;

    final HBox hb = new HBox();
    
    public void setState(LoginState s, Stage g) {
        Login.State = s;
        s.Display(g);
    }
    public AdminCustomer() {
        table = new TableView<Customer>();
        data = FXCollections.observableArrayList();
        fill();
    }

    public void AddCustomer(Customer B) {
        Login.CustList.add(B);
    }

    public void DeleteCustomer(Customer B) {
        Login.CustList.remove(B);
    }

    public void fill() {
        for (int i = 0; i < Login.CustList.size(); i++) {
            data.add((Customer) Login.CustList.get(i));
        }
    }

    @Override
    public void Display(Stage stage) {
        stage.setWidth(600);
        stage.setHeight(550);
        Scene scene = new Scene(new Group(), 600, 550);
        final Label label = new Label("Customer List");
        label.setFont(new Font("Courier New Bold", 20));

        table.setEditable(true);

        TableColumn UserNameCol = new TableColumn("Username");
        UserNameCol.setMinWidth(125);
        UserNameCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("username"));

        TableColumn PasswordCol = new TableColumn("Password");
        PasswordCol.setMinWidth(125);
        PasswordCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("password"));

        TableColumn PointsCol = new TableColumn("Points");
        PointsCol.setMinWidth(125);
        PointsCol.setCellValueFactory(
                new PropertyValueFactory<Customer, Integer>("points"));

        table.setItems(data);
        table.getColumns().addAll(UserNameCol, PasswordCol, PointsCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        final TextField newCustomerUsername = new TextField();
        newCustomerUsername.setPromptText("Username");
        newCustomerUsername.setMaxWidth(UserNameCol.getPrefWidth());
        newCustomerUsername.setMinWidth(180);
        final TextField newCustomerPassword = new TextField();
        newCustomerPassword.setMaxWidth(PasswordCol.getPrefWidth());
        newCustomerPassword.setPromptText("Password");
        newCustomerPassword.setMinWidth(180);

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Customer f = new Customer(newCustomerUsername.getText(), newCustomerPassword.getText());
                Boolean drue = true;
                for (int i = 0; i < Login.CustList.size(); i++) {
                    if ((Login.CustList.get(i).getUsername().equals(f.getUsername())) && (Login.CustList.get(i).getPassword().equals(f.getPassword()))) {
                        drue = false;
                        break;
                    }
                }
                if(f.getUsername().isEmpty())
                    drue = false;
                if(f.getPassword().isEmpty())
                    drue = false;
                if (drue) {
                    AddCustomer(f);
                    data.add(f);
                }
                newCustomerUsername.clear();
                newCustomerPassword.clear();
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
                ObservableList<Customer> row;
                row = table.getSelectionModel().getSelectedItems();
                DeleteCustomer(row.get(0));
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
        hb.getChildren().addAll(newCustomerUsername, newCustomerPassword, addButton, delButton, backButton);
        hb.setSpacing(3);
        HBox.setHgrow(newCustomerUsername, Priority.ALWAYS);
        HBox.setHgrow(newCustomerPassword, Priority.ALWAYS);
        HBox.setHgrow(addButton, Priority.ALWAYS);
        HBox.setHgrow(delButton, Priority.ALWAYS);
        HBox.setHgrow(backButton, Priority.ALWAYS);

        final VBox vbox = new VBox();
        vbox.autosize();
        vbox.setSpacing(5);
        vbox.setMinWidth(505);
        vbox.setPadding(new Insets(25, 25, 25, 25));
        vbox.getChildren().addAll(label, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

    }


}
