package com.google.android.youtube.player

import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.android.youtube.player.internal.ab

// source : https://gist.github.com/medyo/f226b967213c3b8ec6f6bebb5338a492#file-youtubeplayersupportfragmentx-java

open class YouTubePlayerSupportFragmentXKt : Fragment(), YouTubePlayer.Provider {

    private val a: A = A()

    private var b: Bundle? = null

    private var c: YouTubePlayerView? = null

    private var d: String? = null

    private var e: YouTubePlayer.OnInitializedListener? = null

    private val f = false

    private fun a() {
        if (c != null && e != null) {
            c?.a(f)
            c?.a(this.activity, this, d, e, b)
            b = null
            e = null
        }
    }

    override fun initialize(p0: String?, p1: YouTubePlayer.OnInitializedListener?) {
        this.d = ab.a(p0, "Developer key cannot be null or empty")
        this.e = p1
        this.a()
    }

    override fun onCreate(var1: Bundle?) {
        super.onCreate(var1)
        b = var1?.getBundle("YouTubePlayerSupportFragment.KEY_PLAYER_VIEW_STATE")
    }

    override fun onCreateView(var1: LayoutInflater, var2: ViewGroup?, var3: Bundle?): View? {
        c = YouTubePlayerView(this.activity, null as AttributeSet?, 0, a)
        this.a()
        return c
    }

    override fun onStart() {
        super.onStart()
        c?.a()
    }

    override fun onResume() {
        super.onResume()
        c?.b()
    }

    override fun onPause() {
        c?.c()
        super.onPause()
    }

    override fun onSaveInstanceState(var1: Bundle) {
        super.onSaveInstanceState(var1)
        val var2 = if (c != null && c is YouTubePlayerView) {
            (c as YouTubePlayerView).e()
        } else b
        var1.putBundle("YouTubePlayerSupportFragment.KEY_PLAYER_VIEW_STATE", var2)
    }

    override fun onStop() {
        c?.d()
        super.onStop()
    }

    override fun onDestroyView() {
        this.activity?.let {
            c?.c(it.isFinishing)
            c = null
        }

        super.onDestroyView()
    }

    override fun onDestroy() {
        if (c != null && c is YouTubePlayerView) {
            val var1 = this.activity
            (c as YouTubePlayerView).b(var1 == null || var1.isFinishing)
        }
        super.onDestroy()
    }

    companion object {
        fun newInstance(): YouTubePlayerSupportFragmentXKt {
            return YouTubePlayerSupportFragmentXKt()
        }
    }

    private class A : YouTubePlayerView.b {

        override fun a(
            p0: YouTubePlayerView?,
            p1: String?,
            p2: YouTubePlayer.OnInitializedListener?
        ) {
            val fragment = newInstance()
            fragment.initialize(p1, fragment.e)
        }

        override fun a(p0: YouTubePlayerView?) {
            // do nothing
        }
    }

}