package com.dicoding.myevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.myevent.data.api.ApiClient
import com.dicoding.myevent.data.model.EventItem
import com.dicoding.myevent.data.model.EventListResponse
import com.dicoding.myevent.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter: EventListAdapter = EventListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.rvEvents.layoutManager = layoutManager

        fetchDicodingEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchDicodingEvents() {
        val client = context?.let {
            ApiClient.getApiService(it).getEvents(query = "Dicoding")
        }

        client?.enqueue(object : Callback<EventListResponse> {
            override fun onResponse(
                call: Call<EventListResponse>,
                response: Response<EventListResponse>
            ) {
                setEventList(response.body()?.listEvents?.map {
                    it ?: EventItem()
                } ?: emptyList())
            }

            override fun onFailure(call: Call<EventListResponse>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Fetch failed caused by ${t.cause}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun setEventList(eventList: List<EventItem>) = with(binding) {
        adapter.submitList(eventList)
        rvEvents.adapter = adapter
    }

}