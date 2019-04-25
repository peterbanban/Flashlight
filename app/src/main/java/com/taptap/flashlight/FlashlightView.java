package com.taptap.flashlight;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hqx on 2019/4/25 17:13.
 */
public class FlashlightView extends FrameLayout implements OnClickListener {
  @BindView(R.id.container)
  RelativeLayout mContainer;
  @BindView(R.id.on)
  ImageView mOn;
  @BindView(R.id.off)
  ImageView mOff;
  @BindView(R.id.oval)
  ImageView mOval;
  View mRoot;

  public FlashlightView(Context context) {
    super(context);
    initView();
  }

  public void initView (){
//    mRoot = inflate(getContext(), R.layout.flashlight_view, this);
//    ButterKnife.bind(mRoot);
//    mRoot.setBackgroundResource(R.drawable.primary_bg);
//    mOn.setOnClickListener(this);
//    mOff.setOnClickListener(this);
//    mOval.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.on :
        FlashlightHelper.openFalshlight();
        break;

      case R.id.off :
        FlashlightHelper.shutdownFalshlight();
        break;

      case R.id.oval :
        break;
    }
  }
}
