package cgpakoto.com.cgpa.Activities;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import cgpakoto.com.cgpa.AdapterSemister;
import cgpakoto.com.cgpa.CgpaViewModel;
import cgpakoto.com.cgpa.Course;
import cgpakoto.com.cgpa.R;
import cgpakoto.com.cgpa.Semister;

public class Home extends AppCompatActivity implements AdapterSemister.OnSemisterClickListener {
     RecyclerView recyclerView;
     AdapterSemister myAdapter;
     Button calculateButton;
     private CgpaViewModel model;
     TextView finalCgpa;
     List<Semister> mysemisters=new ArrayList<>();
    List<Course> mycourses=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        model= ViewModelProviders.of(this).get(CgpaViewModel.class);
        recyclerView=findViewById(R.id.semisterRecycler);
        calculateButton=findViewById(R.id.button);
        finalCgpa=findViewById(R.id.finalcgpa);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter=new AdapterSemister();
        recyclerView.setAdapter(myAdapter);
        AdView adView;
        adView=findViewById(R.id.adVieww);

        AdRequest adRequest=new AdRequest.Builder().build();

        adView.loadAd(adRequest);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT
                |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                build_alert(viewHolder.getAdapterPosition());


            }
        }).attachToRecyclerView(recyclerView);




        model.getAllSemister().observe(this, new Observer<List<Semister>>() {
            @Override
            public void onChanged(@Nullable List<Semister> semisters) {
             // Toast.makeText(Home.this, semisters.size()+" ", Toast.LENGTH_SHORT).show();

                myAdapter.setSemister(semisters,Home.this);
                mysemisters=semisters;

                if (mysemisters!=null){
                    setFinalCgpa();
                }

            }
        });

      calculateButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(Home.this, MainActivity.class);
              intent.putExtra("SEMISTERNAME","");
              startActivity(intent);
          }
      });

    }

    private void build_alert(final int adapterPosition) {
        int serial=adapterPosition+1;

        new AlertDialog.Builder(this)
                .setTitle("Do You Want To Delete the Semister?")
                .setMessage("Serial: "+serial)
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Semister temppp=mysemisters.get(adapterPosition);
                        mysemisters.remove(adapterPosition);
                        myAdapter.notifyDataSetChanged();
                        setFinalCgpa();
                        model.deleteSemister(temppp.getSemister_name());

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myAdapter.notifyDataSetChanged();
                    }
                })
                .show();


    }



    @Override
    public void OnSemisterClick(int position) {

        Semister temp=mysemisters.get(position);

         Intent intent=new Intent(Home.this,MainActivity.class);
        // Toast.makeText(this, temp.getSemister_name(), Toast.LENGTH_SHORT).show();
         intent.putExtra("SEMISTERNAME",temp.getSemister_name());
         startActivity(intent);



    }

    void setFinalCgpa(){

        double totalcredit=0.00;
        double cgpa=0.00;
        double multiply=0.00;

        for (int i=0;i<mysemisters.size();i++){
            Semister temm=mysemisters.get(i);

             totalcredit+=temm.getTotalcredit();
             multiply+=(temm.getCgpa()*temm.getTotalcredit());

             cgpa=multiply/totalcredit;

            finalCgpa.setText( String.format("FINAL CGPA: %.2f",cgpa));

        }
    }

    @Override
    public void onBackPressed() {
        finish();

    }
}
