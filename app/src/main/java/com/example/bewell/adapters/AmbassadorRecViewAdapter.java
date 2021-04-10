package com.example.bewell.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bewell.R;
import com.example.bewell.models.Exercise;
import com.example.bewell.models.User;
import com.google.android.material.textview.MaterialTextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AmbassadorRecViewAdapter extends RecyclerView.Adapter<AmbassadorRecViewAdapter.ViewHolder> {
    private ArrayList<User> ambassadors = new ArrayList<>();
    private AmbassadorRecViewAdapter.OnAmbassadorClickListener onAmbassadorClickListener;

    public AmbassadorRecViewAdapter() {
    }

    public AmbassadorRecViewAdapter(AmbassadorRecViewAdapter.OnAmbassadorClickListener onAmbassadorClickListener) {
        this.onAmbassadorClickListener = onAmbassadorClickListener;
    }

    @NonNull
    @Override
    public AmbassadorRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_individual_ambassador, parent, false);
        AmbassadorRecViewAdapter.ViewHolder holder = new AmbassadorRecViewAdapter.ViewHolder(view, onAmbassadorClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AmbassadorRecViewAdapter.ViewHolder holder, int position) {
        User userRowAt  = ambassadors.get(position);
        holder.usernamAMbassador.setText(userRowAt.getEmpId());

    }

    @Override
    public int getItemCount() {
        return ambassadors.size();

    }

    public void setAmbassadors(ArrayList<User> ambassadors) {
        this.ambassadors = ambassadors;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView usernamAMbassador;

        AmbassadorRecViewAdapter.OnAmbassadorClickListener onAmbassadorClickListener;

        public ViewHolder(@NonNull View itemView, AmbassadorRecViewAdapter.OnAmbassadorClickListener onAmbassadorClickListener) {
            super(itemView);
            usernamAMbassador  =  itemView.findViewById(R.id.userNameAmbassador);

            this.onAmbassadorClickListener = onAmbassadorClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.v("Something", "It clicked");
            onAmbassadorClickListener.onAmbassadorClick(getAdapterPosition());

        }
    }

    public interface OnAmbassadorClickListener {
        void onAmbassadorClick(int position);
    }


}
