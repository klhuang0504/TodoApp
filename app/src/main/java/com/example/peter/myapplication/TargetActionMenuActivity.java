package com.example.peter.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by peter on 2016/3/31.
 */
public class TargetActionMenuActivity extends DialogFragment {

//    private LinearLayout target_action_menu;
//    private TargetDAO targetDAO;
    private ItemDAO itemDAO;
    private Item userItem;
    private TargetEntity targetEntity;
    private Button executeButton, cancelButton;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.target_action_menu);

//        targetDAO = new TargetDAO(getActivity());
        itemDAO = new ItemDAO(getActivity());

        Bundle bundle = getArguments();
        userItem = (Item) bundle.getSerializable("userItem");
        targetEntity = (TargetEntity) bundle.getSerializable("targetEntity");

    }

//    private void processViews() {
//        target_action_menu = (LinearLayout) findViewById(R.id.targetActionMenu);
//    }

    public void processGoodTarget() {
//        startActivityForResult(
//                new Intent(this, TargetActionMenuActivity.class), 0);
//        setResult(Activity.RESULT_OK);
//        finish();
        userItem.setUserPoint(userItem.getUserPoint() + targetEntity.getPoint());
        itemDAO.update(userItem);
        dismiss();
    }

    public void cancel(View view){
        dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.target_action_menu, container,
                false);
//        target_action_menu = (LinearLayout) view.findViewById(R.id.targetActionMenu);
        executeButton = (Button) view.findViewById(R.id.execute_button);
                executeButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        processGoodTarget();
                    }
                });


        cancelButton = (Button) view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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

