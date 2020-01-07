package cgpakoto.com.cgpa;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SemisterDao {
    @Insert
    long InsertSemister(Semister semister);


    @Update
    void UpdateSemister(Semister semister);

    @Query("Delete From semister where semister_name like:seminame")
    void DeleteSemister(String seminame);

    @Query("Select * from Semister ORDER BY id ASC ")
    LiveData<List<Semister>> GetAllSemisters();




}
