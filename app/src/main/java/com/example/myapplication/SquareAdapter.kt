package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.SquareBinding


class SquareAdapter(private var data: IntArray, private val width: Int) :
    RecyclerView.Adapter<SquareViewHolder>() {

    private lateinit var context: Context
    private var directionStyle = Array(16) { DirectionStyle.NULL }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquareViewHolder {
        context = parent.context
        return SquareViewHolder(
            SquareBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 16
    }

    override fun onBindViewHolder(holder: SquareViewHolder, position: Int) {
        holder.binding.constraintlayout.apply {
            layoutParams.height = this@SquareAdapter.width
            layoutParams.width = this@SquareAdapter.width
            layoutParams = layoutParams
        }
        holder.binding.textView.text = data[position].toString()
        val color = when (data[position]) {
            2 -> ContextCompat.getColor(context, R.color.black)
            4 -> ContextCompat.getColor(context, R.color.purple_200)
            8 -> ContextCompat.getColor(context, R.color.purple_500)
            16 -> ContextCompat.getColor(context, R.color.purple_700)
            32 -> ContextCompat.getColor(context, R.color.teal_200)
            64 -> ContextCompat.getColor(context, R.color.teal_700)
            128 -> ContextCompat.getColor(context, R.color.red)
            256 -> ContextCompat.getColor(context, R.color.ake)

            else -> ContextCompat.getColor(context, R.color.sakura)
        }

        holder.binding.textView.setBackgroundColor(color)
//        if (directionStyle[position] != DirectionStyle.NULL){
//            startAnimation(holder.binding.textView,directionStyle[position])
//        }
    }

    fun setData(data: IntArray) {
        this.data = data
    }

    fun setDirectionStyle(directionStyle: Array<DirectionStyle>) {
        this.directionStyle = directionStyle
    }

    private fun startAnimation(view: View, directionStyle: DirectionStyle) {
        val xy: Pair<Int, Int> = when (directionStyle) {
            DirectionStyle.UP -> Pair(0, width)
            DirectionStyle.DOWN -> Pair(0, -width)
            DirectionStyle.LEFT -> Pair(-width, 0)
            DirectionStyle.RIGHT -> Pair(width, 0)
            DirectionStyle.NULL -> Pair(0, 0)
        }

        val translateAnimation = TranslateAnimation(
            0f, xy.first.toFloat(), 0f, xy.second.toFloat()
        )
        translateAnimation.duration = 500
        view.startAnimation(translateAnimation)
    }
}

enum class DirectionStyle {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    NULL
}

class SquareViewHolder(val binding: SquareBinding) : RecyclerView.ViewHolder(binding.root)