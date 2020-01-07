package cgpakoto.com.cgpa;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert
    void InsertCourse(Course course);

    @Update
    void UpdateCourse(Course course);

    @Delete
    void DeleteCourse(Course course);

    @Query("Select * from Course ORDER BY id ASC ")
    LiveData<List<Course>> GetAllCourses();

    @Query("select * from Course where semister_name like :name")
    List<Course> GetCourseById(String name);

    @Query("delete From Course where semister_name like :name")
     void  DeleteCourseOfSemister(String name);


}
