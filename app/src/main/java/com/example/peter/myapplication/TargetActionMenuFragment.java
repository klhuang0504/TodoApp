package com.example.peter.myapplication;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

/**
 * Created by peter on 2016/3/31.
 */
public class TargetActionMenuFragment extends DialogFragment {

    private UserDAO userDAO;
    private UserEntity userEntity;
    private TargetEntity targetEntity;
    private Button executeButton, cancelButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDAO = new UserDAO(getActivity());

        Bundle bundle = getArguments();
        userEntity = (UserEntity) bundle.getSerializable("userEntity");
        targetEntity = (TargetEntity) bundle.getSerializable("targetEntity");

    }

    public void processGoodTarget() {
        userEntity.setUserPoint(userEntity.getUserPoint() + targetEntity.getPoint());
        userDAO.update(userEntity);
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

