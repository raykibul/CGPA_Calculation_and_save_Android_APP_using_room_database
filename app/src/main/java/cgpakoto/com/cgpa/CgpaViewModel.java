package cgpakoto.com.cgpa;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

public class CgpaViewModel extends AndroidViewModel {
      private CgpaRepository cgpaRepository;
      private List<Course>allCourse;
      private  int semisterid;
      private LiveData<List<Semister>>allSemister;


    public CgpaViewModel(@NonNull Application application) {
        super(application);
        cgpaRepository=new CgpaRepository(application);
        allSemister=cgpaRepository.getmAllSemister();

    }

  public  LiveData<List<Semister>>getAllSemister(){
        return allSemister;
  }

  public List<Course> getAllCourse(String name){

        allCourse=cgpaRepository.getmAllCourse(name);
        return  allCourse;
  }

  public void insertCourse(Course course){
        cgpaRepository.InsertCourse(course);
  }

  public  void insertSemister(Semister semister){
        cgpaRepository.InsertSemister(semister);
  }

  public void deleteCourse(Course course){
        cgpaRepository.DelteteCourse(course);
  }

  public  void delteAllCourse(String name){
        cgpaRepository.DeleteAllcourseofSemister(name);
  }

  public void  deleteSemister(String name){
        cgpaRepository.DelteSemister(name);
  }

  public void updateSemister(Semister semister){
        cgpaRepository.UpdateSemister(semister);
  }

}
