package FinalCoe528Project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ExitScreen implements LoginState {

    double totalCost;
    int points;
    String Status;

    public void setState(LoginState s, Stage g) {
        Login.State = s;
        s.Display(g);
    }
    public ExitScreen(double t, int p, String s) {
        this.totalCost = t;
        this.points = p;
        this.Status = s;
    }

    @Override
    public void Display(Stage primaryStage) {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 600, 550);
        primaryStage.setScene(scene);

        Label Total = new Label("Your total is: " + this.totalCost);
        Total.setFont(Font.font("Courier New Bold", FontWeight.NORMAL, 20));
        grid.add(Total, 0, 0);
        Label point = new Label("Points: " + this.points);
        point.setFont(Font.font("Courier New Bold", FontWeight.NORMAL, 20));
        grid.add(point, 0, 1);
        Label Stat = new Label("Your Status is: " + this.Status);
        Stat.setFont(Font.font("Courier New Bold", FontWeight.NORMAL, 20));
        grid.add(Stat, 0, 2);

        Button btn = new Button("Logout");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 3);

        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            Customer.fullWrite(Login.myFileCustomer);
            Books.fullWrite(Login.myFileBooks);
        });
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Customer.fullWrite(Login.myFileCustomer);
                Books.fullWrite(Login.myFileBooks);
                Login b = new Login();
                setState(b, primaryStage);
            }
        });

    }

}
