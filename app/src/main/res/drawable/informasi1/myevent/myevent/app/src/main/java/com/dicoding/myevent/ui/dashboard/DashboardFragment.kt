package com.dicoding.myevent.ui.dashboard


import com.dicoding.myevent.databinding.FragmentDashboardBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


class DashboardFragment : Fragment() {
//binding ini digunakan untuk menghubungkan layout dengan fragment
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )
    : View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return binding.root
    }
//untuk menghapus layout
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}