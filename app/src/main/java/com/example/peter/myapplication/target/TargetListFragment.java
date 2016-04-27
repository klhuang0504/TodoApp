package com.example.peter.myapplication.target;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

    private ListView targetlistView;
    private TargetDAO targetDAO;
    private List<TargetEntity> targetEntityList;
    private TargetSwipeAdapter targetSwipeAdapter;
    private ImageView targetDoneImageView, addTargetDoneActionImageView, addTargetDeleteActionImageView, addTargetCancelActionImageView, photoImageView;
    private TextView targetDoneTextView;
    private LinearLayout addTargetLayout, addTargetButtonActionLinearLayout;
    private EditText addTodoTaskEditText, targetNameEditText, pointEditText;
    private LinearLayout addTargetPointLinearLayout, addTargetButtonLinearLayout, photoLinearLayout;



    private TargetEntity selectTargetEntity;
    private UserDAO userDAO;
    private UserEntity userEntity;
    private LogDAO logDAO;

    private int targetAttributes;

    private String fileName;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetDAO = new TargetDAO(getActivity());
        userDAO = new UserDAO(getActivity());
        logDAO = new LogDAO(getActivity());

        Bundle bundle = getArguments();
        userEntity = (UserEntity) bundle.getSerializable("userEntity");
        targetAttributes = bundle.getInt("targetAttributes");

        targetEntityList = getTargetEntityList(targetAttributes);
//        int layoutId = R.layout.single_target;
        targetSwipeAdapter = new TargetSwipeAdapter(getActivity(), targetEntityList, this);
    }

//    public void processGoodTarget(View view) {
//        userEntity.setUserPoint(userEntity.getUserPoint() + selectTargetEntity.getPoint());
//        userDAO.update(userEntity);
//    }


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
        return new ArrayList<TargetEntity>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.target_list_layout, container,
                false);
        addTargetLayout = (LinearLayout) view.findViewById(R.id.add_target_layout);
        addTargetButtonActionLinearLayout = (LinearLayout) view.findViewById(R.id.addTargetButtonActionLinearLayout);

        photoImageView = (ImageView) view.findViewById(R.id.photoImageView);


        targetNameEditText = (EditText) view.findViewById(R.id.targetNameEditText);
        pointEditText = (EditText) view.findViewById(R.id.pointEditText);

        photoLinearLayout = (LinearLayout) view.findViewById(R.id.photoLinearLayout);

        addTargetDoneActionImageView = (ImageView) view.findViewById(R.id.done_add_target_button);
        addTargetDeleteActionImageView = (ImageView) view.findViewById(R.id.delete_add_target_button);
        addTargetCancelActionImageView = (ImageView) view.findViewById(R.id.cancel_add_target_button);

        addTargetDoneActionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTargetEntity.setTargetName(targetNameEditText.getText().toString());
                selectTargetEntity.setPoint(Integer.valueOf(pointEditText.getText().toString()));
                targetDAO.update(selectTargetEntity);
                addTargetLayout.setVisibility(View.INVISIBLE);
                photoLinearLayout.setVisibility(View.INVISIBLE);
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

        targetlistView = (ListView) view.findViewById(R.id.goodTargetListView);
        targetSwipeAdapter.setMode(Attributes.Mode.Single);
        targetlistView.setAdapter(targetSwipeAdapter);


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

        // 取得顯示照片的ImageView元件

