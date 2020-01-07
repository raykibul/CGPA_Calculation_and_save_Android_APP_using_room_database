package cgpakoto.com.cgpa;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {
    Double credit,gpa;
    @PrimaryKey(autoGenerate = true)
    int id;

    String  semister_name;

    public String getSemister_name() {
        return semister_name;
    }

    public void setSemister_name(String semister_name) {
        this.semister_name = semister_name;
    }

    public Course(Double credit, Double gpa) {
        this.credit = credit;
        this.gpa = gpa;

    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
