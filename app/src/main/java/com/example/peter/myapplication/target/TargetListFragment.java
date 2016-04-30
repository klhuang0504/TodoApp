package com.example.peter.myapplication.target;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.example.peter.myapplication.FileUtil;
import com.example.peter.myapplication.R;
import com.example.peter.myapplication.data.LogDAO;
import com.example.peter.myapplication.data.LogEntity;
import com.example.peter.myapplication.data.TargetDAO;
import com.example.peter.myapplication.data.TargetEntity;
import com.example.peter.myapplication.data.UserDAO;
import com.example.peter.myapplication.data.UserEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.graphics.Color.WHITE;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by peter on 2016/3/29.
 */
public class TargetListFragment extends Fragment implements TargetSwipeAdapterCallback {

    private static final int SELECT_PHOTO = 0;
    private static final int START_CAMERA = 1;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 100;
    private static final int[] addPointattributes = {0, 3};
    private static final int[] lessPointattributes = {1, 2};
    private static final int todoAttributes = 3;




    private TargetDAO targetDAO;
    private UserDAO userDAO;
    private LogDAO logDAO;
    private UserEntity userEntity;
    private TargetEntity selectTargetEntity;

    private LinearLayout addTargetPointLinearLayout, addTargetButtonLinearLayout, photoLinearLayout, addTargetLayout, addTargetButtonActionLinearLayout, addTodoTaskLayout;

    private ListView targetlistView;
    private TextView targetDoneTextView;
    private ImageView targetDoneImageView, addTargetDoneActionImageView, addTargetDeleteActionImageView, addTargetCancelActionImageView, photoImageView;
    private EditText addTodoTaskEditText, targetNameEditText, pointEditText;


    private List<TargetEntity> targetEntityList;
    private TargetSwipeAdapter targetSwipeAdapter;

    private String fileName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData(){
        targetDAO = new TargetDAO(getActivity());
        userDAO = new UserDAO(getActivity());
        logDAO = new LogDAO(getActivity());

        userEntity = (UserEntity) getArguments().getSerializable("userEntity");
        targetEntityList = getTargetEntityList(getArguments().getInt("targetAttributes"));
        targetSwipeAdapter = new TargetSwipeAdapter(getActivity(), targetEntityList, this, getArguments().getInt("targetAttributes"));
    }

    public ArrayList<TargetEntity> getTargetEntityList(int targetAttributes) {
        if (targetAttributes == 0) {
            return targetDAO.getGoodTargetList();
        }
        if (targetAttributes == 1) {
            return targetDAO.getBadTargetList();
        }
        if (targetAttributes == 2) {
            return targetDAO.getRewardList();
        }
        if (targetAttributes == 3) {
            return targetDAO.getTodoList();
        }
        return new ArrayList<TargetEntity>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.target_list_layout, container, false);
        addTargetLayout = (LinearLayout) view.findViewById(R.id.add_target_layout);
        addTargetButtonActionLinearLayout = (LinearLayout) view.findViewById(R.id.addTargetButtonActionLinearLayout);
        photoLinearLayout = (LinearLayout) view.findViewById(R.id.photoLinearLayout);
        addTodoTaskLayout = (LinearLayout) view.findViewById(R.id.add_todo_task_layout);

        photoImageView = (ImageView) view.findViewById(R.id.photoImageView);
        addTargetDoneActionImageView = (ImageView) view.findViewById(R.id.done_add_target_button);
        addTargetDeleteActionImageView = (ImageView) view.findViewById(R.id.delete_add_target_button);
        addTargetCancelActionImageView = (ImageView) view.findViewById(R.id.cancel_add_target_button);

        targetNameEditText = (EditText) view.findViewById(R.id.targetNameEditText);
        pointEditText = (EditText) view.findViewById(R.id.pointEditText);
        addTodoTaskEditText = (EditText) view.findViewById(R.id.add_todo_task);

        targetlistView = (ListView) view.findViewById(R.id.goodTargetListView);
        targetSwipeAdapter.setMode(Attributes.Mode.Single);
        targetlistView.setAdapter(targetSwipeAdapter);

        addTargetDoneActionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(targetNameEditText.getText().toString() != null){
                    selectTargetEntity.setTargetName(targetNameEditText.getText().toString());
                }else{
//                    Toast.makeText(getActivity(), "請輸入標題", Toast.LENGTH_SHORT).show();
                }
                if(pointEditText.getText().toString() != null){
                    selectTargetEntity.setPoint(Integer.valueOf(pointEditText.getText().toString()));
                }
                targetDAO.update(selectTargetEntity);
                addTodoTaskLayout.setVisibility(View.INVISIBLE);
                addTargetLayout.setVisibility(View.INVISIBLE);
                photoLinearLayout.setVisibility(View.INVISIBLE);
//                addTargetDeleteActionImageView.setVisibility(View.INVISIBLE);
                targetNameEditText.setText("");
                pointEditText.setText("");
            }
        });

        addTargetCancelActionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTargetLayout.setVisibility(View.INVISIBLE);
                targetNameEditText.setText("");
                pointEditText.setText("");
                photoLinearLayout.setVisibility(View.INVISIBLE);
            }
        });

        addTargetDeleteActionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (targetDAO.delete(selectTargetEntity.getId())) {
                    Toast.makeText(getActivity(), "已刪除： " + selectTargetEntity.getTargetName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "刪除失敗", Toast.LENGTH_SHORT).show();
                }
                targetEntityList.remove(selectTargetEntity);
                targetSwipeAdapter.notifyDataSetChanged();
                addTargetLayout.setVisibility(View.INVISIBLE);
                addTargetDeleteActionImageView.setVisibility(View.INVISIBLE);
