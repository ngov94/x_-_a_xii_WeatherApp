package com.example.weatherapp.ui.weekly


import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.data.APIResponse.Daily
import com.example.weatherapp.databinding.FragmentWeeklyBinding
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


class WeeklyFragment : Fragment() {

    private var _binding: FragmentWeeklyBinding? = null

    private val binding get() = _binding!!

    val weeklyList = ArrayList<Daily>()
    val adapter = WeeklyAdapter(weeklyList)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

}