package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.Models.Group;
import com.example.tzadmin.nfc_reader_writer.Models.User;

import java.util.ArrayList;
import java.util.Collection;

public class TeamsActivity extends AppCompatActivity implements View.OnClickListener {

    Collection<ImageButton> images;
    Group currentGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
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
        Group group = new Group();
        group.totemimage = (String) v.getTag();

        currentGroup = (Group)group.selectOneByParams();
        if(currentGroup != null)
            startActivityForResultTeam();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            String RfcId = data.getStringExtra("RfcId");
            User user = new User().selectUserByRfcId(RfcId);
            if(user != null) {
                if (user.groupid == -1) {
                    user.groupid = currentGroup.id;
                    user.update();
                    Toast.makeText(this, Message.SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, Message.USER_ALREADY_SUBSCRUBE_CLAN, Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, Message.USER_THIS_BRACER_NOT_FOUND, Toast.LENGTH_SHORT).show();
            startActivityForResultTeam();
        }
    }

    private void startActivityForResultTeam () {
        Intent intent = new Intent(this, ScanNfcActivity.class);
        intent.putExtra("imageTeam", currentGroup.totemimage);
        intent.putExtra("textTeam", currentGroup.totemname);
        startActivityForResult(intent, 200);
    }
}