//                addTargetButtonActionLinearLayout.setVisibility(View.INVISIBLE);
                targetNameEditText.setText("");
                pointEditText.setText("");
            }
        });
        return view;
    }

    @Override
    public void onClickLaterButtonMethodCallback(TargetEntity targetEntity) {
        targetEntityList.remove(targetEntity);
        targetSwipeAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClickEditButtonCallBack(TargetEntity targetEntity) {
        selectTargetEntity = targetEntity;
        addTargetDeleteActionImageView.setVisibility(View.VISIBLE);
        if(targetEntity.getAttributes() == todoAttributes){
            addTodoTaskLayout.setVisibility(View.VISIBLE);
            addTodoTaskEditText.setText(selectTargetEntity.getTargetName());
        }else{
            targetNameEditText.setText(selectTargetEntity.getTargetName());
            pointEditText.setText(String.valueOf(selectTargetEntity.getPoint()));
            addTargetLayout.setVisibility(View.VISIBLE);
//            addTargetDeleteActionImageView.setVisibility(View.VISIBLE);
            if(targetEntity.getPhotoFileName() != null){
                File file = new File(FileUtil.getExternalStorageDir(FileUtil.APP_DIR),
                        "P" + targetEntity.getPhotoFileName() + ".jpg");
                FileUtil.fileToImageView(file.getAbsolutePath(), photoImageView);
                photoLinearLayout.setVisibility(View.VISIBLE);
                photoImageView.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onClickUndoButtonCallBack(TargetEntity targetEntity) {
        int attributes = targetEntity.getAttributes();
        if (Arrays.asList(lessPointattributes).contains(attributes)) {
            userEntity.setUserPoint(userEntity.getUserPoint() + targetEntity.getPoint());
        } else if (Arrays.asList(addPointattributes).contains(attributes)){
            userEntity.setUserPoint(userEntity.getUserPoint() - targetEntity.getPoint());
        }
        userDAO.update(userEntity);

        ArrayList<LogEntity> logEntityArrayList = new ArrayList<LogEntity>();
        logEntityArrayList = logDAO.getListByEntityId((int)targetEntity.getId());
        LogEntity logEntity = new LogEntity();
        logEntity = logEntityArrayList.get(0);
        logDAO.delete(logEntity.getId());
    }

    @Override
    public void onSwipeLayoutOpenCallback(SwipeLayout swipeLayout) {
        targetDoneImageView = (ImageView) swipeLayout.findViewById(R.id.target_done_image);
        targetDoneTextView = (TextView) swipeLayout.findViewById(R.id.target_done_textView);
        targetDoneImageView.setImageResource(R.drawable.ic_done_black_48dp);
        targetDoneTextView.setTextColor(Color.BLACK);

    }

    @Override
    public void onSwipeLayoutCloseCallback(SwipeLayout swipeLayout) {
        targetDoneImageView = (ImageView) swipeLayout.findViewById(R.id.target_done_image);
        targetDoneTextView = (TextView) swipeLayout.findViewById(R.id.target_done_textView);
        targetDoneImageView.setImageResource(R.drawable.ic_check_white_48dp);
        targetDoneTextView.setTextColor(WHITE);
    }

    @Override
    public void doTarget(TargetEntity targetEntity) {
        int attributes = targetEntity.getAttributes();
        if(attributes == todoAttributes){
            targetEntityList.remove(targetEntity);
            targetSwipeAdapter.notifyDataSetChanged();
            if(!targetEntity.isDone()){
                targetEntity.setDone(true);
                targetDAO.update(targetEntity);
            }else{
                Log.e("TodoError", "Todo targetEntity already done.");
            }
        }
        if (Arrays.asList(lessPointattributes).contains(attributes)) {
            userEntity.setUserPoint(userEntity.getUserPoint() - targetEntity.getPoint());
        } else if(Arrays.asList(addPointattributes).contains(attributes)){
            userEntity.setUserPoint(userEntity.getUserPoint() + targetEntity.getPoint());
        }
        userDAO.update(userEntity);
        LogEntity logEntity = new LogEntity();
        logEntity.setEntityId(targetEntity.getId());
        logEntity.setDate(new Date());
        logDAO.insert(logEntity);
    }

    private void takePicture() {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File pictureFile = configFileName("P", ".jpg");
        Uri uri = Uri.fromFile(pictureFile);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intentCamera, START_CAMERA);
    }

    private File configFileName(String prefix, String extension) {
        // 如果記事資料已經有檔案名稱
        if (selectTargetEntity.getPhotoFileName() != null && selectTargetEntity.getPhotoFileName().length() > 0) {
            fileName = selectTargetEntity.getPhotoFileName();
        }
        // 產生檔案名稱
        else {
            fileName = FileUtil.getUniqueFileName();
        }

        return new File(FileUtil.getExternalStorageDir(FileUtil.APP_DIR),
                prefix + fileName + extension);
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission = checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
                return;
            }
        }
        takePicture();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture();
            }
            else {
                Toast.makeText(getActivity(), R.string.write_external_storage_denied,
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                    selectTargetEntity.setPhotoFileName(fileName);
                    File file = configFileName("P", ".jpg");

                    // 如果照片檔案存在
                    if (file.exists()) {
                        FileUtil.fileToImageView(file.getAbsolutePath(), photoImageView);
                        photoImageView.setVisibility(View.VISIBLE);

                    }
                    break;
                //從圖庫選擇
                case SELECT_PHOTO:
                    Uri pickedImage = data.getData();
                    String[] filePath = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
                    cursor.moveToFirst();
                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

                    FileUtil.fileToImageView(imagePath, photoImageView);
                    photoImageView.setVisibility(View.VISIBLE);
                    cursor.close();

                    selectTargetEntity.setPhotoFileName(null);
                    File outputFilePath = configFileName("P", ".jpg");
                    selectTargetEntity.setPhotoFileName(fileName);
                    try {
                        InputStream inputStream = new FileInputStream(imagePath);
                        OutputStream outputStream = new FileOutputStream(outputFilePath);
                        byte[] writeData = new byte[inputStream.available()];
                        inputStream.read(writeData);
                        outputStream.write(writeData);
                        inputStream.close();
                        outputStream.close();
                    } catch (IOException e) {
                        Log.w("ExternalStorage", "Error writing " + outputFilePath, e);
                    }

                    break;
            }
        }
    }

}
