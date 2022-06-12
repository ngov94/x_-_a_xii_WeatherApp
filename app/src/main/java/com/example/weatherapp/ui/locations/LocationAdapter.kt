package com.example.weatherapp.ui.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.APIResponse.AllWeather
import com.example.weatherapp.APIResponse.Daily
import com.example.weatherapp.APIResponse.LocationWeather
import com.example.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class LocationAdapter(private val locationList: List<LocationWeather>) : RecyclerView.Adapter<ViewHolder>(){

    private lateinit var itemLongListener: OnItemLongClickListener

    interface OnItemLongClickListener{
        fun onItemLongClick (itemView: View)
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener){
        itemLongListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val locationItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.limited_favorites_inflatable, parent, false)
        return ViewHolder(locationItemView, itemLongListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = locationList[position]


        holder.itemCurTextView.text = item.weather?.current?.temp?.roundToInt().toString()+ "°"
        holder.itemMaxTextView.text = item.weather?.daily?.first()?.temp?.max?.roundToInt().toString()+ "°"
        holder.itemMinTextView.text = item.weather?.daily?.first()?.temp?.min?.roundToInt().toString()+ "°"
        holder.itemPrecipTextView.text = (item.weather?.daily?.first()?.pop?.times(100))?.roundToInt().toString()+ "%"
        holder.itemLocationTextView.text = item.favLocations.placeName

        //Will not be visible, passing for deletion
        holder.itemLocationLatTextView.text = item.favLocations.latitude
        holder.itemLocationLongTextView.text = item.favLocations.longitude
        holder.itemLocationIdTextView.text = item.favLocations.id.toString()


        var icon = when (item.weather?.daily?.first()?.weather?.first()?.icon){
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
        return locationList.size
    }


}

class ViewHolder(view: View, longListener: LocationAdapter.OnItemLongClickListener) : RecyclerView.ViewHolder(view) {

    var itemCurTextView: TextView = view.findViewById(R.id.fav_item_currtemp)
    var itemMaxTextView: TextView = view.findViewById(R.id.fav_item_maxtemp)
    var itemMinTextView: TextView = view.findViewById(R.id.fav_item_mintemp)
    var itemIconImageView: ImageView = view.findViewById(R.id.fav_item_icon)
    var itemPrecipTextView: TextView = view.findViewById(R.id.fav_item_pop)
    var itemLocationTextView: TextView = view.findViewById(R.id.fav_item_location)
    var itemLocationLatTextView: TextView = view.findViewById(R.id.fav_item_location_lat)
    var itemLocationLongTextView: TextView = view.findViewById(R.id.fav_item_location_long)
    var itemLocationIdTextView: TextView = view.findViewById(R.id.fav_item_location_id)

    init{
        view.setOnLongClickListener {
            longListener.onItemLongClick((itemView))
            return@setOnLongClickListener true
        }
    }
}
