package com.example.janken

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.janken.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // ビューバインディングの設定
    // lateinitで後から初期化する
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ビューバインディングの設定
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ボタンの処理を設定
        binding.gu.setOnClickListener {
            onJankenButtonTapped(it)
        }
        binding.choki.setOnClickListener {
            onJankenButtonTapped(it)
        }
        binding.pa.setOnClickListener {
            onJankenButtonTapped(it)
        }

    }

    // 引数はViewを受け取れるように指定
    fun onJankenButtonTapped(view: View?) {
        // 第1引数は呼び出し下のインスタンス
        // 第2引数はjava.lang.Classを指定する
        // この第2引数をKotlinでは「クラス名::class.java」と書く
        val resultsActivityIntent = Intent(this, ResultActivity::class.java)
        startActivity(resultsActivityIntent)
    }
}