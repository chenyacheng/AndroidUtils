package com.chenyacheng.androidutils;

import androidx.annotation.NonNull;

import com.chenyacheng.androidutils.databinding.ActivityCountDownBinding;
import com.chenyacheng.androidutils.library.CountDownTimerExKt;

import kotlin.Unit;
import kotlinx.coroutines.Job;

public class CountDownActivity1 extends BaseActivity<ActivityCountDownBinding> {

    private Job job = null;

    @NonNull
    @Override
    protected ActivityCountDownBinding getViewBinding() {
        return ActivityCountDownBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void init() {
        getBinding().btnFinish.setOnClickListener(v -> {
            job = CountDownTimerExKt.countDownCoroutines(60, it -> {
                String countDownStr = "倒计时" + it + "s";
                getBinding().tvCount.setText(countDownStr);
                getBinding().btnFinish.setEnabled(false);
                return Unit.INSTANCE;
            }, () -> {
                getBinding().tvCount.setText("结束");
                getBinding().btnFinish.setEnabled(true);
                return Unit.INSTANCE;
            }, getCoroutineScope());
        });

    }

    @Override
    protected void onDestroy() {
        if (job != null) {
            job.cancel(null);
            job = null;
        }
        super.onDestroy();
    }
}
