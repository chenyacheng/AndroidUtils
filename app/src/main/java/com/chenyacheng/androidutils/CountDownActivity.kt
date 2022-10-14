package com.chenyacheng.androidutils

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chenyacheng.androidutils.databinding.ActivityCountDownBinding
import com.chenyacheng.androidutils.library.countDownCoroutines
import com.chenyacheng.androidutils.library.setThrottleListener
import kotlinx.coroutines.Job

/**
 * @author BD
 * @date 2022/10/14 11:02
 */
class CountDownActivity : AppCompatActivity() {

    private val binding: ActivityCountDownBinding by lazy {
        ActivityCountDownBinding.inflate(
            layoutInflater
        )
    }
    private var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnFinish.setThrottleListener {
            Log.v("===========", "倒计时:开始>>>> ")
            job = countDownCoroutines(5,{
                binding.tvCount.text = "倒计时:${it}s"
                binding.btnFinish.isClickable = false
            },{
                binding.tvCount.text = "结束"
                binding.btnFinish.isClickable = true
            },lifecycleScope)
        }

    }

    override fun onDestroy() {
        job?.cancel()
        job = null
        super.onDestroy()
    }
}