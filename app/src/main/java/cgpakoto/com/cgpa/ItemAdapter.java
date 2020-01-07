package cgpakoto.com.cgpa;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.viewHolder> {
    List<Course> mycourse=new ArrayList<>();
    OnCourseClickListener onCourseClickListener;

    public ItemAdapter(List<Course> mycourse, OnCourseClickListener onCourseClickListener) {
        this.mycourse = mycourse;
        this.onCourseClickListener = onCourseClickListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,parent,false);
      return new viewHolder(view,onCourseClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Course temp=mycourse.get(position);
        int id= position+1;

        holder.slno.setText("Slno: "+id+"");
        holder.gpa.setText("GPA: "+temp.getGpa()+"");
        holder.credit.setText("Credit: "+temp.getCredit()+"");


    }

    @Override
    public int getItemCount() {
        if (mycourse.isEmpty()){
            return 0;
        }
        else
        {
            return mycourse.size();
        }

    }


    public class  viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView slno,gpa,credit;
         OnCourseClickListener onCourseClickListener;

        public viewHolder(View itemView,OnCourseClickListener onCourseClickListener) {
            super(itemView);

            slno=itemView.findViewById(R.id.slnoid);
            gpa=itemView.findViewById(R.id.gpaid);
            credit=itemView.findViewById(R.id.creditid);
            this.onCourseClickListener=onCourseClickListener;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

          onCourseClickListener.onCourseClick(getAdapterPosition());
        }
    }

    public interface OnCourseClickListener {
        void onCourseClick(int  position);

    }
}
