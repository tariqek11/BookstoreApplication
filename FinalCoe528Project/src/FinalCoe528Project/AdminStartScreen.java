package FinalCoe528Project;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AdminStartScreen implements LoginState {
    
    public void setState(LoginState s, Stage g) {
        Login.State = s;
        s.Display(g);
    }
    
    @Override
    public void Display(Stage primaryStage) {
        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.setPadding(new Insets(25, 25, 25, 25));

        //Buttons on admin start screen
        Button books = new Button("Books");
        Button customers = new Button("Customers");
        Button logout = new Button("Logout");
        books.prefHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 3));

        customers.prefHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 3));

        logout.prefHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 3));

        books.setMinWidth(400);
        customers.setMinWidth(400);
        logout.setMinWidth(400);
        books.setFont(new Font(20));
        customers.setFont(new Font(20));
        logout.setFont(new Font(20));
        grid2.add(books, 0, 0);
        grid2.add(customers, 0, 1);
        grid2.add(logout, 0, 2);

        //Eventhandler for each button
        books.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Changing to admin books screen.");
                AdminBooks g = new AdminBooks();
                setState(g, primaryStage);
            }
        });

        customers.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Changing to admin customers screen.");
                AdminCustomer ac = new AdminCustomer();
                setState(ac, primaryStage);
            }
        });
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            Customer.fullWrite(Login.myFileCustomer);
            Books.fullWrite(Login.myFileBooks);
        });
        logout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Changing to login screen.");
                Customer.fullWrite(Login.myFileCustomer);
                Books.fullWrite(Login.myFileBooks);
                Login lo2 = new Login();
                setState(lo2, primaryStage);
            }
        });

        Scene scene = new Scene(grid2, 600, 550);

        primaryStage.setTitle("Bookstore App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
