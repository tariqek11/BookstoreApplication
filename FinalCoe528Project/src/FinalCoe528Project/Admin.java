package FinalCoe528Project;

import java.io.File;

public class Admin extends User {

    public Admin() { //inits admin properties
        super.setPassword("admin");
        super.setUsername("admin");
    }

    @Override
    public void read(File f) {

    }

    @Override
    public void write(File f) {

    }

}
