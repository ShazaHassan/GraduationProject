package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shaza.graduationproject.Activities.CertainQuestion;
import com.example.shaza.graduationproject.Database.Table.Question;
import com.example.shaza.graduationproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Question_List_Adapters extends ArrayAdapter<Question> {
    Holder holder = null;
    private ArrayList<Question> questions;
    private Context context;

    public Question_List_Adapters(Context context, ArrayList<Question> list) {
        super(context, 0, list);
        this.questions = list;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_list_questions, parent, false);
            holder = new Holder();
            listItem.setTag(holder);
        } else {
            holder = (Holder) listItem.getTag();
        }

        final Question question = getItem(position);
        holder.ques = listItem.findViewById(R.id.question);
        holder.ques.setText(question.getQuestion());
        holder.cat = listItem.findViewById(R.id.q_cat);
        holder.cat.setText(question.getCategory());
        holder.time = listItem.findViewById(R.id.q_time);
        Calendar c = Calendar.getInstance();
        Date current, publish;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        current = Calendar.getInstance().getTime();
        try {
            c.setTime(dateFormat.parse(question.getPublishDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        publish = c.getTime();
        long diff = current.getTime() - publish.getTime();
        Log.v("timeCurre", current.toString());
        Log.v("timePub", publish.toString());
        Log.v("timediff", Long.toString(diff));
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        Log.v("timemin", Long.toString(minutes));

        long hours = minutes / 60;
        Log.v("timehour", Long.toString(hours));

        long days = hours / 24;
        Log.v("timeday", Long.toString(days));


        if (days == 0) {
            if (hours == 0) {
                holder.time.setText(Long.toString(minutes) + " minutes Ago");
            } else {
                holder.time.setText(Long.toString(hours) + " hours Ago");
            }
        } else if (days < 30) {
            holder.time.setText(Long.toString(days) + " days ago");
        } else if (days == 30 || days < 30 * 2) {
            holder.time.setText("one month ago");
        } else if (days == 30 * 2 || days < 30 * 3) {
            holder.time.setText("two months ago");
        } else if (days == 30 * 3 || days < 30 * 4) {
            holder.time.setText("three months ago");
        } else if (days == 30 * 4 || days < 30 * 5) {
            holder.time.setText("four months ago");
        } else if (days == 30 * 5 || days < 30 * 6) {
            holder.time.setText("five months ago");
        } else if (days == 30 * 6 || days < 30 * 7) {
            holder.time.setText("six months ago");
        } else if (days == 30 * 7 || days < 30 * 8) {
            holder.time.setText("seven months ago");
        } else if (days == 30 * 8 || days < 30 * 9) {
            holder.time.setText("eight months ago");
        } else if (days == 30 * 9 || days < 30 * 10) {
            holder.time.setText("nine months ago");
        } else if (days == 30 * 10 || days < 30 * 11) {
            holder.time.setText("ten months ago");
        } else if (days == 30 * 11 || days < 30 * 12) {
            holder.time.setText("eleven month ago");
        } else if (days == 30 * 12 || days < 30 * 13) {
            holder.time.setText("one year ago");
        }
        holder.creator = listItem.findViewById(R.id.owner_question);
        holder.creator.setText(question.getUserName());
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent answerPage = new Intent(getContext(), CertainQuestion.class);
                answerPage.putExtra("id", question.getQuestionID());
                getContext().startActivity(answerPage);
            }
        });
        return listItem;
    }

    static class Holder {
        TextView ques, cat, time, creator;
    }
}
