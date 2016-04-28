package com.example.peter.myapplication.target;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.peter.myapplication.FileUtil;
import com.example.peter.myapplication.R;
import com.example.peter.myapplication.data.TargetEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class TargetSwipeAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private TargetEntity selectTargetEntity;
    private List<TargetEntity> targetEntityList;
    private TargetSwipeAdapterCallback mTargetSwipeAdapterCallback;

    public TargetSwipeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public TargetSwipeAdapter(Context mContext, List<TargetEntity> targetEntityList) {
        this.mContext = mContext;
        this.targetEntityList = targetEntityList;
    }

    public TargetSwipeAdapter(Context mContext, List<TargetEntity> targetEntityList, TargetSwipeAdapterCallback callback) {
        this.mContext = mContext;
        this.targetEntityList = targetEntityList;
        this.mTargetSwipeAdapterCallback = callback;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_target_swipe_layout, null);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.bottom_wrapper));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.bottom_wrapper_2));



//        swipeLayout.addDrag(SwipeLayout.DragEdge.Top, starBottView);
//        swipeLayout.addDrag(SwipeLayout.DragEdge.Bottom, starBottView);
//        swipeLayout.addRevealListener(R.id.delete, new SwipeLayout.OnRevealListener() {
//            @Override
//            public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
////                Toast.makeText(mContext, "Reveal", Toast.LENGTH_SHORT).show();
//
//            }
//        });

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            private boolean smallOpen = true;

            @Override
            public void onStartOpen(SwipeLayout layout) {
                if (!smallOpen) {
                    layout.close();
                }
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                selectTargetEntity = targetEntityList.get(position);
                mTargetSwipeAdapterCallback.doTarget(selectTargetEntity);
                mTargetSwipeAdapterCallback.onSwipeLayoutOpenCallback(layout);

//                Toast.makeText(mContext, selectTargetEntity.getTargetName(), Toast.LENGTH_SHORT).show();
//                Log.i("",selectTargetEntity.getTargetName());
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
//                mTargetSwipeAdapterCallback.onSwipeLayoutStartCloseCallback(layout);
            }

            @Override
            public void onClose(SwipeLayout layout) {
                mTargetSwipeAdapterCallback.onSwipeLayoutCloseCallback(layout);
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                if (xvel < (layout.getWidth() / 10 * 8)) {
                    smallOpen = false;
                    layout.close();
                } else {
                    smallOpen = true;
                }
            }
        });

        swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "Click on surface", Toast.LENGTH_SHORT).show();
//                Log.d(MyActivity.class.getName(), "click on surface");
            }
        });
        swipeLayout.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Toast.makeText(mContext, "longClick on surface", Toast.LENGTH_SHORT).show();
//                Log.d(MyActivity.class.getName(), "longClick on surface");
                return false;
            }
        });
        swipeLayout.findViewById(R.id.plusOneImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTargetEntity = targetEntityList.get(position);
                mTargetSwipeAdapterCallback.doTarget(selectTargetEntity);
            }
        });
        swipeLayout.findViewById(R.id.undoImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeLayout.close();
                mTargetSwipeAdapterCallback.onClickUndoButtonCallBack(selectTargetEntity);
            }
        });
        swipeLayout.findViewById(R.id.laterImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTargetEntity = targetEntityList.get(position);
                mTargetSwipeAdapterCallback.onClickLaterButtonMethodCallback(selectTargetEntity);
            }
        });

        swipeLayout.findViewById(R.id.editImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTargetSwipeAdapterCallback.onClickEditButtonCallBack(selectTargetEntity);
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TargetEntity targetEntity = targetEntityList.get(position);
        TextView swipeItemTextView = (TextView) convertView.findViewById(R.id.SwipeItemTextView);
        swipeItemTextView.setText(targetEntity.getTargetName() + "  (" + String.valueOf(targetEntity.getPoint()) + "分)");
        if(targetEntity.getPhotoFileName() != null){
            File file = new File(FileUtil.getExternalStorageDir(FileUtil.APP_DIR),
                    "P" + targetEntity.getPhotoFileName() + ".jpg");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;

            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, getBitmapOptions(2));

//            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            Drawable drawable = new BitmapDrawable(mContext.getResources(), bitmap);
//            Drawable drawable = Drawable.createFromPath(file.getAbsolutePath());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                swipeItemTextView.setBackground(drawable);
            }
        }
    }

    public BitmapFactory.Options getBitmapOptions(int scale){
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPurgeable = true;
//        options.inInputShareable = true;
        options.inSampleSize = scale;
        return options;
    }

    @Override
    public int getCount() {
        return targetEntityList.size();
    }

    @Override
    public TargetEntity getItem(int position) {
        return targetEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        TargetEntity targetEntity = targetEntityList.get(position);
        return targetEntity.getId();
    }

}
