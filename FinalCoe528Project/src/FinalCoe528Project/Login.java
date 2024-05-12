package FinalCoe528Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application implements LoginState {

    private final String filename1 = "customer.txt";
    private final String filename2 = "books.txt";
    protected static File myFileCustomer;
    protected static File myFileBooks;
    protected static LoginState State;
    protected static User CurrentUser;
    protected static ArrayList<Books> BookList = new ArrayList<Books>();
    protected static ArrayList<User> CustList = new ArrayList<User>();

    public Login() {

        myFileCustomer = new File(filename1);
        myFileBooks = new File(filename2);
        try {

            if (myFileCustomer.createNewFile()) {
                System.out.println("File created: " + myFileCustomer.getName());
            }

            if (myFileBooks.createNewFile()) {
                System.out.println("File created: " + myFileBooks.getName());
            }

            fillLists();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void set(String u, String p) {
        if (u.equals("admin") && p.equals("admin")) {
            CurrentUser = new Admin();
        } 
        else {
            CurrentUser = (Customer) new Customer(u, p);
            for (int i = 0; i < CustList.size(); i++) {
                if (((Customer) CurrentUser).isequals((Customer) (CustList.get(i)))) {
                    CurrentUser = CustList.get(i);
                    break;
                }
            }
            if (!CustList.contains(CurrentUser)) {
                CustList.add(CurrentUser);
            }

        }
    }

    public void fillLists() {
        CustList.clear();
        BookList.clear();
        try {
            Customer c;
            Scanner scanner = new Scanner(Login.myFileCustomer);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                c = new Customer(parts[0], parts[1]);
                c.setPoints(Integer.parseInt(parts[2]));
                c.setStatus(parts[3]);
                c.setTotalPoints(Integer.parseInt(parts[4]));
                Login.CustList.add(c);

            }
            scanner.close();
        } catch (FileNotFoundException e) {
        }
        try {
            Books c;

            Scanner scanner1 = new Scanner(Login.myFileBooks);
            while (scanner1.hasNextLine()) {
                String line = scanner1.nextLine();
                String[] parts = line.split(",");
                c = new Books(parts[0], Double.parseDouble(parts[1]));
                Login.BookList.add(c);

            }
            scanner1.close();
        } catch (FileNotFoundException e) {

        }

    }

    public void setState(LoginState s, Stage g) {
        State = s;
        s.Display(g);
    }

    @Override
    public void start(Stage primaryStage) {
        Display(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void Display(Stage primaryStage) {
        primaryStage.setTitle("Bookstore Application");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 600, 550);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        Text scenetitle = new Text("Welcome To The Bookstore");
        scenetitle.setFont(new Font("Courier New Bold", 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Log in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        primaryStage.show();
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String pass = "";
                String user= "";
                //Display buttons and Login functions 
                user = userTextField.getText();
                pass = pwBox.getText();

                if(!user.equals("")&&!pass.equals("")){
                set(user, pass);
                if (CurrentUser instanceof Admin) {
                    State = new AdminStartScreen();
                    setState(State, primaryStage);
                } 
                else {
                    CustomerBooks b = new CustomerBooks();
                    State = b;
                    setState(State, primaryStage);
                }
                } 
            }
        });
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            Customer.fullWrite(myFileCustomer);
            Books.fullWrite(myFileBooks);
        });

    }

}
