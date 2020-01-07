package cgpakoto.com.cgpa;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Semister {
    @PrimaryKey(autoGenerate = true)
    int id;
    double cgpa;
    double totalcredit;

    public Semister(double cgpa, String semister_name,double totalcredit) {
        this.cgpa = cgpa;
        this.semister_name = semister_name;
        this.totalcredit=totalcredit;
    }

    public double getTotalcredit() {
        return totalcredit;
    }

    public void setTotalcredit(double totalcredit) {
        this.totalcredit = totalcredit;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    String semister_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSemister_name() {
        return semister_name;
    }



    public void setSemister_name(String semister_name) {
        this.semister_name = semister_name;
    }
}
