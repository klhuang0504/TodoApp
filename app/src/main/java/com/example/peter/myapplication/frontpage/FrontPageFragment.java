package com.example.peter.myapplication.frontpage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.myapplication.BackHandledFragment;
import com.example.peter.myapplication.FileUtil;
import com.example.peter.myapplication.R;
import com.example.peter.myapplication.data.TargetDAO;
import com.example.peter.myapplication.data.TargetEntity;
import com.example.peter.myapplication.data.UserDAO;
import com.example.peter.myapplication.data.UserEntity;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.util.List;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by peter on 2016/3/29.
 */
public class FrontPageFragment extends BackHandledFragment {

    private static final int SELECT_PHOTO = 0;
    private static final int START_CAMERA = 1;
    private TargetDAO targetDAO;
    private List<TargetEntity> goodTargetList;
    private UserDAO userDAO;
    private UserEntity userEntity;
    private LinearLayout addTodoTaskLayout, addTargetLayout;
    private EditText addTodoTaskEditText, targetNameEditText, pointEditText;
    private RelativeLayout floatingActionsMenuLayout;
    private Button addGoodTargetButton, addBadTargetButton, addRewardButton, tackPictureButton, selectPictureButton;
    private InputMethodManager inputMethodManager;
    private TextView userPointTextView;
    private LinearLayout addTargetPointLinearLayout, addTargetButtonLinearLayout;
    private FloatingActionsMenu menuMultipleActions;
    private TargetEntity addRewardTargetEntity;


    private boolean addTodoTaskLayoutIsVisVisible, addTargetLayoutIsVisible, floatingActionsMenuIsOpen;

    // 檔案名稱
    private String fileName;
    // 照片
    private ImageView picture;
    // 寫入外部儲存設備授權請求代碼
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 100;

    @Override
    public String getTagText() {
        return null;
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addRewardTargetEntity = new TargetEntity();
        targetDAO = new TargetDAO(getActivity());
        userDAO = new UserDAO(getActivity());

        addTodoTaskLayoutIsVisVisible = false;
        addTargetLayoutIsVisible = false;
        floatingActionsMenuIsOpen = false;


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
        addRewardTargetEntity.setTargetName(targetNameEditText.getText().toString());
        addRewardTargetEntity.setPoint(Integer.parseInt(pointEditText.getText().toString()));
        addRewardTargetEntity.setAttributes(attributes);
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
//        addTargetButtonLinearLayout = (LinearLayout) view.findViewById(R.id.addTargetButtonLinearLayout);


        addTodoTaskEditText = (EditText) view.findViewById(R.id.addTodoTaskEditText);
        targetNameEditText = (EditText) view.findViewById(R.id.targetNameEditText);
        pointEditText = (EditText) view.findViewById(R.id.pointEditText);

        userPointTextView = (TextView) view.findViewById(R.id.userPointTextView);
        userPointTextView.setText(String.valueOf(userEntity.getUserPoint()));

        picture = (ImageView) view.findViewById(R.id.picture);
        tackPictureButton = (Button) view.findViewById(R.id.tackPictureButton);

        tackPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStoragePermission();
            }
        });

        selectPictureButton = (Button) view.findViewById(R.id.selectPictureButton);

        selectPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

//        addGoodTargetButton = (Button) view.findViewById(R.id.addGoodTargetButton);
//        addBadTargetButton = (Button) view.findViewById(R.id.addBadTargetButton);
//        addRewardButton = (Button) view.findViewById(R.id.addRewardButton);

//        addGoodTargetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addTargetReward(0);
//                setAddTargetLayoutVisible(false);
//            }
//        });
//
//        addBadTargetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addTargetReward(1);
//                setAddTargetLayoutVisible(false);
//            }
//        });
//
//        addRewardButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addTargetReward(2);
//                setAddTargetLayoutVisible(false);
//            }
//        });


        floatingActionsMenuLayout = (RelativeLayout) view.findViewById(R.id.floatingActionsMenuLayout);

        menuMultipleActions = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
        menuMultipleActions.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
//                floatingActionsMenuLayout.setBackgroundColor(Color.argb(55, 255, 255, 255));
                floatingActionsMenuLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.semi_transparent_background));
                floatingActionsMenuIsOpen = true;
            }

            @Override
            public void onMenuCollapsed() {
//                floatingActionsMenuLayout.setBackgroundColor(Color.argb(0, 0, 255, 0));
                floatingActionsMenuLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent_background));
                floatingActionsMenuIsOpen = false;

            }
        });

        final FloatingActionButton addGoodTargetButton = (FloatingActionButton) view.findViewById(R.id.add_goodTarget);
        addGoodTargetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addGoodTargetButton.setTitle("Action A clicked");
                menuMultipleActions.collapse();
