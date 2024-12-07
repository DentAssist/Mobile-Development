package com.barengsaya.dentassist.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.barengsaya.dentassist.R
import com.barengsaya.dentassist.databinding.FragmentHomeBinding
import com.barengsaya.dentassist.view.adapter.AdapterSlider
import com.barengsaya.dentassist.view.scan.CameraActivity
import com.barengsaya.dentassist.view.clinik.ClinikActivity
import com.barengsaya.dentassist.view.item.ImageItem
import com.barengsaya.dentassist.view.informasi.InformasiActivity
import com.barengsaya.dentassist.view.obat.ObatActivity
import java.util.UUID

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private val params = ViewGroup.MarginLayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8, 0, 8, 0)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                searchView.hide()
                Toast.makeText(requireContext(), searchView.text, Toast.LENGTH_SHORT).show()
                false
            }
            btnKlinik.setOnClickListener {
                startActivity(Intent(requireContext(), ClinikActivity::class.java))
            }

            btnObat.setOnClickListener {
                startActivity(Intent(requireContext(), ObatActivity::class.java))
            }

            btnCamera.setOnClickListener {
                startActivity(Intent(requireContext(), CameraActivity::class.java))
            }

            btnInformasi.setOnClickListener {
                startActivity(Intent(requireContext(), InformasiActivity::class.java))
            }

            setupViewPager()
        }
    }

    private fun setupViewPager() {
        val imageList = arrayListOf(
            ImageItem(
                UUID.randomUUID().toString(),
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1652143049/attached_image/sariawan-0-alodokter.jpg"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1652143049/attached_image/sariawan-0-alodokter.jpg"
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1652143049/attached_image/sariawan-0-alodokter.jpg"
            )
        )

        val adapterSlider = AdapterSlider()
        binding.viewpager2.adapter = adapterSlider
        adapterSlider.submitList(imageList)

        val dotsImage = Array(imageList.size) { ImageView(requireContext()) }

        dotsImage.forEach {
            it.setImageResource(R.drawable.non_active_dot)
            binding.slideDotLL.addView(it, params)
        }

        dotsImage[0].setImageResource(R.drawable.active_dot)

        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dotsImage.mapIndexed { index, imageView ->
                    if (position == index) {
                        imageView.setImageResource(R.drawable.active_dot)
                    } else {
                        imageView.setImageResource(R.drawable.non_active_dot)
                    }
                }
                super.onPageSelected(position)
            }
        }
        binding.viewpager2.registerOnPageChangeCallback(pageChangeListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewpager2.unregisterOnPageChangeCallback(pageChangeListener)
        _binding = null
    }
}
