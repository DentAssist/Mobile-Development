package com.barengsaya.dentassist.view.predict

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barengsaya.dentassist.R
import com.barengsaya.dentassist.data.api.response.Clinic
import com.barengsaya.dentassist.databinding.ItemClinicPredictBinding
import com.bumptech.glide.Glide

class PredictClinicAdapter(
    clinics: List<Clinic>
) : RecyclerView.Adapter<PredictClinicAdapter.ClinicViewHolder>() {

    private val filteredClinics = clinics.filter {
        !it.name.isNullOrEmpty() && !it.address.isNullOrEmpty() && !it.city.isNullOrEmpty() && !it.photo.isNullOrEmpty()
    }

    class ClinicViewHolder(private val binding: ItemClinicPredictBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clinic: Clinic) {
            binding.namaKlinik.text = clinic.name
            binding.kotaKlinik.text = clinic.city
            Glide.with(itemView.context)
                .load(clinic.photo)
                .placeholder(R.drawable.image_preview)
                .error(R.drawable.image_preview)
                .into(binding.gambarKlinik)

            binding.clinik.setOnClickListener {
                val linkMaps = clinic.linkMaps
                if (!linkMaps.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkMaps))
                    it.context.startActivity(intent)
                }
            }
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
        val clinic = filteredClinics[position]
        holder.bind(clinic)
    }

    override fun getItemCount() = filteredClinics.size
}
