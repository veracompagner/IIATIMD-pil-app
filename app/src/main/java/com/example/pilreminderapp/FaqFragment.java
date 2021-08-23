package com.example.pilreminderapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pilreminderapp.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FaqFragment extends Fragment {

    Context context;

    private FaqViewModel faqViewModel;

    private RecyclerView recyclerView;
    private List<Faq> faqList;
    private FaqRepository faqRepository;
    private QuestionAdapter questionAdapter;

    public FaqFragment(Context ct1) {
        context = ct1;
    }

    private TextView questionTextView;
    private TextView answerTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View faqView = inflater.inflate(R.layout.fragment_faq, container, false);

        recyclerView = faqView.findViewById(R.id.questionRecyclerView);

        questionTextView = faqView.findViewById(R.id.questionTextView);
        answerTextView = faqView.findViewById(R.id.answerTextView);

        faqRepository = new FaqRepository(getActivity().getApplication());
        faqList = new ArrayList<>();

        questionAdapter = new QuestionAdapter(getActivity().getApplication(), faqList);

        faqViewModel = new ViewModelProvider(getActivity()).get(FaqViewModel.class);
        networkRequest();
        faqViewModel.getAllFaqs().observe(getActivity(), new Observer<List<Faq>>() {
            @Override
            public void onChanged(List<Faq> faqList) {
                recyclerView.setAdapter(questionAdapter);
                questionAdapter.getAllFaqs(faqList);

            }
        });

        recyclerView.setAdapter(questionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return faqView;
    }

    private void networkRequest() {
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<List<Faq>> call = retrofitClient.laravelApi.getFaq();
        call.enqueue(new Callback<List<Faq>>() {
            @Override
            public void onResponse(Call<List<Faq>> call, Response<List<Faq>> response) {
                if (response.isSuccessful()) {
                    faqRepository.insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Faq>> call, Throwable t) {
                Toast.makeText(getActivity(), "Internet momenteel onbereikbaar. FAQ toont laatst geupdate informatie", Toast.LENGTH_LONG).show();
            }
        });
    }
}


