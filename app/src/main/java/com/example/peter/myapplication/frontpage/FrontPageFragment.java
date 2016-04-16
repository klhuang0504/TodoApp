package com.example.peter.myapplication.frontpage;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.myapplication.BackHandledFragment;
import com.example.peter.myapplication.R;
import com.example.peter.myapplication.data.TargetDAO;
import com.example.peter.myapplication.data.TargetEntity;
import com.example.peter.myapplication.data.UserDAO;
import com.example.peter.myapplication.data.UserEntity;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.List;

/**
 * Created by peter on 2016/3/29.
 */
public class FrontPageFragment extends BackHandledFragment {

    private TargetDAO targetDAO;
    private List<TargetEntity> goodTargetList;
    private UserDAO userDAO;
    private UserEntity userEntity;
    private LinearLayout addTodoTaskLayout, addTargetLayout;
    private EditText addTodoTaskEditText, targetNameEditText, pointEditText;
    private RelativeLayout floatingActionsMenuLayout;
    private Button addGoodTargetButton, addBadTargetButton, addRewardButton;
    private InputMethodManager inputMethodManager;
    private TextView userPointTextView;
    private LinearLayout addTargetPointLinearLayout,addTargetButtonLinearLayout;

    public enum ViewStatus_FrontPageFragment
    {
        Action01, Action02;
    }

    private CallbackInterface mCallback;
    public interface CallbackInterface
    {
        public void updateContentView_By_Fragment01(FrontPageFragment status);
    };

    @Override
    public String getTagText() {
        return (String) this.getTag();
    }

    @Override
    public boolean onBackPressedFlag() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetDAO = new TargetDAO(getActivity());
        userDAO = new UserDAO(getActivity());

        Bundle bundle = getArguments();
        userEntity = (UserEntity) bundle.getSerializable("userEntity");
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(FrontPageFragment.this.getActivity().INPUT_METHOD_SERVICE);
    }



    private void addTargetReward(int attributes) {
        if (targetNameEditText == null || targetNameEditText.getText() == null || targetNameEditText.getText().toString().trim().equals("")) {
            return;
        }
        if (pointEditText == null || pointEditText.getText() == null || pointEditText.getText().toString().trim().equals("")) {
            return;
        }
        TargetEntity targetEntity = new TargetEntity();
        targetEntity.setTargetName(targetNameEditText.getText().toString());
        targetEntity.setPoint(Integer.parseInt(pointEditText.getText().toString()));
        targetEntity.setAttributes(attributes);
        targetDAO.insert(targetEntity);
        addTargetLayout.setVisibility(View.INVISIBLE);
        targetNameEditText.setText("");
        pointEditText.setText("");
        closeKeyBoard(pointEditText);
    }

    private void closeKeyBoard(EditText editText) {
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void openKeyBoard() {
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.front_page_layout, container,
                false);

        addTodoTaskLayout = (LinearLayout) view.findViewById(R.id.add_todo_task_layout);
        addTargetLayout = (LinearLayout) view.findViewById(R.id.add_target_layout);
        addTargetPointLinearLayout = (LinearLayout) view.findViewById(R.id.addTargetPointLinearLayout);
        addTargetButtonLinearLayout = (LinearLayout) view.findViewById(R.id.addTargetButtonLinearLayout);


        addTodoTaskEditText = (EditText) view.findViewById(R.id.addTodoTaskEditText);
        targetNameEditText = (EditText) view.findViewById(R.id.targetNameEditText);
        pointEditText = (EditText) view.findViewById(R.id.pointEditText);

        userPointTextView = (TextView) view.findViewById(R.id.userPointTextView);
        userPointTextView.setText(String.valueOf(userEntity.getUserPoint()));

        addGoodTargetButton = (Button) view.findViewById(R.id.addGoodTargetButton);
        addBadTargetButton = (Button) view.findViewById(R.id.addBadTargetButton);
        addRewardButton = (Button) view.findViewById(R.id.addRewardButton);

        addGoodTargetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTargetReward(0);
            }
        });

        addBadTargetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTargetReward(1);
            }
        });

        addRewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTargetReward(2);
            }
        });


        floatingActionsMenuLayout = (RelativeLayout) view.findViewById(R.id.floatingActionsMenuLayout);

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
        menuMultipleActions.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
//                floatingActionsMenuLayout.setBackgroundColor(Color.argb(55, 255, 255, 255));
                floatingActionsMenuLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.semi_transparent_background));
            }

            @Override
            public void onMenuCollapsed() {
//                floatingActionsMenuLayout.setBackgroundColor(Color.argb(0, 0, 255, 0));
                floatingActionsMenuLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent_background));

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
//                targetNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                    @Override
//                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                        addTargetLayout.setVisibility(View.INVISIBLE);
//                        targetNameEditText.setText("");
//                        Toast.makeText(FrontPageFragment.this.getActivity(), targetNameEditText.getText(), Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                });
//                pointEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                    @Override
//                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                        addTargetLayout.setVisibility(View.INVISIBLE);
//                        pointEditText.setText("");
//                        Toast.makeText(FrontPageFragment.this.getActivity(), targetNameEditText.getText(), Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                });
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

//        final View addTodoTaskButton = view.findViewById(R.id.add_todo_task);
//        addTodoTaskButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                menuMultipleActions.collapse();
////                floatingActionsMenuLayout.setBackgroundColor(Color.argb(55, 0, 255, 0));
//                addTargetLayout.setVisibility(View.VISIBLE);
//
//                targetNameEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
//                targetNameEditText.setHint("");
//                addTargetPointLinearLayout.setVisibility(View.INVISIBLE);
//                addTargetButtonLinearLayout.setVisibility(View.INVISIBLE);
//                targetNameEditText.requestFocus();
//                ((InputMethodManager) getActivity().getSystemService(FrontPageFragment.this.getActivity().INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                addTodoTaskEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                    @Override
//                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                        addTodoTaskLayout.setVisibility(View.INVISIBLE);
//                        addTodoTaskEditText.setText("");
//                        Toast.makeText(FrontPageFragment.this.getActivity(), addTodoTaskEditText.getText(), Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                });
//            }
//        });

        addTodoTaskEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //当EditText失去焦点时，隐藏软键盘
                if (!hasFocus) {
                    closeKeyBoard(addTodoTaskEditText);
                }
            }
        });

        targetNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //当EditText失去焦点时，隐藏软键盘
                if (!hasFocus) {
                    closeKeyBoard(targetNameEditText);
                }
            }
        });

        pointEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //当EditText失去焦点时，隐藏软键盘
                if (!hasFocus) {
                    closeKeyBoard(pointEditText);
                }
            }
        });



        return view;
    }
}
