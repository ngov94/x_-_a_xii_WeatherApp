package com.example.weatherapp.ui.weekly


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.APIResponse.Daily
import com.example.weatherapp.DataBase.WeatherDatabase
import com.example.weatherapp.R
import com.example.weatherapp.RetroApiInterface
import com.example.weatherapp.WeatherRepository
import com.example.weatherapp.WeatherViewModel
import com.example.weatherapp.databinding.FragmentWeeklyBinding
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList


class WeeklyFragment : Fragment() {

    private var _binding: FragmentWeeklyBinding? = null

    private val binding get() = _binding!!

    private val weeklyList = ArrayList<Daily>()
    private val adapter = WeeklyAdapter(weeklyList)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWeeklyBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // Weekly Recycler View
        binding.futureRecycler.adapter = adapter
        binding.futureRecycler.layoutManager = LinearLayoutManager(activity)

        setFragmentResultListener("key_to_weekly"){key,result ->
            weeklyList.clear()
            weeklyList.addAll(result.get("daily") as List<Daily>)
            adapter.notifyDataSetChanged()

            //Date Range on top
            var dateNow = SimpleDateFormat("MMMM d").format(weeklyList.first().dt.toLong()*1000)
            var dateWeekfromNow = SimpleDateFormat("MMMM d").format(weeklyList.last().dt.toLong()*1000)

            binding.dateRangeLabel.text = dateNow +" - "+ dateWeekfromNow

        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}