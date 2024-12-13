package com.barengsaya.dentassist.view.clinik

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barengsaya.dentassist.R
import com.barengsaya.dentassist.data.api.response.DataItem
import com.barengsaya.dentassist.databinding.ItemKlinikBinding
import com.bumptech.glide.Glide

class ClinikAdapter(private val clinics: List<DataItem>) : RecyclerView.Adapter<ClinikAdapter.ClinicViewHolder>() {

    inner class ClinicViewHolder(private val binding: ItemKlinikBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clinic: DataItem) {
            binding.apply {
                namaKlinik.text = clinic.name
                kotaKlinik.text = clinic.city
                Log.d("ClinikAdapter", "Memuat gambar dari URL: ${clinic.photo}")
                Glide.with(itemView.context)
                    .load(clinic.photo)
                    .error(R.drawable.image_preview)
                    .into(gambarKlinik)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicViewHolder {
        val binding = ItemKlinikBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClinicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClinicViewHolder, position: Int) {
        clinics[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int = clinics.size
}
