package com.example.weatherapp.ui.weekly


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.weatherapp.APIResponse.Daily
import com.example.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WeeklyAdapter(private val dailyList: List<Daily>) : RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val weeklyItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.limited_weekly_inflatable, parent, false)
        return ViewHolder(weeklyItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dailyList[position]

        var date = SimpleDateFormat("EEE d").format(Date(item.dt.toLong()*1000))



        holder.itemMaxTextView.text = item.temp.max.roundToInt().toString()+ "°"
        holder.itemMinTextView.text = item.temp.min.roundToInt().toString()+ "°"
        holder.itemPrecipTextView.text = (item.pop*100).roundToInt().toString()+ "%"
        holder.itemDateTextView.text = date


        var icon = when (item.weather.first().icon){
            "01d" -> R.drawable.w_clear_sky_day
            "01n" -> R.drawable.w_clear_sky_night
            "02d" -> R.drawable.w_few_clouds_day
            "02n" -> R.drawable.w_few_clouds_night
            "03d" -> R.drawable.w_scattered_clouds
            "03n" -> R.drawable.w_scattered_clouds
            "04d" -> R.drawable.w_broken_clouds
            "04n" -> R.drawable.w_broken_clouds
            "09d" -> R.drawable.w_shower_rain
            "09n" -> R.drawable.w_shower_rain
            "10d" -> R.drawable.w_rain_day
            "10n" -> R.drawable.w_rain_night
            "11d" -> R.drawable.w_thunderstorm
            "11n" -> R.drawable.w_thunderstorm
            "13d" -> R.drawable.w_snow
            "13n" -> R.drawable.w_snow
            "50d" -> R.drawable.w_mist
            "50n" -> R.drawable.w_mist
            else -> R.drawable.w_clear_sky_day
        }

        holder.itemIconImageView.setImageDrawable(holder.itemView.context.getDrawable(icon))


    }

    override fun getItemCount(): Int {
        return dailyList.size
    }


}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var itemMaxTextView: TextView = view.findViewById(R.id.weekly_item_max)
    var itemMinTextView: TextView = view.findViewById(R.id.weekly_item_min)
    var itemIconImageView: ImageView = view.findViewById(R.id.weekly_item_current_icon)
    var itemPrecipTextView: TextView = view.findViewById(R.id.weekly_item_precipitation)
    var itemDateTextView: TextView = view.findViewById(R.id.weekly_item_date)

}