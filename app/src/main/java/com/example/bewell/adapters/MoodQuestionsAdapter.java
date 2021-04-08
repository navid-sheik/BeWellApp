package com.example.bewell.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bewell.R;
import com.example.bewell.models.MoodQuestion;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class MoodQuestionsAdapter extends RecyclerView.Adapter<MoodQuestionsAdapter.ViewHolder> {


    private ArrayList<MoodQuestion> questions =  new ArrayList<>();
    private MoodListener mooodLister;

    public MoodQuestionsAdapter(MoodListener mooodLister) {
        this.mooodLister = mooodLister;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mood_question, parent, false);
        MoodQuestionsAdapter.ViewHolder holder = new MoodQuestionsAdapter.ViewHolder(view, mooodLister);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MoodQuestion atRowAt  =  questions.get(position);
        holder.questionView.setText(atRowAt.getQuestion());


        if (atRowAt.isSetQuestionAnswer() ==  false && atRowAt.isAnswer() == false){
            holder.positiveAnswerBtn.setBackgroundColor(Color.GRAY);
            holder.negativeAnswerBtn.setBackgroundColor(Color.GRAY);
        }else{
            if(atRowAt.isAnswer()){
                holder.positiveAnswerBtn.setBackgroundColor(Color.RED);
                holder.negativeAnswerBtn.setBackgroundColor(Color.LTGRAY);
            }else {
                holder.positiveAnswerBtn.setBackgroundColor(Color.LTGRAY);
                holder.negativeAnswerBtn.setBackgroundColor(Color.RED);
            }
        }


    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void setQuestions(ArrayList<MoodQuestion> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {


        private MaterialTextView questionView;
        private MaterialButton positiveAnswerBtn;
        private MaterialButton negativeAnswerBtn;
        private MoodListener moodListener;


        public ViewHolder(@NonNull View itemView, MoodListener moodListener) {
            super(itemView);
            questionView =  itemView.findViewById(R.id.moodQuestion);
            positiveAnswerBtn = itemView.findViewById(R.id.positiveMoodAnswer);
            positiveAnswerBtn.setOnClickListener(buttonHolderLister);
            negativeAnswerBtn = itemView.findViewById(R.id.negativeMoodAnswer);
            negativeAnswerBtn.setOnClickListener(buttonHolderLister);
            this.moodListener =  moodListener;
        }

        public View.OnClickListener buttonHolderLister = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.positiveMoodAnswer:
                        positiveAnswerBtn.setBackgroundColor(Color.RED);
                        negativeAnswerBtn.setBackgroundColor(Color.LTGRAY);
                        moodListener.positiviteMoodOnClickButton(getAdapterPosition());
                        break;
                    case R.id.negativeMoodAnswer:
                        positiveAnswerBtn.setBackgroundColor(Color.LTGRAY);
                        negativeAnswerBtn.setBackgroundColor(Color.RED);
                        moodListener.negativeMoodOnClickButton(getAdapterPosition());
                        break;
                }
            }
        };
    }


    public interface MoodListener{
        void positiviteMoodOnClickButton (int position);
        void negativeMoodOnClickButton (int position);
    }
}
