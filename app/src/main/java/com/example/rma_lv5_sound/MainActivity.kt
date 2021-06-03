package com.example.rma_lv5_sound

import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.SoundPool
import android.os.Build
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.file.attribute.AclEntry


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var soundPool: SoundPool
    private var loaded: Boolean=false
    private var soundMap: HashMap<Int, Int> = HashMap()
    private val loadDimensions: Int = 350


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        loadSounds()

    }

    private fun loadSounds() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            this.soundPool = SoundPool.Builder().setMaxStreams(10).build()
        }
        else{
            this.soundPool = SoundPool(10,AudioManager.STREAM_MUSIC, 0)
        }
        this.soundPool.setOnLoadCompleteListener { _, _, _ -> loaded = true}
        this.soundMap[R.raw.barking]= this.soundPool.load(this, R.raw.barking,1)
        this.soundMap[R.raw.bumblebee]= this.soundPool.load(this, R.raw.bumblebee,1)
        this.soundMap[R.raw.birds]= this.soundPool.load(this, R.raw.birds,1)
        }

    private fun setupUI() {
        Glide.with(this).load(getString(R.string.url_dog)).apply(RequestOptions().override(loadDimensions,loadDimensions)).into(btn_top)
        Glide.with(this).load(getString(R.string.url_bee)).apply(RequestOptions().override(loadDimensions,loadDimensions)).into(btn_middle)
        Glide.with(this).load(getString(R.string.url_birds)).apply(RequestOptions().override(loadDimensions,loadDimensions)).into(btn_bottom)

        btn_top.setOnClickListener(this)
        btn_middle.setOnClickListener(this)
        btn_bottom.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        if (loaded){
            when(view?.id){
                R.id.btn_top -> playSound(R.raw.barking)
                R.id.btn_middle -> playSound(R.raw.bumblebee)
                R.id.btn_bottom -> playSound(R.raw.birds)
            }
        }
    }

    private fun playSound(selectedSound: Int) {
        val soundID = this.soundMap[selectedSound]?: 0
        this.soundPool.play(soundID, 1f,1f,1,0,1f)

    }
}