package com.smallow.badminton.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.smallow.badminton.R;
import com.smallow.badminton.sys.utils.CommonUtils;


/**
 * Created by smallow on 15/12/3.
 */
public class DataStateBox extends FrameLayout {

    public enum State {
        /** 初始化加载状态 */
        INIT_LOADING,
        /** 没有数据 */
        EMPTY_DATA,
        /** 加载失败 */
        LOAD_ERROR,
        /** 隐藏不需要使用 */
        HIDE;
    }

    private View stateBlockView;
    //private ProgressBar stateLoadingView;
    private ProgressWheel stateLoadingView;

    private TextView stateTextView;
    private View contentView;
    //
    private String msgInitLoading = "加载中...";
    private String msgEmptyDataResult = "没有数据,点击刷新";
    private String msgLoadError = "请求失败,点击重试";

    /**
     * 构造函数
     * @param context
     * @param attrs
     */
    public DataStateBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWidgets();
    }

    /**
     * 构造函数
     * @param context
     */
    public DataStateBox(Context context) {
        super(context);

        initWidgets();
    }

    private void initWidgets() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_data_state, this,
                true);
        if (isInEditMode()) {return;}
        stateBlockView = findViewById(R.id.data_state_ui_block);
        stateBlockView.setOnClickListener(innerOnClickListener);
        stateLoadingView = (ProgressWheel) findViewById(R.id.data_state_progress);
        stateTextView = (TextView) findViewById(R.id.data_state_text);
        //setState(State.HIDE);
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        if (child.getId() == R.id.data_state_ui_block) {
            index = getChildCount() - 1;
        } else {
            contentView = child;
            index = 0;
        }
        super.addView(child, index, params);
    }

    public View getContentView() {
        if (getChildCount() == 0) {
            return null;
        }
        return getChildAt(0);
    }

    public void setMsgInitLoading(String msgInitLoading) {
        this.msgInitLoading = msgInitLoading;
    }

    public void setMsgEmptyDataResult(String msgEmptyDataResult) {
        this.msgEmptyDataResult = msgEmptyDataResult;
    }

    public void setMsgLoadError(String msgLoadError) {
        this.msgLoadError = msgLoadError;
    }

    private State curState = State.INIT_LOADING;

    public void setState(State state) {
        this.curState = state;
        updateStateUI();
    }

    private void updateStateUI() {
        if (curState == null) {
            curState = State.HIDE;
        }
        switch (curState) {
            case HIDE:
                CommonUtils.checkVisibility(stateBlockView, View.GONE);
                CommonUtils.checkVisibility(contentView, View.VISIBLE);
                break;
            case EMPTY_DATA:
                CommonUtils.checkVisibility(contentView, View.INVISIBLE);
                CommonUtils.checkVisibility(stateBlockView, View.VISIBLE);
                CommonUtils.checkVisibility(stateLoadingView, View.GONE);
                stateTextView.setText(msgEmptyDataResult);
                break;
            case LOAD_ERROR:
                CommonUtils.checkVisibility(contentView, View.INVISIBLE);
                CommonUtils.checkVisibility(stateBlockView, View.VISIBLE);
                CommonUtils.checkVisibility(stateLoadingView, View.GONE);
                stateTextView.setText(msgLoadError);
                break;
            case INIT_LOADING:
                CommonUtils.checkVisibility(contentView, View.INVISIBLE);
                CommonUtils.checkVisibility(stateBlockView, View.VISIBLE);
                CommonUtils.checkVisibility(stateLoadingView, View.VISIBLE);
                stateTextView.setText(msgInitLoading);
                break;
        }
    }

    private DataStateBoxListener mDataSateBoxListener;
    private OnClickListener innerOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (curState) {
                case HIDE:
                case INIT_LOADING:
                    return;
                case EMPTY_DATA:
                    if (mDataSateBoxListener != null) {
                        mDataSateBoxListener.onReqeustReloadData(curState,
                                DataStateBox.this);
                    }
                    break;
                case LOAD_ERROR:
                    if (mDataSateBoxListener != null) {
                        mDataSateBoxListener.onReqeustReloadData(curState,
                                DataStateBox.this);
                    }
                    break;
            }
        }
    };

    public void setDataSateBoxListener(DataStateBoxListener l) {
        this.mDataSateBoxListener = l;
    }

    public interface DataStateBoxListener {
        /**
         * 请求加载数据
         *
         * @param curState
         */
        public void onReqeustReloadData(State curState, DataStateBox v);
    }
}
