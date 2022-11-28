package com.chenyacheng.androidutils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding

/**
 * activity基类
 *
 * @author bd
 * @date 2021/09/29
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var viewBinding: VB? = null
    protected val binding get() = viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = getViewBinding()
        setContentView(viewBinding!!.root)
        init()
    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

    fun getCoroutineScope(): LifecycleCoroutineScope {
        return lifecycleScope
    }

    protected abstract fun getViewBinding(): VB

    protected abstract fun init()
}