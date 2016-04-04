package com.example.peter.myapplication;


        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.Locale;

        import android.app.Fragment;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

public class Sample1Fragment extends Fragment {
    private String mTimeString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat formater = new SimpleDateFormat(
                "yyyy/MM/dd HH:mm:ss.SSS", Locale.TAIWAN);
        mTimeString = formater.format(now);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample1, container,
                false);
        TextView txtDateTime = (TextView) view.findViewById(R.id.txtDateTime);
        txtDateTime.setText(mTimeString);
        return view;
    }
}

