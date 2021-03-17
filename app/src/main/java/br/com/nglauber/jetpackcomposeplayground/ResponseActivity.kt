package br.com.nglauber.jetpackcomposeplayground

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.nglauber.jetpackcomposeplayground.databinding.ActivityResponseBinding

class ResponseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResponseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResponseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            sendActivityResult("Button 1")
        }
        binding.btn2.setOnClickListener {
            sendActivityResult("Button 2")
        }
        binding.btn3.setOnClickListener {
            sendActivityResult("Button 3")
        }
    }

    private fun sendActivityResult(text: String) {
        val anInt = intent.getIntExtra(EXTRA_INT, -1)
        setResult(RESULT_OK, Intent().apply {
            putExtra(EXTRA_STRING, "$text: $anInt")
        })
        finish()
    }

    companion object {
        const val EXTRA_INT = "anInt"
        const val EXTRA_STRING = "anString"
    }
}