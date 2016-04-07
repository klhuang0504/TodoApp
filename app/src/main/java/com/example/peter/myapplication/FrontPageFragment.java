package com.example.peter.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.List;

/**
 * Created by peter on 2016/3/29.
 */
public class FrontPageFragment extends Fragment {

    private TargetDAO targetDAO;
    private List<TargetEntity> goodTargetList;
    private UserDAO userDAO;
    private UserEntity userEntity;
    private LinearLayout addTodoTaskLayout, addTargetLayout;
    private EditText addTodoTaskEditText, targetNameEditText;
    private RelativeLayout floatingActionsMenuLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetDAO = new TargetDAO(getActivity());
        userDAO = new UserDAO(getActivity());

        Bundle bundle = getArguments();
        userEntity = (UserEntity) bundle.getSerializable("userEntity");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.front_page_layout, container,
                false);

        addTodoTaskLayout = (LinearLayout) view.findViewById(R.id.add_todo_task_layout);
        addTargetLayout = (LinearLayout) view.findViewById(R.id.add_target_layout);

        addTodoTaskEditText = (EditText) view.findViewById(R.id.addTodoTaskEditText);
        targetNameEditText = (EditText) view.findViewById(R.id.targetNameEt);

        floatingActionsMenuLayout = (RelativeLayout)view.findViewById(R.id.floatingActionsMenuLayout);

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
        menuMultipleActions.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
//                floatingActionsMenuLayout.setBackgroundColor(Color.argb(55, 255, 255, 255));
                floatingActionsMenuLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.semi_transparent_background));
            }

            @Override
            public void onMenuCollapsed() {
//                floatingActionsMenuLayout.setBackgroundColor(Color.argb(0, 0, 255, 0));
                floatingActionsMenuLayout.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.transparent_background));

            }
        });

        final FloatingActionButton addTargetButton = (FloatingActionButton) view.findViewById(R.id.add_target);
        addTargetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addTargetButton.setTitle("Action A clicked");
                menuMultipleActions.collapse();
//                floatingActionsMenuLayout.setBackgroundColor(Color.argb(55, 0, 255, 0));
                addTargetLayout.setVisibility(View.VISIBLE);
                targetNameEditText.requestFocus();
                ((InputMethodManager) getActivity().getSystemService(FrontPageFragment.this.getActivity().INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                targetNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        addTargetLayout.setVisibility(View.INVISIBLE);
                        targetNameEditText.setText("");
                        Toast.makeText(FrontPageFragment.this.getActivity(), targetNameEditText.getText(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        });

        final View addTodoTaskButton = view.findViewById(R.id.add_todo_task);
        addTodoTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuMultipleActions.collapse();
//                floatingActionsMenuLayout.setBackgroundColor(Color.argb(55, 0, 255, 0));
                addTodoTaskLayout.setVisibility(View.VISIBLE);
                addTodoTaskEditText.requestFocus();
                ((InputMethodManager) getActivity().getSystemService(FrontPageFragment.this.getActivity().INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                addTodoTaskEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        addTodoTaskLayout.setVisibility(View.INVISIBLE);
                        addTodoTaskEditText.setText("");
                        Toast.makeText(FrontPageFragment.this.getActivity(), addTodoTaskEditText.getText(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        });

        return view;
    }
}
