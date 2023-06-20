package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SquareAdapter
    private var downX = 0f
    private var downY = 0f
    private var height = 0
    private var width = 0
    private var zeroSize = 16
    private val data = IntArray(16) { 0 }

//    private val adapter = SquareAdapter(data,width/4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        height = intent.getIntExtra("height", 0)
        width = intent.getIntExtra("width", 0)

        initView()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
            }
            MotionEvent.ACTION_UP -> {

                val x = abs(event.x - downX)
                val y = abs(event.y - downY)
                val directionStyle = Array(16) { DirectionStyle.NULL }
                if (x > y) {
                    if (event.x - downX > 60) {

                        repeat(3) {
                            repeat(3) { a ->
                                repeat(4) {
                                    val i = 3 + it * 4 - a
                                    if (data[i] == 0 && data[i - 1] != 0) {
                                        data[i] = data[i - 1]
                                        data[i - 1] = 0
                                        directionStyle[i - 1] = DirectionStyle.RIGHT
                                    } else if (data[i] != 0 && data[i] == data[i - 1]) {
                                        data[i] *= 2
                                        data[i - 1] = 0
                                        directionStyle[i - 1] = DirectionStyle.RIGHT
                                    }
                                }
                            }
                        }
                        adapter.setDirectionStyle(directionStyle)
                        adapter.setData(data)
                        adapter.notifyDataSetChanged()
                        addNumber()
                        Log.d("Motion", "从左至右滑动")
                    }
                    if (downX - event.x > 60) {

                        repeat(3) {
                            repeat(3) { a ->
                                repeat(4) {
                                    val i = it * 4 + a
                                    if (data[i] == 0 && data[i + 1] != 0) {
                                        data[i] = data[i + 1]
                                        data[i + 1] = 0
                                        directionStyle[i + 1] = DirectionStyle.LEFT
                                    } else if (data[i] != 0 && data[i] == data[i + 1]) {
                                        data[i] *= 2
                                        data[i + 1] = 0
                                        directionStyle[i + 1] = DirectionStyle.LEFT
                                    }
                                }
                            }
                            adapter.setDirectionStyle(directionStyle)
                            adapter.setData(data)
                            adapter.notifyDataSetChanged()
                        }
                        addNumber()
                        Log.d("Motion", "从右至左滑动")
                    }
                } else {
                    if (downY - event.y > 60) {

                        repeat(3) {
                            repeat(3) { a ->
                                repeat(4) {
                                    val i = it + a * 4
                                    if (data[i] == 0 && data[i + 4] != 0) {
                                        data[i] = data[i + 4]
                                        data[i + 4] = 0
                                        directionStyle[i + 4] = DirectionStyle.UP
                                    } else if (data[i] != 0 && data[i] == data[i + 4]) {
                                        data[i] *= 2
                                        data[i + 4] = 0
                                        directionStyle[i + 4] = DirectionStyle.UP
                                    }
                                }
                            }
                            adapter.setDirectionStyle(directionStyle)
                            adapter.setData(data)
                            adapter.notifyDataSetChanged()
                        }
                        addNumber()
                        Log.d("Motion", "从下至上滑动")
                    }

                    if (event.y - downY > 60) {

                        repeat(3) {
                            repeat(3) { a ->
                                repeat(4) {
                                    val i = it + (3 - a) * 4
                                    if (data[i] == 0 && data[i - 4] != 0) {
                                        data[i] = data[i - 4]
                                        data[i - 4] = 0
                                        directionStyle[i - 4] = DirectionStyle.DOWN
                                    } else if (data[i] != 0 && data[i] == data[i - 4]) {
                                        data[i] *= 2
                                        data[i - 4] = 0
                                        directionStyle[i - 4] = DirectionStyle.DOWN
                                    }
                                }
                            }
                            adapter.setDirectionStyle(directionStyle)
                            adapter.setData(data)
                            adapter.notifyDataSetChanged()
                        }
                        addNumber()
                        Log.d("Motion", "从上至下滑动")
                    }
                }
            }
        }

        return false
    }

    private fun initView() {
        binding.apply {
            recyclerview.layoutParams.height =
                width + convertDpToPixel(10f, this@MainActivity).toInt()
            recyclerview.layoutParams = recyclerview.layoutParams
            adapter = SquareAdapter(data, width / 4)
            recyclerview.adapter = adapter
            recyclerview.layoutManager = GridLayoutManager(this@MainActivity, 4)

        }
        addNumber()

    }

    private fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi / 160f)
    }

    private fun getZeroSize() { //重新計算還有多少0
        zeroSize = 0
        data.forEach {
            if (it == 0) {
                zeroSize++
            }
        }
    }

    private fun addNumber() {
        getZeroSize()
        if (zeroSize > 0) {
            var rd = (1..zeroSize).random()
            data.forEachIndexed { index, i ->
                if (i == 0 && rd == 1) {
                    data[index] = 2
                    adapter.setData(data)
                    adapter.notifyItemChanged(index)
                    return
                } else if (i == 0) {
                    rd--
                }
            }
        }
    }
}

data class IntAndDirectionStyle(
    var i: Int,
    var directionStyle: DirectionStyle,
)
