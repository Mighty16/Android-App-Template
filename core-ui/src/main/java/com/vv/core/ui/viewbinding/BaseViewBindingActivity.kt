package com.vv.core.ui.viewbinding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingActivity<VB:ViewBinding> : ComponentActivity() {

    abstract val binding: VB
    private var _binding: VB? = null

    protected val viewBinding: VB
        get() = _binding!!

    protected abstract fun setUpViews(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tmp = binding
        _binding = tmp
        setContentView(tmp.root)
    }

}