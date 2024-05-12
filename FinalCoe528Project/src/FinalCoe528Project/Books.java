package FinalCoe528Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class Books {

    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private CheckBox select;

    public Books(String n, double p) {
        name = new SimpleStringProperty(n);
        price = new SimpleDoubleProperty(p);
        this.select = new CheckBox();
        select.setSelected(false);
    }

    public String getName() {
        return name.get();
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(boolean s) {
        select.setSelected(s);
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price = new SimpleDoubleProperty(price);
    }

    public void write(File f) {
        String toWrite = (this.getName() + "," + this.getPrice() + "\n");
        try {
            FileWriter writer = new FileWriter(f, true);
            writer.write(toWrite);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");

        }
    }

    public static void fullWrite(File f) {
        try {
            FileWriter writer = new FileWriter(f, false);
            writer.write("");
            writer = new FileWriter(f, true);
            for (int i = 0; i < Login.BookList.size(); i++) {
                String toWrite = (Login.BookList.get(i).getName() + "," + Login.BookList.get(i).getPrice() + "\n");
                writer.write(toWrite);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");

        }
    }
}
