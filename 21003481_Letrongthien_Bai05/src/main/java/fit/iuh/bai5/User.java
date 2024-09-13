package fit.iuh.bai5;

import java.time.LocalDate;
import java.util.Date;

import jakarta.json.bind.annotation.JsonbDateFormat;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate dob;

    public User() {
    }

    public User(int id, String firstName, String lastName, LocalDate dob) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }
    
    public User( String firstName, String lastName, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }
    

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
