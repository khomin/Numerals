package com.example.nummerals

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.delay


class AudioNumeralPlayer : Activity() {
    private var mMediaPlayerArray: MutableList<MediaPlayer> = mutableListOf()
    private var mCallbackFinish: (()->Unit) ?= null
    private var mContext: Context?= null

    @RequiresApi(Build.VERSION_CODES.M)
    fun addValueToPlay(context: Context?, value: List<Int>, callbackFinish: ()->Unit) {
        context?.let {
            mContext = it
            mCallbackFinish = callbackFinish

            for(i in value) {
                val player = MediaPlayer.create(mContext, i)
                player?.setPlaybackParams(player.playbackParams.setSpeed(1.2f))
                player.pause()
                mMediaPlayerArray.add(player)
            }

            createListenerEndPlay()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createListenerEndPlay() {
        GlobalScope.launch {
            withContext(coroutineContext) {
                while(mMediaPlayerArray.isNotEmpty()) {
                    mMediaPlayerArray.first().start()
                    delay(mMediaPlayerArray.first().duration.toLong()-200)

                    mMediaPlayerArray.remove(mMediaPlayerArray.first())
                }
                mCallbackFinish?.invoke()
            }
        }
    }
}