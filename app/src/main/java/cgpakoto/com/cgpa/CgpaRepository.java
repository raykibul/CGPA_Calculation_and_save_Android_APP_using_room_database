package cgpakoto.com.cgpa;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CgpaRepository {

   private CourseDao courseDao;
   private  SemisterDao semisterDao;
   //public static int semisterid=0;

   private  List<Course>mAllCourse;
   private LiveData<List<Semister>>mAllSemister;

    public CgpaRepository(Application application) {
        CgpaDatabase db=CgpaDatabase.getDatabase(application);
        courseDao=db.courseDao();
        semisterDao=db.semisterDao();
        mAllSemister=semisterDao.GetAllSemisters();

    }

    LiveData<List<Semister>>getmAllSemister(){
        return mAllSemister;
    }

    List<Course> getmAllCourse(String name){
        List<Course>mycourse=new ArrayList<>();
        try {
           mycourse= new myasynctask(courseDao).execute(name).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return mycourse;

    }
    public void DelteteCourse(Course course){

        new deleteCourse(courseDao).execute(course);
    }

    public void DeleteAllcourseofSemister(String name){

        new delteallcourse(courseDao).execute(name);
    }

    public void DelteSemister(String  name){

        new deltesemister(semisterDao).execute(name);
        DeleteAllcourseofSemister(name);

    }

    public void InsertCourse(Course course){
        new insertAsyncTask(courseDao).execute(course);
    }

    public void InsertSemister(Semister semister){
       new  insertTask(semisterDao).execute(semister);
    }

    public  void UpdateSemister(Semister semister){
        new updatesemister(semisterDao).execute(semister);
    }









    public static class myasynctask extends AsyncTask<String, Void, List<Course>> {


        private  CourseDao mycoursedao;
        private  myasynctask(CourseDao dao){
            mycoursedao=dao;
        }

        @Override
        protected List<Course> doInBackground(String... strings) {
            List<Course>mycourses= mycoursedao.GetCourseById(strings[0]);
            return mycourses;
        }

        @Override
        protected void onPostExecute(List<Course> courses) {
            super.onPostExecute(courses);
        }


    }

    private static class delteallcourse extends AsyncTask<String, Void, Void> {

        private CourseDao mAsyncTaskDao;

        delteallcourse(CourseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.DeleteCourseOfSemister(params[0]);
            return null;
        }
    }

    private static class deltesemister extends AsyncTask<String, Void, Void> {

        private SemisterDao mAsyncTaskDao;

        deltesemister(SemisterDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.DeleteSemister(params[0]);
            return null;
        }
    }


    private static class updatesemister extends AsyncTask<Semister, Void, Void> {

        private SemisterDao mAsyncTaskDao;

        updatesemister(SemisterDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Semister... params) {
            mAsyncTaskDao.UpdateSemister(params[0]);
            return null;
        }
    }




    private static class insertAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao mAsyncTaskDao;

        insertAsyncTask(CourseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
             mAsyncTaskDao.InsertCourse(params[0]);
            return null;
        }
    }

    private static class insertTask extends AsyncTask<Semister, Void, Void> {

        private SemisterDao mAsyncTaskDao;

        insertTask(SemisterDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Semister... params) {
            mAsyncTaskDao.InsertSemister(params[0]);
            return null;
        }
    }

    private static class deleteCourse extends AsyncTask<Course, Void, Void> {

        private CourseDao mAsyncTaskDao;

        deleteCourse(CourseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mAsyncTaskDao.DeleteCourse(params[0]);
            return null;
        }
    }
}
