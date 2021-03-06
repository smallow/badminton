package com.smallow.badminton.sys.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.smallow.badminton.sys.ui.CustomProgressDialogOne;
import com.smallow.badminton.sys.ui.CustomProgressDialogThree;
import com.smallow.badminton.view.SimpleLoadingView;

/**
 * Created by Administrator on 2015/10/28.
 */
public abstract class BaseFragment extends Fragment {

    public final static String TAG = "BaseFragment";
    private View rootView;


    /**
     * 从XML加载布局
     *
     * @param layoutId
     * @return
     */
    protected View inflaterView(int layoutId) {
        return LayoutInflater.from(getActivity()).inflate(layoutId, null);
    }


    protected <T extends View> T findViewById(int id) {
        return (T) rootView.findViewById(id);
    }

    @Override
    public final void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        if (rootView == null) {
            Log.d(TAG, getClass().getCanonicalName()
                    + " on view created,but content view was null ");
        }
        customProgressDialog=new CustomProgressDialogThree(getActivity());
        simpleLoadingView=new SimpleLoadingView(getActivity());
        onInitWidgets(view, savedInstanceState);
    }

    protected abstract void onInitWidgets(View rootView,
                                          Bundle savedInstanceState);


    private CustomProgressDialogThree customProgressDialog;
    private SimpleLoadingView simpleLoadingView;


    public void showLoadingDialog(final String text) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                simpleLoadingView.setCancelable(false);
                // TODO Auto-generated method stub
                if (text != null) {
                    simpleLoadingView.setMsg(text);
                }
                try {
                    if (!simpleLoadingView.isShowing()) {
                        simpleLoadingView.show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

    public void dismissLoadingDialog() {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (simpleLoadingView.isShowing()) {
                    simpleLoadingView.dismiss();
                }
            }
        });

    }

}
