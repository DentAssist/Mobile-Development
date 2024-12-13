package com.barengsaya.dentassist.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.barengsaya.dentassist.data.pref.UserPreference
import com.barengsaya.dentassist.data.pref.dataStore
import com.barengsaya.dentassist.databinding.FragmentHistoryBinding
import com.barengsaya.dentassist.view.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: HistoryAdapter
    private lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreference = UserPreference.getInstance(requireContext().dataStore)

        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(HistoryViewModel::class.java)

        adapter = HistoryAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            val user = userPreference.getSession().first()
            viewModel.getHistory(user.idUser ).observe(viewLifecycleOwner) { history ->
                adapter.submitList(history.data)
            }
        }
    }
}
