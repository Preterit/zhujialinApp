package com.sdxxtop.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdxxtop.base.R;

import androidx.annotation.Nullable;

public class TitleView extends RelativeLayout {

    private boolean layoutIconIsGray;
    private int rightTextColor;
    private String rightTextValue;
    private int bgColor;
    private int titleColor;
    private TextView tvTitle;
    private String titleValue;
    private TextView tvRight;
    private LinearLayout linearBack;
    private boolean layoutIsShow;
    private boolean fitsSystemWindows;
    private boolean clipToPadding;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleView, defStyleAttr, 0);
        titleValue = a.getString(R.styleable.TitleView_titleValue);
        titleColor = a.getColor(R.styleable.TitleView_titleColor, getResources().getColor(R.color.color_303030));
        bgColor = a.getColor(R.styleable.TitleView_bgColor, getResources().getColor(R.color.white));
        rightTextValue = a.getString(R.styleable.TitleView_rightTextValue);
        rightTextColor = a.getColor(R.styleable.TitleView_rightTextColor, getResources().getColor(R.color.white));
        layoutIsShow = a.getBoolean(R.styleable.TitleView_leftLayoutIsShow, false);
        layoutIconIsGray = a.getBoolean(R.styleable.TitleView_leftLayoutIconIsGray, false);
        fitsSystemWindows = a.getBoolean(R.styleable.TitleView_fits_system_windows, true);
        clipToPadding = a.getBoolean(R.styleable.TitleView_clip_to_padding, true);
        a.recycle();
        init();
    }

    private void init() {
        if (clipToPadding){
            setClipToPadding(clipToPadding);
            setFitsSystemWindows(fitsSystemWindows);
        }
        setBackgroundColor(bgColor);

        LayoutInflater.from(getContext()).inflate(R.layout.view_title, this, true);
        tvTitle = findViewById(R.id.tview_title);
        tvTitle.setText(titleValue);
        tvTitle.setTextColor(titleColor);
        tvRight = findViewById(R.id.tview_right);
        tvRight.setText(rightTextValue);
        tvRight.setTextColor(rightTextColor);
        if (layoutIsShow || layoutIconIsGray) {

            if (layoutIconIsGray) {
                ImageView view = findViewById(R.id.iv_back);
//                view.setPadding(UIUtils.dip2px(10), UIUtils.dip2px(4), UIUtils.dip2px(4), UIUtils.dip2px(0));
                view.setImageResource(R.drawable.white_return);
            }
            linearBack = findViewById(R.id.ll_back);
            linearBack.setVisibility(VISIBLE);
            linearBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getContext() instanceof Activity) {
                        ((Activity) getContext()).finish();
                    }
                }
            });
        }
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public void setTitleValue(String titleValue) {
        tvTitle.setText(titleValue);
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    /**
     * 需要的时候，重新把返回键的监听改一下
     *
     * @param listener
     */
    public void resetBackListener(OnClickListener listener) {
        linearBack.setOnClickListener(listener);
    }
}
