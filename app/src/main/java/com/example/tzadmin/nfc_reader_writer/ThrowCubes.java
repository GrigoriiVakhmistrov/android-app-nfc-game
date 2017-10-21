package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.example.tzadmin.nfc_reader_writer.Database.Group;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collection;

public class ThrowCubes extends AppCompatActivity implements View.OnClickListener {

    Collection<ImageButton> images;
    Group currentGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throw_cubes);

        images = getAllImages((ViewGroup) findViewById(R.id.team_constraint_layout));
        subscribeOnClickListener(images);
    }

    private void subscribeOnClickListener (Collection<ImageButton> images) {
        for (ImageButton image : images) {
            image.setOnClickListener(this);
        }
    }

    private Collection<ImageButton> getAllImages(ViewGroup group) {
        Collection<ImageButton> buttons = new ArrayList<>();

        int collectionCout = group.getChildCount();

        for (int i = 0; i < collectionCout; i++) {
            View v = group.getChildAt(i);
            if (v instanceof ViewGroup) {
                buttons.addAll(getAllImages((ViewGroup) v));
            } else if (v instanceof ImageButton){
                buttons.add((ImageButton) v);
            }
        }

        return buttons;
    }

    @Override
    public void onClick(View v) {
        currentGroup = DataSupport.where("totemimage like ?", (String) v.getTag()).findFirst(Group.class);
        if(currentGroup != null) {
            Intent i = new Intent(this, CubeActivity.class);
            i.putExtra("GroupId", currentGroup.id.intValue());
            startActivity(i);
        }
    }

}
