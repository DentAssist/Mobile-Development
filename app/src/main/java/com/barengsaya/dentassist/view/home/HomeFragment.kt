package com.barengsaya.dentassist.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.barengsaya.dentassist.R
import com.barengsaya.dentassist.databinding.FragmentHomeBinding
import com.barengsaya.dentassist.view.ViewModelFactory
import com.barengsaya.dentassist.view.adapter.AdapterSlider
import com.barengsaya.dentassist.view.scan.CameraActivity
import com.barengsaya.dentassist.view.clinik.ClinikActivity
import com.barengsaya.dentassist.view.item.ImageItem
import com.barengsaya.dentassist.view.informasi.InformasiActivity
import com.barengsaya.dentassist.view.obat.ObatActivity
import com.barengsaya.dentassist.view.welcome.WelcomeActivity
import com.bumptech.glide.Glide
import java.util.UUID

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels { ViewModelFactory.getInstance(requireContext()) }

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

        observeViewModel()

        homeViewModel.getSession().observe(viewLifecycleOwner) { user ->
            binding.progressBar.visibility = View.VISIBLE
            if (user.isLogin) {
                homeViewModel.fetchUserProfile(user.idUser)
            } else {
                startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                activity?.finish()
            }
        }k

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

    private fun observeViewModel() {
        homeViewModel.userProfile.observe(viewLifecycleOwner) { userResponse ->
            userResponse?.data?.let { userData ->
                binding.username.text = getString(R.string.username, userData.username)
                Glide.with(this)
                    .load(userData.profileImage)
                    .placeholder(R.drawable.profile_default)
                    .into(binding.userprofile)
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupViewPager() {
        val imageList = arrayListOf(
            ImageItem(
                UUID.randomUUID().toString(),
                getString(R.string.https_img_antaranews_com_cache_1200x800_2018_11_kunir_jpg_webp)
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                getString(R.string.https_res_cloudinary_com_dk0z4ums3_image_upload_v1708999329_attached_image_bentuk_gigi_normal_dan_tips_menjaga_kesehatannya_0_alodokter_jpg)
            ),

            ImageItem(
                UUID.randomUUID().toString(),
                getString(R.string.https_img_cdn_medkomtek_com_isnmni_jhczvdymt_d2nwov6kv8_730x411_smart_filters_quality_100_format_webp_article_epb6t_faes9wetzvxrmsf_original_030227300_1520847207_berbagai_kondisi_rongga_mulut_yang_tidak_boleh_anda_abaikan_by_irina_bg_shutterstock_jpg_w_128_q_100)
            ),
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
