package com.barengsaya.dentassist.view.predict

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barengsaya.dentassist.R
import com.barengsaya.dentassist.data.api.response.Clinic
import com.barengsaya.dentassist.databinding.ItemClinicPredictBinding
import com.bumptech.glide.Glide

class PredictClinicAdapter(
    private val clinics: List<Clinic>
) : RecyclerView.Adapter<PredictClinicAdapter.ClinicViewHolder>() {

    class ClinicViewHolder(private val binding: ItemClinicPredictBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clinic: Clinic) {
            binding.namaKlinik.text = clinic.name
            binding.namaKlinik.text = clinic.address
            binding.kotaKlinik.text = clinic.city
            Glide.with(itemView.context)
                .load(clinic.photo)
                .placeholder(R.drawable.image_preview)
                .error(R.drawable.image_preview)
                .into(binding.gambarKlinik)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicViewHolder {
        val binding = ItemClinicPredictBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClinicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClinicViewHolder, position: Int) {
        val clinic = clinics[position]
        holder.bind(clinic)
    }

    override fun getItemCount() = clinics.size
}
