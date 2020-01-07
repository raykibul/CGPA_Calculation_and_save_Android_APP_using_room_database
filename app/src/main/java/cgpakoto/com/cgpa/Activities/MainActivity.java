package cgpakoto.com.cgpa.Activities;

import android.app.Dialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import cgpakoto.com.cgpa.CgpaViewModel;
import cgpakoto.com.cgpa.Course;
import cgpakoto.com.cgpa.ItemAdapter;
import cgpakoto.com.cgpa.R;
import cgpakoto.com.cgpa.Semister;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnCourseClickListener {

    EditText creditext,gpatext;
    Button addbutton;
    TextView cgpatext;
    RecyclerView recyclerView;
    List<Course>mycourse=new ArrayList<>();
    LiveData<List<Course>>liveCourse;
    double cgpa=0.00;

    int counter=0;
    ItemAdapter itemAdapter;
    Dialog mydialog;
    FloatingActionButton saveButton;

    private CgpaViewModel myViewModel;
    Boolean isNew=true;
    String name;
    double totalcredit=0.00;
    double Gpa=0.00;
    boolean changed=false;
    Switch myswitch;
    Spinner myspinner;
    ArrayAdapter<CharSequence>arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activitymain);
        Initialize();


        myViewModel= ViewModelProviders.of(this).get(CgpaViewModel.class);

        myswitch.setChecked(true);

        Toast.makeText(this, "Swipe to delete Semister , Rate Our App please..", Toast.LENGTH_SHORT).show();


        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (myswitch.isChecked()){
                    gpatext.setVisibility(View.GONE);
                    myspinner.setVisibility(View.VISIBLE);
                }
                else
                {
                    gpatext.setVisibility(View.VISIBLE);
                    myspinner.setVisibility(View.GONE);
                }
            }
        });
        arrayAdapter=ArrayAdapter.createFromResource(this,R.array.options,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(arrayAdapter);

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String value=parent.getItemAtPosition(position).toString();
                Gpa= giveMeGpa(value);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

               Gpa=9.999;

            }
        });



        Intent intent=getIntent();
        String semistr=intent.getStringExtra("SEMISTERNAME");

        if (semistr.equals("")){

        }else {
            name=semistr;
            isNew=false;

          //  Toast.makeText(this, "Name" + semistr, Toast.LENGTH_SHORT).show();

            mycourse = myViewModel.getAllCourse(semistr);


            if (mycourse != null) {
                CalculateCgpa();

               // Toast.makeText(this, mycourse.size() + "size:", Toast.LENGTH_SHORT).show();
            }
        }
             RecyclerInitialize();
            mydialog = new Dialog(MainActivity.this);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNew){
                        SaveButtonclick();
                    }
                    else if (isNew!=true&&changed==true){
                        AlertAndSave();
                    }


                }
            });


        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buttonclick(v);
            }
        });

}

    private double giveMeGpa(String value) {
        switch (value){
            case "A+":
                return 4.00;

            case "A-":
                return 3.50;

            case "A":
                return 3.75;


            case "B+":
                return 3.25;

            case "B-":
                return 2.75;

            case "B":
                return 3.00;


            case "C+":
                return 2.50;

            case "C":
                return 2.25;

            case "D":
                return 2.00;


        }
        return 9.999;

    }

    private void AlertAndSave() {

        new AlertDialog.Builder(this)
                .setTitle("ATTENTION!!!")
                .setMessage("Do You Really Want To Update The Semister?")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myViewModel.deleteSemister(name);
                        Semister newsem=new Semister(returnCgpa(),name,returntotalCredit());
                        myViewModel.insertSemister(newsem);

                        for (int i = 0; i < mycourse.size(); i++) {
                            Course tempcourse = mycourse.get(i);
                            tempcourse.setSemister_name(name);
                            myViewModel.insertCourse(tempcourse);
                        }
                        Intent intent=new Intent(MainActivity.this, Home.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changed=false;
                        Intent intent=new Intent(MainActivity.this,Home.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .show();

    }

    private void SaveButtonclick() {
      mydialog.setContentView(R.layout.savesemister);
      final EditText semistername=mydialog.findViewById(R.id.semistername);
      Button saveSemister=mydialog.findViewById(R.id.saveSemisterbutton);
      Button cancellbutton=mydialog.findViewById(R.id.cancellsemister);
      mydialog.show();

        cancellbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.cancel();
            }
        });
      saveSemister.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View v) {

              name=semistername.getText().toString();

              if (name.equals("")){
                  Toast.makeText(MainActivity.this, "Please Insert A Name to save", Toast.LENGTH_SHORT).show();
              }
              else {
                   Semister temp = new Semister(returnCgpa(),name,returntotalCredit());
                   myViewModel.insertSemister(temp);
                  Toast.makeText(MainActivity.this, "Semiseter Saved", Toast.LENGTH_SHORT).show();

                  if (!mycourse.isEmpty()) {

                      for (int i = 0; i < mycourse.size(); i++) {
                          Course tempcourse = mycourse.get(i);
                              tempcourse.setSemister_name(name);
                              myViewModel.insertCourse(tempcourse);
                      }
                      mydialog.cancel();
                  }
                  Intent intent=new Intent(MainActivity.this,Home.class);
                  startActivity(intent);
                  finish();
              }

              }
          });





      //  Toast.makeText(this, "We Are Working on Saving , please keep up-to-date your app", Toast.LENGTH_SHORT).show();

    }

    private void RecyclerInitialize() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter=new ItemAdapter(mycourse,this);
        recyclerView.setAdapter(itemAdapter);

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



    }

    private void build_alert(final int position) {
        int serial=position+1;

        new AlertDialog.Builder(this)
                .setTitle("Do You Want To Delete the course?")
                .setMessage("Serial: "+serial)
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { Course temppp=mycourse.get(position);
                    mycourse.remove(position);
                    itemAdapter.notifyDataSetChanged();
                    CalculateCgpa();
                    changed=true;

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     itemAdapter.notifyDataSetChanged();
                    }
                })
                .show();

    }

    private void Initialize() {
        creditext=findViewById(R.id.editText2);
        gpatext=findViewById(R.id.editText);
        addbutton=findViewById(R.id.button2);
        cgpatext=findViewById(R.id.textView2);
        myspinner=findViewById(R.id.spinner);
        myswitch=findViewById(R.id.switchd);

        recyclerView=findViewById(R.id.recyclerView);
        saveButton=findViewById(R.id.fab);

    }

    void Buttonclick(View view){
        changed=true;

         if (myswitch.isChecked()){

             if (creditext.getText().toString().equals("")||Gpa==9.999){

                 Toast.makeText(this, "Please Insert CREDIT and Select GPA", Toast.LENGTH_SHORT).show();
             }else {
                 double credit= Double.parseDouble(creditext.getText().toString());

                 mycourse.add(new Course(credit,Gpa));
                 itemAdapter.notifyDataSetChanged();
                 CalculateCgpa();
             }


         }
         else{
             if (creditext.getText().toString().equals("")||gpatext.getText().toString().equals("")){

                 Toast.makeText(this, "Please Insert GPA & CREDIT", Toast.LENGTH_SHORT).show();

             } else{
                 double gpa=Double.parseDouble(gpatext.getText().toString());
                 double credit=Double.parseDouble(creditext.getText().toString());
                 if(gpa>4.00){
                     Toast.makeText(this, "GPA can't be greater than 4.00", Toast.LENGTH_SHORT).show();
                     return;
                 }

                 counter++;

                 mycourse.add(new Course(credit,gpa));
                 itemAdapter.notifyDataSetChanged();
                 CalculateCgpa();
                 gpatext.setText("");
             }

         }


    }
    @Override
    public void onCourseClick(int position) {

        ShowDialogue(position);



    }

    private void ShowDialogue(int position) {
        final Course temp=mycourse.get(position);

        mydialog.setContentView(R.layout.dialogueview);

        final EditText gpaa=mydialog.findViewById(R.id.editText3);
        gpaa.setText(temp.getGpa()+"");



        final EditText creditt=mydialog.findViewById(R.id.editText4);
        creditt.setText(temp.getCredit()+"");

        TextView serial=mydialog.findViewById(R.id.textView4);
        serial.setText("Editing Entry No: " +temp.getId()+"");

        TextView cancel=mydialog.findViewById(R.id.canceltext);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.cancel();
            }
        });

        TextView update=mydialog.findViewById(R.id.updatetext);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changed=true;
                temp.setGpa(Double.parseDouble(gpaa.getText().toString()));
                temp.setCredit(Double.parseDouble(creditt.getText().toString()));
                itemAdapter.notifyDataSetChanged();
                CalculateCgpa();
                mydialog.cancel();
            }
        });




        mydialog.show();

    }



    private  void CalculateCgpa(){
        double multiply=0.00;
        totalcredit=0.00;
        cgpa=0.00;

        Course temp ;
        if (mycourse.isEmpty()){
            cgpatext.setText("CGPA: 0.00");
        }
        else
        {
            for (int i=0;i<mycourse.size();i++){
                temp =mycourse.get(i);
                totalcredit+=temp.getCredit();
                multiply+=(temp.getCredit()*temp.getGpa());

            }

            cgpa=multiply/totalcredit;

            cgpatext.setText(String.format("CGPA: %.2f",cgpa));

        }

    }

    double returntotalCredit(){
        return totalcredit;
    }

    double returnCgpa(){
        return  cgpa;
    }

    @Override
    public void onBackPressed() {


        if (changed&&isNew==false){

            AlertAndSave();
        }else if (mycourse.size()>0&&isNew){
            showalert();
        }
        else
        {
            super.onBackPressed();
        }


    }

    private void showalert(){

        new AlertDialog.Builder(this)
                .setTitle("ATTENTION!!!")
                .setMessage("You have unsaved Courses . Do  you want to save now?")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SaveButtonclick();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changed=false;
                        Intent intent=new Intent(MainActivity.this,Home.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .show();

    }
}