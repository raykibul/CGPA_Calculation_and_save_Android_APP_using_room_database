package cgpakoto.com.cgpa;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterSemister extends RecyclerView.Adapter<AdapterSemister.viewHolder> {
     List <Semister> mysemister=new ArrayList<>();
     OnSemisterClickListener onSemisterClickListener;


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.semisteritem,parent,false);
       return new viewHolder(view,onSemisterClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Semister semister=mysemister.get(position);

        holder.semistername.setText(semister.getSemister_name());
        holder.cgpatext.setText(String.format("CGPA: %.2f",semister.getCgpa()));

    }

    @Override
    public int getItemCount() {
        if (mysemister.isEmpty()){
            return 0;
        }
        else
            return mysemister.size();

    }

  public void setSemister(List<Semister> semister,OnSemisterClickListener onSemisterClickListener){
        this.onSemisterClickListener=onSemisterClickListener;
        this.mysemister=semister;
        notifyDataSetChanged();
  }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnSemisterClickListener onSemisterClickListener;
         TextView semistername,cgpatext;

        public viewHolder(View itemView,OnSemisterClickListener onSemisterClickListener) {
            super(itemView);
            semistername=itemView.findViewById(R.id.semisternameitemid);
            this.onSemisterClickListener=onSemisterClickListener;
            cgpatext=itemView.findViewById(R.id.CGPAID);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onSemisterClickListener.OnSemisterClick(getAdapterPosition());
        }
    }

    public  interface OnSemisterClickListener {
        void OnSemisterClick(int position);
    }
}
