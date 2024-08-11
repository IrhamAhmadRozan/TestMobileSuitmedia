package com.developer.rozan.testmobile.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.developer.rozan.testmobile.R
import com.developer.rozan.testmobile.databinding.ActivityMainBinding
import com.developer.rozan.testmobile.databinding.DialogBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheck.setOnClickListener {
            val palindrome = binding.tfPalindrome.editText?.text.toString()

            if (palindrome.isNotEmpty()) {
                showDialog(isPalindrome(palindrome))
            } else {
                Toast.makeText(this, "Palindrome column cannot be empty !", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnNext.setOnClickListener {
            val name = binding.tfName.editText?.text.toString()

            if (name.isNotEmpty()) {
                startActivity(Intent(this, SecondActivity::class.java).apply {
                    putExtra("Name", name)
                })
            } else {
                Toast.makeText(this, "Name cannot be empty !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val normalizedText = text.replace("\\s".toRegex(), "").lowercase()
        return normalizedText == normalizedText.reversed()
    }

    private fun showDialog(checkPalindrome: Boolean) {
        val dialog = Dialog(this)

        val dialogBinding = DialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.window?.attributes?.windowAnimations = R.style.animation

        if (checkPalindrome) {
            val color = ContextCompat.getColor(this, R.color.colorSuccess)
            dialogBinding.ivImage.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            dialogBinding.ivImage.setImageResource(R.drawable.ic_checklist)
            dialogBinding.tvText.text = "Palindrome"
        } else {
            val color = ContextCompat.getColor(this, R.color.colorFailed)
            dialogBinding.ivImage.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            dialogBinding.ivImage.setImageResource(R.drawable.ic_cancel)
            dialogBinding.tvText.text = "Not Palindrome"
        }

        dialogBinding.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}