package com.example.shaza.graduationproject.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shaza.graduationproject.TemplateForAdapter.QuestionList;
import com.example.shaza.graduationproject.R;

import java.util.ArrayList;

/**
 * Created by Mariam on 2/11/2018.
 */

public class Question_List_Adapters extends BaseAdapter {
    private ArrayList <QuestionList> list;

    public Question_List_Adapters(ArrayList<QuestionList> list) { //Arraylist constructor
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View myView = layoutInflater.inflate(R.layout.item_list_questions, viewGroup,false);

        final QuestionList questionList = list.get(i);
        TextView textView_Question = myView.findViewById(R.id.question);
        TextView textView_Categeory = myView.findViewById(R.id.Qcat);
        TextView textView_Time = myView.findViewById(R.id.Qtime);

        textView_Categeory.setText(questionList.getCategoery());
        textView_Question.setText(questionList.getQuestion());
        textView_Time.setText(questionList.getTime());



        return myView;
    }
}
