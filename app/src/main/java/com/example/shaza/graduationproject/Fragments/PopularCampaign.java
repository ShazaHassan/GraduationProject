package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Adapters.AdapterForShowCampaign;
import com.example.shaza.graduationproject.Database.Table.RewardCampaign;
import com.example.shaza.graduationproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PopularCampaign extends Fragment {

//    private static final int[] img = {R.drawable.aa, R.drawable.ba148f888900f93996a2e2eabb7750a7, R.drawable.welcom_img};
//    private static final String[] texts = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa.  "
//            , "Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. "
//            , "Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices. "};
//    private static final String[] campaignName = {"Camp1", "Camp2", "Camp3"};
//    private static final String[] noOfDays = {"2 days left", "3 days left", "1 day left"};
//    private static final String[] need = {"1$", "0$", "6$"};
//    private static final int[] total = {5, 3, 12};
//    private static final int[] get = {4, 3, 6};
//    private static final String[] category = {"Cat1", "Cat2", "Cat3"};
//    private ArrayList<ImgAndText> array = new ArrayList<>();

    View rootView;
    private ArrayList<RewardCampaign> campaigns = new ArrayList<>();
    private DatabaseReference rewardTable;
    private RewardCampaign campaign;
    private TextView noCampsTextView;
    private ListView listView;
    private AdapterForShowCampaign adapter;
    public PopularCampaign() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_campaign, container, false);
        noCampsTextView = rootView.findViewById(R.id.no_camp_text_view);
        listView = rootView.findViewById(R.id.list);


        rewardTable = FirebaseDatabase.getInstance().getReference().child("Reward Campaign");
        Log.v("popular", "get camp");

        rewardTable.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("popular", "search for camp");
                campaigns.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    campaigns.add(snapshot.getValue(RewardCampaign.class));
                    Log.v("popular", dataSnapshot.getChildren().toString());
                }
                Log.v("popular", String.valueOf(campaigns.size()));
                if (campaigns.size() != 0) {
                    Log.v("popular", "camps");
                    noCampsTextView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    adapter = new AdapterForShowCampaign(getActivity(), campaigns, R.color.lightYellow);
                    listView.setAdapter(adapter);
                } else {
                    Log.v("popular", "no camp");
                    listView.setVisibility(View.GONE);
                    noCampsTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        array.clear();
//        for (int i = 0; i < img.length; i++)
//            array.add(new ImgAndText(img[i], texts[i], campaignName[i], noOfDays[i], need[i], total[i], get[i], category[i]));
//        AdapterForShowCampaign adapter = new AdapterForShowCampaign(getActivity(), array, R.color.lightYellow);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(getActivity(), Campaign_info_for_creator.class);
//                i.putExtra("id", position);
//                getActivity().startActivity(i);
//            }
//        });

        return rootView;
    }
}
