package com.example.peter.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by peter on 2016/3/29.
 */
public class TargetAdapter extends ArrayAdapter<TargetEntity> {

    // 畫面資源編號
    private int resource;
    // 包裝的記事資料
    private List<TargetEntity> targetEntityList;

    public TargetAdapter(Context context, int resource, List<TargetEntity> targetEntityList) {
        super(context, resource, targetEntityList);
        this.resource = resource;
        this.targetEntityList = targetEntityList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        // 讀取目前位置的記事物件
        final TargetEntity targetEntity = getItem(position);

        if (convertView == null) {
            // 建立項目畫面元件
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)
                    getContext().getSystemService(inflater);
            li.inflate(resource, itemView, true);
        }
        else {
            itemView = (LinearLayout) convertView;
        }

        // 讀取記事顏色、已選擇、標題與日期時間元件
//        RelativeLayout typeColor = (RelativeLayout) itemView.findViewById(R.id.type_color);
//        ImageView selectedItem = (ImageView) itemView.findViewById(R.id.selected_item);
        TextView targetView = (TextView) itemView.findViewById(R.id.target_text);
        TextView pointView = (TextView) itemView.findViewById(R.id.point_text);

        // 設定記事顏色
//        GradientDrawable background = (GradientDrawable)typeColor.getBackground();
//        background.setColor(item.getColor().parseColor());

        // 設定標題與日期時間
        targetView.setText(targetEntity.getTargetName());
        pointView.setText(targetEntity.getPoint());

        // 設定是否已選擇
//        selectedItem.setVisibility(item.isSelected() ? View.VISIBLE : View.INVISIBLE);

        return itemView;
    }

    // 設定指定編號的記事資料
//    public void set(int index, TargetEntity TtargetEntity) {
//        if (index >= 0 && index < targetEntityList.size()) {
//            targetEntityList.set(index, item);
//            notifyDataSetChanged();
//        }
//    }

    // 讀取指定編號的記事資料
//    public Item get(int index) {
//        return items.get(index);
//    }

}
