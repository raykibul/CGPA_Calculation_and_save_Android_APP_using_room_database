package cgpakoto.com.cgpa;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

@Database(entities = {Course.class,Semister.class},version = 6, exportSchema = false)
public abstract class CgpaDatabase extends RoomDatabase {

    public abstract CourseDao courseDao();

    public abstract SemisterDao semisterDao();

    private static volatile CgpaDatabase INSTANCE;

    static CgpaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CgpaDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CgpaDatabase.class, "CGPADATABASE")
                                .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback CGPAdatabasecallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final SemisterDao mDao;

        PopulateDbAsync(CgpaDatabase db) {
            mDao = db.semisterDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {


            return null;
        }
    }
}