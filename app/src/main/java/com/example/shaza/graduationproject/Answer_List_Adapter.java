package com.example.shaza.graduationproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mariam on 2/11/2018.
 */

public class Answer_List_Adapter extends BaseAdapter{

    private ArrayList<AnswerList> list;

    public Answer_List_Adapter(ArrayList<AnswerList> list) {
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
        View myView = layoutInflater.inflate(R.layout.item_list_answer_question, viewGroup,false);

        final AnswerList questionList = list.get(i);
        TextView textView_Answer = myView.findViewById(R.id.textView4);
        textView_Answer.setText(questionList.getAnswer());

        return myView;
    }
}
