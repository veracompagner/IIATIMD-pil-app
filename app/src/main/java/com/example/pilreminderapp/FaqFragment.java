package com.example.pilreminderapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FaqFragment extends Fragment {

    Context context1;

    private RecyclerView recyclerView;
    public FaqFragment(Context ct1){
        context1 = ct1;
    }

    //hard code
    String s1[];
    String s2[];


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_faq, container, false);

        recyclerView = view1.findViewById(R.id.questionRecyclerView);
        //hard code ophalen van de faq (die moet vanaf een database komen nog)
        s1 = getResources().getStringArray(R.array.question_names);
        s2 = getResources().getStringArray(R.array.answer_names);


        QuestionAdapter questionAdapter = new QuestionAdapter(context1, s1, s2);
        recyclerView.setAdapter(questionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context1));
        return view1;



    }


}