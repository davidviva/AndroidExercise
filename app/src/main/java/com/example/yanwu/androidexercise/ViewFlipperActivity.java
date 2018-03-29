package com.example.yanwu.androidexercise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ViewFlipper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


// ViewFlipper 继承自ViewAnimator， 所以继承了一些很有用的方法，需研究
public class ViewFlipperActivity extends AppCompatActivity{
    @BindView(R.id.flipper)
    ViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewflipper);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_prev)
    public void onPrevClick() {
        flipper.showPrevious();
//        int index = flipper.getDisplayedChild();
//        System.out.println(flipper.getDisplayedChild());
//        flipper.setDisplayedChild(index - 1);
    }

    @OnClick(R.id.btn_next)
    public void onNextClick() {
        flipper.showNext();
//        int index = flipper.getDisplayedChild();
//        System.out.println(flipper.getDisplayedChild());
//        flipper.setDisplayedChild(index + 1);
    }
}
