package com.example.peter.myapplication;

/**
 * Created by peter on 4/15/16.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class BackHandledFragment extends Fragment {
    protected BackHandlerInterface backHandlerInterface;

    public abstract String getTagText();

    public abstract boolean onBackPressedFlag();

    public interface BackHandlerInterface {
        public void setSelectedFragment(BackHandledFragment backHandledFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandlerInterface)) {
            throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
        } else {
            backHandlerInterface = (BackHandlerInterface) getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Mark this fragment as the selected Fragment.
        backHandlerInterface.setSelectedFragment(this);
    }
}