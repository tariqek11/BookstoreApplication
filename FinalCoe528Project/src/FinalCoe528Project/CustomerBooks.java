package FinalCoe528Project;

import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CustomerBooks implements LoginState {

    private TableView<Books> table;
    private ObservableList<Books> data;

    final HBox hb = new HBox();

    private ArrayList<Books> cart = new ArrayList<Books>();

    public CustomerBooks() {
        table = new TableView<Books>();
        data = FXCollections.observableArrayList();
        fill();
    }
    public void setState(LoginState s, Stage g) {
        Login.State = s;
        s.Display(g);
    }

    public void BuyMoney(Customer C, double totalCost) {
        int points = calcPoints(totalCost);
        C.setPoints(C.getPoints() + points);
        C.setTotalPoints(C.getTotalPoints() + points);

        C.setStatus(calcMembership(C));

    }

    public double BuyPoints(Customer C, double totalCost) {

        int usePoints = C.getPoints();

        if ((usePoints / 100) >= totalCost) {
            C.setPoints(usePoints - (int) totalCost * 100);
            totalCost = 0;
            C.setStatus(calcMembership(C));
        } else {
            totalCost = totalCost - C.getPoints() / 100;
            C.setPoints(0);
            BuyMoney(C, totalCost);

        }
        return totalCost;
    }

    public String calcMembership(Customer C) {
        String status;
        int totalPoints = C.getPoints();
        if (totalPoints < 1000) {
            status = "Silver";
        } else {
            status = "Gold";
        }
        return status;
    }

    public void AddBookCart(Books b) {
        cart.add(b);
    }

    public void RemoveBookCart(Books b) {
        for (int i = 0; i < cart.size(); i++) {
            if (((cart.get(i)).getName()).equals(b.getName()) && ((cart.get(i)).getPrice()) == b.getPrice()) {
                cart.remove(i);
                break;
            }
        }
    }

    public int calcPoints(double totalcost) {
        int points;
        points = (int) totalcost * 10;
        return (points);
    }

    public double TotalCost() {
        double totalCost = 0;
        for (int i = 0; i < cart.size(); i++) {
            totalCost = totalCost + cart.get(i).getPrice();
        }
        return totalCost;
    }

    public void fill() {
        for (int i = 0; i < Login.BookList.size(); i++) {
            data.add(Login.BookList.get(i));
        }
    }

    @Override
    public void Display(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setWidth(600);
        stage.setHeight(600);
        final Label label = new Label("Welcome " + Login.CurrentUser.getUsername() + ". You have " + ((Customer) Login.CurrentUser).getPoints() + " Points.\n");
        label.setFont(new Font("Courier New Bold", 20));
        final Label label2 = new Label("Your Status is " + ((Customer) Login.CurrentUser).getStatus()+".");
        label2.setFont(new Font("Courier New Bold", 20));
        final Label label4 = new Label("Book Catalog");
        label4.setFont(new Font("Courier New Bold", 20));

        table.setEditable(false);
        TableColumn SelectCol = new TableColumn("Select");
        SelectCol.setMinWidth(50);
        SelectCol.setCellValueFactory(new PropertyValueFactory<Books, String>("select"));

        TableColumn BookNameCol = new TableColumn("Book Name");
        BookNameCol.setMinWidth(250);
        BookNameCol.setCellValueFactory(
                new PropertyValueFactory<Books, String>("name"));

        TableColumn BookPriceCol = new TableColumn("Book Price");
        BookPriceCol.setMinWidth(100);
        BookPriceCol.setCellValueFactory(
                new PropertyValueFactory<Books, Double>("price"));

        table.setItems(data);
        table.getColumns().addAll(SelectCol, BookNameCol, BookPriceCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        final Button BuyButton = new Button("Buy");
        BuyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (int i = 0; i < Login.BookList.size(); i++) {
                    if (Login.BookList.get(i).getSelect().isSelected()) {
                        cart.add(Login.BookList.get(i));
                        Login.BookList.get(i).getSelect().setSelected(false);
                    }
                }
                double total = TotalCost();
                BuyMoney(((Customer) Login.CurrentUser), total);
                ExitScreen s = new ExitScreen(total, ((Customer) Login.CurrentUser).getPoints(), ((Customer) Login.CurrentUser).getStatus());
                for (int i = 0; i < cart.size(); i++) {
                    Login.BookList.remove(cart.get(i));
                }
                cart.clear();
                setState(s, stage);
            }
        });
        stage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            Customer.fullWrite(Login.myFileCustomer);
            Books.fullWrite(Login.myFileBooks);
        });
        final Button BuyPointsButton = new Button("Buy with Points");
        BuyPointsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (int i = 0; i < Login.BookList.size(); i++) {
                    if (Login.BookList.get(i).getSelect().isSelected()) {
                        cart.add(Login.BookList.get(i));
                        Login.BookList.get(i).setSelect(false);
                    }
                }

                double totalCost = TotalCost();
                totalCost = BuyPoints(((Customer) Login.CurrentUser), totalCost);
                ExitScreen s = new ExitScreen(totalCost, ((Customer) Login.CurrentUser).getPoints(), ((Customer) Login.CurrentUser).getStatus());
                for (int i = 0; i < cart.size(); i++) {
                    Login.BookList.remove(cart.get(i));
                }
                cart.clear();
                setState(s, stage);
            }
        });
        final Button logout = new Button("Log-Out");
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (int i = 0; i < cart.size(); i++) {
                    cart.get(i).setSelect(false);
                }
                Customer.fullWrite(Login.myFileCustomer);
                cart.clear();
                Login.State = new Login();
                setState(Login.State, stage);

            }
        });

        hb.getChildren().addAll(BuyButton, BuyPointsButton, logout);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setMinWidth(550);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, label2, label4, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        for (Node child : vbox.getChildren()) {
            VBox.setVgrow(child, Priority.ALWAYS);
        }
        stage.setScene(scene);
        stage.show();

    }

}