//        targetlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ((SwipeLayout) (targetlistView.getChildAt(position - targetlistView.getFirstVisiblePosition()))).open(true);
//            }
//        });
//        targetlistView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                Log.e("ListView", "OnTouch");
//                return false;
//            }
//        });
//        targetlistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//        targetlistView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
////                Log.e("ListView", "onScrollStateChanged");
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
//
//        targetlistView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                Log.e("ListView", "onItemSelected:" + position);
//                selectTargetEntity = targetEntityList.get(position);
//                Log.i("select Entity", selectTargetEntity.getTargetName());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
////                Log.e("ListView", "onNothingSelected:");
//            }
//        });
        return view;
    }

    @Override
    public void onMethodCallback(TargetEntity targetEntity) {
        int attributes = targetEntity.getAttributes();
        if (attributes > 0) {
            userEntity.setUserPoint(userEntity.getUserPoint() - targetEntity.getPoint());
        } else {
            userEntity.setUserPoint(userEntity.getUserPoint() + targetEntity.getPoint());
        }
        userDAO.update(userEntity);
        LogEntity logEntity = new LogEntity();
        logEntity.setEntityId(targetEntity.getId());
        logEntity.setDate(new Date());
        logDAO.insert(logEntity);
//        Toast.makeText(getActivity(), "onMethodCallback - " + targetEntity.getTargetName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeItemOnMethodCallback(TargetEntity targetEntity) {
        targetEntityList.remove(targetEntity);
        targetSwipeAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClickEditButtonCallBack(TargetEntity targetEntity) {
        selectTargetEntity = targetEntity;
        targetNameEditText.setText(selectTargetEntity.getTargetName());
        pointEditText.setText(String.valueOf(selectTargetEntity.getPoint()));
        addTargetLayout.setVisibility(View.VISIBLE);
        addTargetDeleteActionImageView.setVisibility(View.VISIBLE);
        if(targetEntity.getPhotoFileName() != null){
            File file = new File(FileUtil.getExternalStorageDir(FileUtil.APP_DIR),
                    "P" + targetEntity.getPhotoFileName() + ".jpg");
            FileUtil.fileToImageView(file.getAbsolutePath(), photoImageView);
            photoLinearLayout.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.VISIBLE);
        }
//        addTargetButtonActionLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickDeleteActionButtonCallBack(TargetEntity targetEntity) {

    }

    @Override
    public void onSwipeLayoutStartOpenCallback(SwipeLayout swipeLayout) {

//        targetDoneImageView = (ImageView) swipeLayout.findViewById(R.id.target_done_image);
//        targetDoneTextView = (TextView) swipeLayout.findViewById(R.id.target_done_textView);
////        targetDoneImageView.setImageResource(R.drawable.ic_done_black_48dp);
//        int imageFrom = R.drawable.ic_check_white_48dp;
//        int imageTo = R.drawable.ic_done_black_48dp;
//        ValueAnimator imageAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), imageFrom, imageTo);
//        imageAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                targetDoneImageView.setImageResource((int) animator.getAnimatedValue());
//            }
//        });
//        imageAnimation.setDuration(3000); //設定速度
//        imageAnimation.start();
////        targetDoneTextView.setTextColor(BLACK);
//
////        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(),
////                R.animator.property_animator);
////        set.setTarget(targetDoneTextView);
////        set.start();
//
//        int colorFrom = Color.WHITE;
//        int colorTo = Color.BLACK;
//        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                targetDoneTextView.setTextColor((int) animator.getAnimatedValue());
//            }
//        });
//        colorAnimation.setDuration(3000); //設定速度
//        colorAnimation.start();
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
    public void onSwipeLayoutStartCloseCallback(SwipeLayout swipeLayout) {

        targetSwipeAdapter.removeShownLayouts(swipeLayout);
        targetSwipeAdapter.notifyDataSetChanged();
//        targetSwipeAdapter.notify();
//        targetDoneImageView = (ImageView) swipeLayout.findViewById(R.id.target_done_image);
//        targetDoneTextView = (TextView) swipeLayout.findViewById(R.id.target_done_textView);
//        targetDoneImageView.setImageResource(R.drawable.ic_done_black_48dp);
//        targetDoneTextView.setTextColor(BLACK);

//        int imageTo = R.drawable.ic_check_white_48dp;
//        int imageFrom = R.drawable.ic_done_black_48dp;
//        ValueAnimator imageAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), imageFrom, imageTo);
//        imageAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                targetDoneImageView.setImageResource((int) animator.getAnimatedValue());
//            }
//        });
//        imageAnimation.setDuration(3000); //設定速度
//        imageAnimation.start();

//        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(),
//                R.animator.property_animator);
//        set.setTarget(targetDoneTextView);
//        set.start();

//        int colorTo = Color.WHITE;
//        int colorFrom = Color.BLACK;
//        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                targetDoneTextView.setTextColor((int) animator.getAnimatedValue());
//            }
//        });
//        colorAnimation.setDuration(3000); //設定速度
//        colorAnimation.start();
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

//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
//                    photoImageView.setImageBitmap(bitmap);

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
