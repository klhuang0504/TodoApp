package com.example.peter.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by peter on 2016/3/31.
 */
public class TargetActionMenuActivity extends Fragment {

//    private LinearLayout target_action_menu;
//    private TargetDAO targetDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.target_action_menu);

//        targetDAO = new TargetDAO(getApplicationContext());

//        processViews();

//        LinearLayout.LayoutParams layout =
//                new LinearLayout.LayoutParams(300, 150);
//        layout.setMargins(6, 6, 6, 6);
//
//        Button doButton = new Button(this);
//        doButton.setText("執行");
//        doButton.setLayoutParams(layout);
//
//        Button editButton = new Button(this);
//        editButton.setText("編輯");
//        editButton.setLayoutParams(layout);
//
//        Button cancelbutton = new Button(this);
//        cancelbutton.setText("取消");
//        cancelbutton.setLayoutParams(layout);
//
//
//        target_action_menu.addView(doButton);
//        target_action_menu.addView(editButton);
//        target_action_menu.addView(cancelbutton);

    }

//    private void processViews() {
//        target_action_menu = (LinearLayout) findViewById(R.id.targetActionMenu);
//    }

    public void processGoodTarget(View view) {
//        Toast.makeText(this, "執行Target", Toast.LENGTH_LONG).show();
//        startActivityForResult(
//                new Intent(this, TargetActionMenuActivity.class), 0);
//        setResult(Activity.RESULT_OK);
//        finish();
    }

    public void cancel(View view){
//        finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.target_action_menu, container,
                false);
//        target_action_menu = (LinearLayout) view.findViewById(R.id.targetActionMenu);
        return view;
    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        String title = getArguments().getString("title");
//        return new AlertDialog.Builder(getActivity()).setIcon(R.drawable.icon).setTitle(title).set
//
//                .setPositiveButton("是", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
////                        ((MainActivity) getActivity()).doPositiveClick();
//                    }
//                }).setNegativeButton("不", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
////                        ((MainActivity) getActivity()).doNegativeClick();
//                    }
//                }).create();
//    }
//}

}

