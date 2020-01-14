package kr.dnx.ble.android.mysunflower

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.dnx.ble.android.mysunflower.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainAcitivty"

    private lateinit var binding: ActivityMainBinding;
    private val myName: MyName = MyName("juntaek oh")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        Log.i(TAG, "Hello")
        val retrofitManger = OkHttpRetrofitManager.getInstance()
        retrofitManger.requestPosts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError {
                Toast.makeText(this, "doOnError!", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "doOnError")
            }
            .unsubscribeOn(Schedulers.io())
            .onErrorReturn {t: Throwable ->
                Log.d(TAG, "onErrorReturn : " + t.message)
                arrayOf(Post())
            }
            .subscribe { result ->
                binding.bioText.setText(result.get(0).body)
            }





        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.doneButton.setOnClickListener {
            addNickName(it)
        }

        binding.myName = myName
    }

    private fun addNickName(it: View) {
        binding.apply {
            myName?.nickname = nicknameEdit.text.toString()
            invalidateAll()
            nicknameEdit.visibility = View.GONE
            doneButton.visibility = View.GONE
            nicknameText.visibility = View.VISIBLE
        }

//        // Hide the keyboard.
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}
