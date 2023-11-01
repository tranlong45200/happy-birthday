package com.taymay.nam_trang_birthday

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {
    var player: MediaPlayer? = null

    var play = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player = MediaPlayer()

        var a: LottieAnimationView = findViewById<LottieAnimationView>(R.id.lotie)

        a.setOnClickListener {
            if (!play) {
                findViewById<LottieAnimationView>(R.id.lotie).visibility = View.VISIBLE
                findViewById<RelativeLayout>(R.id.happyShow).visibility =
                    View.GONE
                player = MediaPlayer()
                startSound("audio.mp3");
                a.setAnimation(R.raw.birthday)
                a.playAnimation()
                a.loop(true)

            } else {
                try {
                    a.setAnimation(R.raw.candle)
                    a.playAnimation()
                    a.loop(true)
                    player!!.release();
                    player = null;
                    player?.stop()

                    Timer().schedule(object : TimerTask() {
                        override fun run() {
                            runOnUiThread {
                                findViewById<LottieAnimationView>(R.id.lotie).visibility = View.GONE
                                findViewById<RelativeLayout>(R.id.happyShow).visibility =
                                    View.VISIBLE
                            }
                        }
                    }, 1000)
                } catch (e: Exception) {
                }
            }
        }

        findViewById<RelativeLayout>(R.id.happyShow).setOnClickListener {
                findViewById<LottieAnimationView>(R.id.lotie).visibility = View.VISIBLE
                findViewById<RelativeLayout>(R.id.happyShow).visibility =
                    View.GONE
                player = MediaPlayer()
                startSound("audio.mp3");
                a.setAnimation(R.raw.birthday)
                a.playAnimation()
                a.loop(true)
                play = true
        }

        startSound("audio.mp3");
        player!!.setOnCompletionListener(OnCompletionListener { mp -> mp.release() })
    }

    private fun startSound(filename: String) {
        var afd: AssetFileDescriptor? = null
        try {
            afd = resources.assets.openFd(filename)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            assert(afd != null)
            player?.setDataSource(afd!!.fileDescriptor, afd.startOffset, afd.length + 1000)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            player?.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        player?.isLooping = true
        player?.start()
    }

    override fun onStop() {
        super.onStop()
        player?.stop()
        player?.release()
    }
}