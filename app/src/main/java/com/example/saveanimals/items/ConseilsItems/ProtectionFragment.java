package com.example.saveanimals.items.ConseilsItems;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.saveanimals.R;

public class ProtectionFragment extends Fragment {
    TextView detailsText ;
    LinearLayout layout ;
    CardView click;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_protection, container, false);



        //advantages fragment hooks:
        layout = v.findViewById(R.id.layout);
        detailsText = v.findViewById(R.id.details);
        click = v.findViewById(R.id.click);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);


        //set OnClick Listener
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = (detailsText.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;

                TransitionManager.beginDelayedTransition(layout,new AutoTransition());
                detailsText.setVisibility(x);
            }
        });




        return v;
}



    }