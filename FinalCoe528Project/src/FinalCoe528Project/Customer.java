package FinalCoe528Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javafx.beans.property.SimpleIntegerProperty;

public class Customer extends User {

    private SimpleIntegerProperty points;
    private String Status;
    private int totalPoints;

    public Customer(String n, String p) {
        super.setUsername(n);
        super.setPassword(p);
        points = new SimpleIntegerProperty(0);
        Status = "Silver";
        totalPoints = 0;
    }

    public boolean isequals(Customer o) {
        if (o.getUsername().equals(super.getUsername()) && (o.getPassword().equals(super.getPassword()))) {
            return true;
        }
        return false;

    }

    public int getPoints() {
        return points.get();
    }

    public void setPoints(int points) {
        this.points = new SimpleIntegerProperty(points);
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public void read(File f) {
        try {
            Scanner scanner = new Scanner(f);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if ((line.contains(super.getPassword())) && (line.contains(super.getUsername()))) {
                    String[] parts = line.split(",");
                    super.setUsername(parts[0]);
                    super.setPassword(parts[1]);
                    this.setPoints(Integer.parseInt(parts[2]));
                    this.setStatus(parts[3]);
                    this.setTotalPoints(Integer.parseInt(parts[4]));
                    break;
                }
            }
        } catch (FileNotFoundException e) {

        }
    }

    public void write(File f) {
        String toWrite = (super.getUsername() + "," + super.getPassword() + "," + Integer.toString(this.getPoints()) + "," + this.getStatus() + "," + Integer.toString(this.getTotalPoints()) + "\n");
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
            for (int i = 0; i < Login.CustList.size(); i++) {
                String toWrite = (Login.CustList.get(i).getUsername() + "," + Login.CustList.get(i).getPassword() + "," + Integer.toString(((Customer) Login.CustList.get(i)).getPoints()) + "," + ((Customer) Login.CustList.get(i)).getStatus() + "," + Integer.toString(((Customer) Login.CustList.get(i)).getTotalPoints()) + "\n");
                writer.write(toWrite);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");

        }
    }

}
