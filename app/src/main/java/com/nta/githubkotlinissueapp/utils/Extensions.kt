package com.nta.githubkotlinissueapp.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

inline fun <reified T : ViewDataBinding> Activity.lazyBind(@IdRes layoutId: Int) = lazy {
    DataBindingUtil.setContentView(this, layoutId) as T
}

val View.inflater: LayoutInflater
    get() = LayoutInflater.from(context)

fun Fragment.toast(message: String?) = message?.let {
    requireContext().toast(it)
}

fun Context.toast(message: String?) = message?.let {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun <T> T.dpToPX(dp: Float): Int where T : Context {
    return (dp * resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT).toInt()
}

fun <T> T.dpToPX(dp: Float): Int where T : Fragment {
    return (dp * resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT).toInt()
}


fun <T> LiveData<T>.requireOrThenAwait(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@requireOrThenAwait.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}
