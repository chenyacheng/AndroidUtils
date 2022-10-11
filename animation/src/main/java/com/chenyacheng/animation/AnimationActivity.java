package com.chenyacheng.animation;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import androidx.appcompat.app.AppCompatActivity;

import com.chenyacheng.animation.databinding.ActivityAnimationBinding;

/**
 * @author BD
 * @date 2022/10/11 17:38
 */
public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private AlphaAnimation alphaAniShow, alphaAniHide;
    private TranslateAnimation translateAniShow, translateAniHide;
    private Animation bigAnimation, smallAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAnimationBinding binding = ActivityAnimationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alphaAnimation();
        binding.btnAlphaShow.setOnClickListener(view -> {
            binding.ivAlphaLogo.startAnimation(alphaAniShow);
            binding.ivAlphaLogo.setVisibility(View.VISIBLE);
        });
        binding.btnAlphaHide.setOnClickListener(view -> {
            binding.ivAlphaLogo.startAnimation(alphaAniHide);
            //这个地方为什么要做动画的监听呢，因为隐藏和显示不一样，
            //必须在动画结束之后再隐藏你的控件，这样才不会显得很突兀
            alphaAniHide.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.ivAlphaLogo.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        });

        scaleAnimation();
        binding.btnScaleShow.setOnClickListener(view -> {
            binding.ivScaleLogo.startAnimation(bigAnimation);
            binding.ivScaleLogo.setVisibility(View.VISIBLE);
        });
        binding.btnScaleHide.setOnClickListener(view -> {
            binding.ivScaleLogo.startAnimation(smallAnimation);
            smallAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.ivScaleLogo.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        });

        translateAnimation();
        binding.btnTranslateShow.setOnClickListener(view -> {
            binding.ivTranslateLogo.startAnimation(translateAniShow);
            binding.ivTranslateLogo.setVisibility(View.VISIBLE);
        });
        binding.btnTranslateHide.setOnClickListener(view -> {
            binding.ivTranslateLogo.startAnimation(translateAniHide);
            translateAniHide.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.ivTranslateLogo.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        });
    }

    //透明度动画
    private void alphaAnimation() {
        //显示
        alphaAniShow = new AlphaAnimation(0, 1);//百分比透明度，从0%到100%显示
        alphaAniShow.setDuration(1000);//一秒

        //隐藏
        alphaAniHide = new AlphaAnimation(1, 0);
        alphaAniHide.setDuration(1000);
    }

    //缩放动画
    private void scaleAnimation() {
        //放大
        bigAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_big);
        //缩小
        smallAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_small);

    }

    //位移动画
    private void translateAnimation() {
        //向上位移显示动画  从自身位置的最下端向上滑动了自身的高度
        translateAniShow = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                1,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                0);//fromXValue表示结束的Y轴位置
        translateAniShow.setRepeatMode(Animation.REVERSE);
        translateAniShow.setDuration(1000);

        //向下位移隐藏动画  从自身位置的最上端向下滑动了自身的高度
        translateAniHide = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                1);//fromXValue表示结束的Y轴位置
        translateAniHide.setRepeatMode(Animation.REVERSE);
        translateAniHide.setDuration(1000);
    }


    @Override
    public void onClick(View view) {

    }
}
