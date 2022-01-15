package com.example.janken

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.example.janken.databinding.ActivityMainBinding
import com.example.janken.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    val gu = 0
    val choki = 1
    val pa = 2

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupJanken()
        setupBackButton()
    }

    private fun setupBinding() {
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupJanken() {
        val id = intent.getIntExtra("MY_HAND", 0)
        val myHand: Int
        // myHandのイメージをセットしてmyHandのIntを返す
        myHand = bindMyHandImageAndReturnMyHand(id)
        // コンピュータの手を決める
        val comHand = getHand()
        // Comのイメージをセットする
        bindComHandImage(comHand)
        // 勝敗を判定する
        val gameResult = (comHand - myHand + 3) % 3
        // テキストをセットする
        setText(gameResult)
        // ゲームデータを保存する
        saveData(myHand, comHand, gameResult)
    }

    private fun bindMyHandImageAndReturnMyHand(id: Int): Int {
        when(id) {
            R.id.gu -> {
                binding.myHandImage.setImageResource(R.drawable.gu)
                return gu
            }
            R.id.choki -> {
                binding.myHandImage.setImageResource(R.drawable.choki)
                return choki
            }
            R.id.pa -> {
                binding.myHandImage.setImageResource(R.drawable.pa)
                return pa
            }
            else -> return gu
        }
    }

    private fun bindComHandImage(comHand: Int) {
        when(comHand) {
            gu -> binding.comHandImage.setImageResource(R.drawable.com_gu)
            choki -> binding.comHandImage.setImageResource(R.drawable.com_choki)
            pa -> binding.comHandImage.setImageResource(R.drawable.com_pa)
        }
    }

    private fun setText(gameResult: Int) {
        when(gameResult) {
            0 -> binding.resultLabel.setText(R.string.result_draw)
            1 -> binding.resultLabel.setText(R.string.result_win)
            2 -> binding.resultLabel.setText(R.string.result_lose)
        }
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            // 現在のアクティビティを終了して、前のアクティビティに戻る
            finish()
        }
    }

    private fun saveData(myHand: Int, comHand: Int, gameResult: Int) {
        // 共有プリファレンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        // 共有プリファレンスから値を取得して、変数に代入
        val gameCount = pref.getInt("GAME_COUNT", 0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0)
        val lastComHand = pref.getInt("LAST_COM_HAND", 0)
        val lastGameResult = pref.getInt("GAME_RESULT", -1)

        // 連勝回数を保持する変数
        val edtWinningStreakCount: Int =
            when {
                lastGameResult == 2 && gameResult == 2 ->
                    winningStreakCount + 1
                else ->
                    0
            }

        pref.edit {
            // 勝負回数をカウントアップ
            putInt("GAME_COUNT", gameCount + 1)
            // 共有プリファレンスに書き込み
            putInt("WINNING_STREAK_COUNT", edtWinningStreakCount)
            putInt("LAST_MY_HAND", myHand)
            putInt("LAST_COM_HAND", comHand)
            putInt("BEFORE_LAST_COM_HAND", lastComHand)
            putInt("GAME_RESULT", gameResult)
        }
    }

    private fun getHand(): Int {
        var hand = (Math.random() * 3).toInt()
        var pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAME_COUNT", 0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0)
        val lastMyHand = pref.getInt("LAST_MY_HAND", 0)
        val lastComHand = pref.getInt("LAST_COM_HAND", 0)
        val beforeLastComHand = pref.getInt("BEFORE_LAST_COM_HAND", 0)
        val gameResult = pref.getInt("GAME_RESULT", -1)

        if (gameCount == 1) {
            if (gameResult == 2) {
                while (lastComHand == hand) {
                    hand = (Math.random() * 3).toInt()
                }
            } else if (gameResult == 1) {
                hand = (lastComHand - 1 + 3) % 3
            }
        } else if (winningStreakCount > 0) {
            if (beforeLastComHand == lastComHand) {
                while (lastComHand == hand) {
                    hand = (Math.random() * 3).toInt()
                }
            }
        }
        return hand
    }

}