package com.developer.rozan.testmobile.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.developer.rozan.testmobile.data.response.DataItem
import com.developer.rozan.testmobile.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var name: String
    private lateinit var getResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.getSerializableExtra("Name").toString()

        binding.tvName.text = name

        binding.btnChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            getResultLauncher.launch(intent)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        activityResult()
    }

    private fun activityResult() {
        getResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val user: DataItem? = result.data?.getParcelableExtra("selected_user")

                if (user != null) {
                    binding.tvSelectedUsername.text = "${user.firstName} ${user.lastName}"
                }
            }
        }
    }
}