//                floatingActionsMenuLayout.setBackgroundColor(Color.argb(55, 0, 255, 0));
                addTargetLayout.setVisibility(View.VISIBLE);
                addRewardTargetEntity = new TargetEntity();
                addTargetLayoutIsVisible = true;
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
                pointEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        addTargetReward(0);
                        setAddTargetLayoutVisible(false);
                        return false;
                    }
                });
            }
        });

        final FloatingActionButton addBadTargetButton = (FloatingActionButton) view.findViewById(R.id.add_badTarget);
        addBadTargetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuMultipleActions.collapse();
                addTargetLayout.setVisibility(View.VISIBLE);
                addTargetLayoutIsVisible = true;
                targetNameEditText.requestFocus();
                ((InputMethodManager) getActivity().getSystemService(FrontPageFragment.this.getActivity().INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                pointEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        addTargetReward(1);
                        setAddTargetLayoutVisible(false);
                        return false;
                    }
                });
            }
        });

        final FloatingActionButton addRewardButton = (FloatingActionButton) view.findViewById(R.id.add_reward);
        addRewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuMultipleActions.collapse();
                addTargetLayout.setVisibility(View.VISIBLE);
                addTargetLayoutIsVisible = true;
                targetNameEditText.requestFocus();
                ((InputMethodManager) getActivity().getSystemService(FrontPageFragment.this.getActivity().INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                pointEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        addTargetReward(2);
                        setAddTargetLayoutVisible(false);
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
                addTodoTaskLayoutIsVisVisible = true;
                addTodoTaskEditText.requestFocus();
                ((InputMethodManager) getActivity().getSystemService(FrontPageFragment.this.getActivity().INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                addTodoTaskEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        setAddTodoTaskLayoutVisible(false);
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

    public boolean isAddTodoTaskLayoutisVisible() {
        return addTodoTaskLayoutIsVisVisible;
    }

    public boolean isAddTargetLayoutIsVisible() {
        return addTargetLayoutIsVisible;
    }

    public boolean isFloatingActionsMenuOpen() {
        return floatingActionsMenuIsOpen;
    }

    public void setAddTodoTaskLayoutVisible(boolean visible) {
        if (visible) {
            addTodoTaskLayout.setVisibility(View.VISIBLE);

        } else {
            addTodoTaskLayout.setVisibility(View.INVISIBLE);
            addTodoTaskEditText.setText("");
        }
        this.addTodoTaskLayoutIsVisVisible = visible;
    }

    public void setAddTargetLayoutVisible(boolean visible) {
        if (visible) {
            addTargetLayout.setVisibility(View.VISIBLE);

        } else {
            addTargetLayout.setVisibility(View.INVISIBLE);
            targetNameEditText.setText("");
            pointEditText.setText("");
        }
        addTargetLayoutIsVisible = visible;
    }

    public void setFloatingActionsMenuOpen(boolean open) {
        if (open) {
            menuMultipleActions.expand();
        } else {
            menuMultipleActions.collapse();

        }
    }

    // 拍攝照片
    private void takePicture() {
        // 啟動相機元件用的Intent物件
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 照片檔案名稱
        File pictureFile = configFileName("P", ".jpg");
        Uri uri = Uri.fromFile(pictureFile);
        // 設定檔案名稱
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // 啟動相機元件
        startActivityForResult(intentCamera, 0);
    }

    private File configFileName(String prefix, String extension) {
        // 如果記事資料已經有檔案名稱
        if (addRewardTargetEntity.getPhotoFileName() != null && addRewardTargetEntity.getPhotoFileName().length() > 0) {
            fileName = addRewardTargetEntity.getPhotoFileName();
        }
        // 產生檔案名稱
        else {
            fileName = FileUtil.getUniqueFileName();
        }

        return new File(FileUtil.getExternalStorageDir(FileUtil.APP_DIR),
                prefix + fileName + extension);
    }

    // 讀取與處理寫入外部儲存設備授權請求
    private void requestStoragePermission() {
        // 如果裝置版本是6.0（包含）以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 取得授權狀態，參數是請求授權的名稱
            int hasPermission = checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            // 如果未授權
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                // 請求授權
                //     第一個參數是請求授權的名稱
                //     第二個參數是請求代碼
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
                return;
            }
        }

        // 如果裝置版本是6.0以下，
        // 或是裝置版本是6.0（包含）以上，使用者已經授權，
        // 拍攝照片
        takePicture();
    }

    // 覆寫請求授權後執行的方法
//     第一個參數是請求代碼
//     第二個參數是請求授權的名稱
//     第三個參數是請求授權的結果，PERMISSION_GRANTED或PERMISSION_DENIED
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 如果是寫入外部儲存設備授權請求
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            // 如果在授權請求選擇「允許」
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 拍攝照片
                takePicture();
            }
            // 如果在授權請求選擇「拒絕」
            else {
                // 顯示沒有授權的訊息
                Toast.makeText(FrontPageFragment.this.getActivity(), R.string.write_external_storage_denied,
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        File file = configFileName("P", ".jpg");

        // 如果照片檔案存在
        if (file.exists()) {
            // 顯示照片元件
            picture.setVisibility(View.VISIBLE);
            // 設定照片
            FileUtil.fileToImageView(file.getAbsolutePath(), picture);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                // 照像
                case START_CAMERA:
                    // 設定照片檔案名稱
                    addRewardTargetEntity.setPhotoFileName(fileName);

                    break;
                case SELECT_PHOTO:
                    Uri pickedImage = data.getData();
                    // Let's read picked image path using content resolver
                    String[] filePath = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();
                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
                    picture.setImageBitmap(bitmap);
                    picture.setVisibility(View.VISIBLE);

                    // Do something with the bitmap


                    // At the end remember to close the cursor or you will end with the RuntimeException!
                    cursor.close();
                    break;
            }
        }
    }

}
