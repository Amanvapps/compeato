package com.appinnovates.campeat.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.services.AdService.Option;
import com.appinnovates.campeat.services.AdService.OptionService;
import com.appinnovates.campeat.services.AdService.OptionsDelegate;
import com.appinnovates.campeat.services.AdService.Question;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Question> questions;
    private OptionsAdapter adapter;
    private boolean isSurvey = false;
    public QuestionAdapter(Context context, ArrayList<Question> questions,boolean isSurvey) {
        this.context = context;
        this.questions = questions;
        this.isSurvey = isSurvey;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Question question = questions.get(position);
        holder.txtQuestion.setText(question.question);
        int num = position+1;
        String hint = context.getResources().getString(R.string.quiz) + " " + String.valueOf(num);
        holder.txtQuiz.setText(hint);
        if (isSurvey){
            holder.optional_layout.setVisibility(question.isShortAnswer ? View.VISIBLE : View.GONE);
        }

        if (question.optionList.size() == 0){
            new OptionService().options(question.object, new OptionsDelegate() {
                @Override
                public void options(ArrayList<Option> optionList) {
                    question.optionList.clear();
                    question.optionList.addAll(optionList);
                    adapter = new OptionsAdapter(context,question.optionList);
                    LinearLayoutManager manager = new LinearLayoutManager(context);
                    holder.recyclerView.setLayoutManager(manager);
                    holder.recyclerView.setAdapter(adapter);
                }

                @Override
                public void failure(String message) {

                }
            });
        }else{
            adapter = new OptionsAdapter(context,question.optionList);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            holder.recyclerView.setLayoutManager(manager);
            holder.recyclerView.setAdapter(adapter);
        }

        holder.edtOptional.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                question.answer = String.valueOf(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtQuiz,txtQuestion;
        RecyclerView recyclerView;
        LinearLayout optional_layout;
        EditText edtOptional;
        public ViewHolder(View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txt_question);
            txtQuiz = itemView.findViewById(R.id.txt_quiz);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            optional_layout = itemView.findViewById(R.id.optional_layout);
            edtOptional = itemView.findViewById(R.id.edt_optional);
        }
    }
}
