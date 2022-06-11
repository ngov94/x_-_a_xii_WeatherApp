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

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val weeklyList = ArrayList<Daily>()
    val adapter = WeeklyAdapter(weeklyList)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val intr = RetroApiInterface.create()
        val dao = WeatherDatabase.getInstance(this.requireContext())?.weatherDao()!!
        val repo = WeatherRepository(intr, dao)
        val vm = WeatherViewModel(repo)


        _binding = FragmentWeeklyBinding.inflate(inflater, container, false)
        val root: View = binding.root




        //Date Range
        var dateNow = SimpleDateFormat("MMMM d").format(Date())

        var localnow = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        var localWeek = Date.from(localnow.plusDays(8).atStartOfDay(ZoneId.systemDefault()).toInstant())
        var dateWeekfromNow = SimpleDateFormat("MMMM d").format(localWeek)

        binding.dateRangeLabel.text = dateNow +" - "+ dateWeekfromNow

        setFragmentResultListener("key_to_weekly"){key,result ->
            weeklyList.addAll(result.get("daily") as List<Daily>)
            adapter.notifyDataSetChanged()
            binding.futureRecycler.adapter = adapter
            binding.futureRecycler.layoutManager = LinearLayoutManager(activity)
        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}