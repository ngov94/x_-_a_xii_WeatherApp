package com.example.weatherapp.ui.weekly


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.weatherapp.APIResponse.Daily
import com.example.weatherapp.R

class WeeklyAdapter(private val dailyList: List<Daily>) : RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val weeklyItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.limited_weekly_inflatable, parent, false)
        return ViewHolder(weeklyItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    //var sugarConcTextView: TextView = view.findViewById(R.id.sugar_conc_card)


}
