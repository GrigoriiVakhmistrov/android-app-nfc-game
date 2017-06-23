package com.example.tzadmin.nfc_reader_writer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collection;

public class ThrowCubes extends AppCompatActivity implements View.OnClickListener {

    LinearLayout ll_cube_block1;
    LinearLayout ll_cube_block2;
    LinearLayout ll_cube_block3;

    Integer mark1, mark2, mark3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throw_cubes);


        mark1 = 0;
        mark1 = 2;
        mark1 = 3;


        ll_cube_block1 = (LinearLayout) findViewById(R.id.ll_cube_block1);
        ll_cube_block2 = (LinearLayout) findViewById(R.id.ll_cube_block2);
        ll_cube_block3 = (LinearLayout) findViewById(R.id.ll_cube_block3);

    }

    private Collection<Button> getAllByBlock(ViewGroup group) {
        Collection<Button> buttons = new ArrayList<>();

        int collectionCout = group.getChildCount();

        for (int i = 0; i < collectionCout; i++) {
            View v = group.getChildAt(i);
            if (v instanceof ViewGroup) {
                buttons.addAll(getAllByBlock((ViewGroup) v));
            } else if (v instanceof Button){
                buttons.add((Button) v);
            }
        }

        return buttons;
    }


    private void setAllClickListner(Collection<Button> buttons) {
        for (Button b : buttons) {
            b.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
