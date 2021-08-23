package com.example.pilreminderapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    //String data1[], data2[];

    private List<Faq> faqList;
    private Context context;

    public QuestionAdapter(Context context, List<Faq> faqList){
        this.context = context;
        this.faqList = faqList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.faq_card, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        //holder.text1.setText(data1[position]);
        Faq faq = faqList.get(position);
        holder.question.setText(faq.getQuestion());
        holder.answer.setText(faq.getAnswer());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ThirdActivity.class);
                intent.putExtra("data1", faq.getQuestion());
                intent.putExtra("data2", faq.getAnswer());
                context.startActivity(intent);
            }
        });
    }

    public void getAllFaqs(List<Faq> faqList){
        this.faqList = faqList;
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        TextView question, answer;
        ConstraintLayout mainLayout;

        public QuestionViewHolder(@NonNull View itemView){
            super(itemView);
            question = itemView.findViewById(R.id.questionTextView);
            answer = itemView.findViewById(R.id.answerTextView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
