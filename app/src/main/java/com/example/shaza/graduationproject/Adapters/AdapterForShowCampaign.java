package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shaza.graduationproject.Database.Table.RewardCampaign;
import com.example.shaza.graduationproject.R;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shaza Hassan on 24-Jan-18.
 */

public class AdapterForShowCampaign extends ArrayAdapter<RewardCampaign> {
    ArrayList<RewardCampaign> imgAndTexts = new ArrayList<>();
    private Context context;
    private int colorResource;
    ImageView myImage;
    private Date startDate, currentDate, endDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private String sDate, cDate, eDate;
    private Calendar c = Calendar.getInstance();

    public AdapterForShowCampaign(Context context, ArrayList<RewardCampaign> imgAndTexts) {
        super(context, 0, imgAndTexts);
        this.imgAndTexts = imgAndTexts;
        this.context = context;
        this.colorResource = R.color.white;
    }

    public AdapterForShowCampaign(Context context, ArrayList<RewardCampaign> imgAndTexts, int colorResource) {
        super(context, 0, imgAndTexts);
        this.imgAndTexts = imgAndTexts;
        this.context = context;
        this.colorResource = colorResource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.campaign_short_view, parent, false);
        }
        RewardCampaign imgAndText = getItem(position);
        currentDate = Calendar.getInstance().getTime();
        cDate = dateFormat.format(currentDate);

        Log.v("Addate", cDate);
        c.add(Calendar.DATE, 7);
        Log.v("Addate", c.getTime().toString());
        eDate = imgAndText.getEndDate();
        try {
            c.setTime(dateFormat.parse(eDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        endDate = c.getTime();
        long diff = endDate.getTime() - currentDate.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        Log.v("date", Long.toString(days));


        myImage = listItemView
                .findViewById(R.id.image_of_campaign_in_reward_home_page);
        String imgURI = imgAndText.getCampaign_Image();
        new DownloadImage().execute(imgURI);


        TextView description = listItemView
                .findViewById(R.id.description_for_reward_campaign_home_page);
        description.setMinLines(2);
        description.setText("Heighlight of campaign is : " + imgAndText.getHeighlight() + "\n" +
                "Vision of this campaign : " + imgAndText.getVision() + "\n" +
                "Offers for funded : " + imgAndText.getOffers() + "\n" +
                "Team helps in campaign : " + imgAndText.getHelperTeam());

        TextView campaignName = listItemView.findViewById(R.id.campaign_name);
        campaignName.setText(imgAndText.getName());

        TextView daysLeft = listItemView.findViewById(R.id.days_left);
        daysLeft.setText(Long.toString(days) + " Days left");

        TextView need = listItemView.findViewById(R.id.need);
        need.setText("Need: " + (Integer.parseInt(imgAndText.getNeededMoney()) - Integer.parseInt(imgAndText.getFundedMoney()) + " $"));

        TextView cat = listItemView.findViewById(R.id.campaign_category_short_view);
        cat.setText(imgAndText.getCategory());

        int percentage = (int) ((Float.parseFloat(imgAndText.getFundedMoney()) / Float.parseFloat(imgAndText.getNeededMoney())) * 100);
        ProgressBar showPercentage = listItemView.
                findViewById(R.id.progress_bar_for_reward_campaign_home_page);
        showPercentage.setMax(100);
        showPercentage.setProgress(percentage);

        TextView percentageView = (TextView) listItemView.findViewById(R.id.percentage);
        percentageView.setText(percentage + "%");

//        listItemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent detailsPage = new Intent(context, Campaign_info_for_funded.class);
//                detailsPage.putExtra("id", position);
//                context.startActivity(detailsPage);
//            }
//        });
        View setColorBackground = listItemView.findViewById(R.id.short_view_to_show_camp);
        int color = ContextCompat.getColor(getContext(), colorResource);
        setColorBackground.setBackgroundColor(color);
        return listItemView;
    }

    void displayImage(Bitmap bitmap) {
        myImage.setImageBitmap(bitmap);
    }

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        private Exception exception;

        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                return bmp;
            } catch (Exception e) {
                this.exception = e;

                return null;
            } finally {
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            // TODO: check this.exception
            // TODO: do something with the feed
            super.onPostExecute(bitmap);
            displayImage(bitmap);
        }
    }
}
