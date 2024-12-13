package com.dicoding.myevent.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.dicoding.myevent.databinding.FragmentNotificationBinding
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class NotificationsFragment : Fragment() {
//binding ini digunakan untuk menghubungkan layout dengan fragment
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
//ini adalah fungsi yang digunakan untuk menampilkan layout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
//layout ke fragment
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
//untuk menghapus layout
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}