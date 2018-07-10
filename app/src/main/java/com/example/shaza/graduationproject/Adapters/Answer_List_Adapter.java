package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shaza.graduationproject.Database.Table.Answer;
import com.example.shaza.graduationproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Answer_List_Adapter extends ArrayAdapter<Answer> {

    private Context context;
    private ArrayList<Answer> answers;

    public Answer_List_Adapter(Context context, ArrayList<Answer> answers) {
        super(context, 0, answers);
        this.context = context;
        this.answers = answers;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.answer_item, parent, false);
        }
        final Answer answer = getItem(position);

        TextView answerTextView = listItem.findViewById(R.id.answer);
        answerTextView.setText(answer.getAnswer());
        TextView time = listItem.findViewById(R.id.time_answer);
        Calendar c = Calendar.getInstance();
        Date current, publish;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        current = Calendar.getInstance().getTime();
        try {
            c.setTime(dateFormat.parse(answer.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        publish = c.getTime();
        long diff = current.getTime() - publish.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        if (days == 0) {
            if (hours == 0) {
                time.setText(Long.toString(minutes) + " minutes Ago");
            } else {
                time.setText(Long.toString(hours) + " hours Ago");
            }
        } else if (days < 30) {
            time.setText(Long.toString(days) + " days ago");
        } else if (days == 30 || days < 30 * 2) {
            time.setText("one month ago");
        } else if (days == 30 * 2 || days < 30 * 3) {
            time.setText("two months ago");
        } else if (days == 30 * 3 || days < 30 * 4) {
            time.setText("three months ago");
        } else if (days == 30 * 4 || days < 30 * 5) {
            time.setText("four months ago");
        } else if (days == 30 * 5 || days < 30 * 6) {
            time.setText("five months ago");
        } else if (days == 30 * 6 || days < 30 * 7) {
            time.setText("six months ago");
        } else if (days == 30 * 7 || days < 30 * 8) {
            time.setText("seven months ago");
        } else if (days == 30 * 8 || days < 30 * 9) {
            time.setText("eight months ago");
        } else if (days == 30 * 9 || days < 30 * 10) {
            time.setText("nine months ago");
        } else if (days == 30 * 10 || days < 30 * 11) {
            time.setText("ten months ago");
        } else if (days == 30 * 11 || days < 30 * 12) {
            time.setText("eleven month ago");
        } else if (days == 30 * 12 || days < 30 * 13) {
            time.setText("one year ago");
        }
        TextView answerPublisher = listItem.findViewById(R.id.answer_creator);
        answerPublisher.setText(answer.getUserName());


        return listItem;
    }
}
