package com.barengsaya.dentassist.view.predict

import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.barengsaya.dentassist.R
import com.barengsaya.dentassist.data.api.response.PredictionData
import com.barengsaya.dentassist.databinding.ActivityPredictBinding
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
class PredictActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val predictionData = intent.getParcelableExtra<PredictionData>("PREDICT_RESULT")

        predictionData?.let { data ->
            with(binding) {
                predictLabel.text = "${data.label}"
                predictConfidence.text = Html.fromHtml(
                    "<b>Akurasi: </b>${String.format("%.2f", data.confidenceScore)}%"
                )

                predictExplanation.text =Html.fromHtml(
                    "<b> Deskripsi: </b> <br><br> ${data.explanation }"
                )

                predictSuggestion.text = "${data.suggestion }"



                Glide.with(this@PredictActivity)
                    .load(data.signedUrl)
                    .placeholder(R.drawable.image_preview)
                    .error(R.drawable.image_preview)
                    .into(predictImage)

                data.products?.let { products ->
                    binding.recyclerProducts.layoutManager = LinearLayoutManager(this@PredictActivity)
                    val products = data.products?.filterNotNull() ?: emptyList()
                    binding.recyclerProducts.adapter = PredictProductAdapter(products)

                }
                data.articles?.let { articles ->
                    binding.recyclerArticles.layoutManager = LinearLayoutManager(this@PredictActivity)
                    val articlesList = articles.filterNotNull()
                    binding.recyclerArticles.adapter = PredictArticleAdapter(articlesList)
                }

                data.clinic?.let { clinic ->
                    binding.recyclerClinics.layoutManager = LinearLayoutManager(this@PredictActivity)
                    val clinicList = listOf(clinic)
                    binding.recyclerClinics.adapter = PredictClinicAdapter(clinicList)
                }
            }
        } ?: run {
            Toast.makeText(this, "Data tidak tersedia!", Toast.LENGTH_SHORT).show()
        }
    }

}
