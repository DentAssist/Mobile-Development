package com.barengsaya.dentassist.view.predict

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.barengsaya.dentassist.MainActivity
import com.barengsaya.dentassist.R
import com.barengsaya.dentassist.data.api.response.PredictionData
import com.barengsaya.dentassist.databinding.ActivityPredictBinding
import com.bumptech.glide.Glide

@Suppress("DEPRECATION", "NAME_SHADOWING")
class PredictActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setNavigationOnClickListener {
            onBackPressed()
        }

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

                titleProduct.text = data.products?.getOrNull(0)?.title?.let {
                    Html.fromHtml("<b>$it yang mungkin cocok untuk Anda:</b>")
                } ?: run {
                    ""
                }
                titleArticle.text = data.articles?.getOrNull(0)?.title?.let {
                    Html.fromHtml("<b>$it terkait dengan penyakit Anda:</b>")
                } ?: run {
                    ""
                }
                titleClinic.text = data.clinic?.title?.let {
                    Html.fromHtml("<b>$it mulut yang disarankan untuk Anda:</b>")
                } ?: run {
                    ""
                }

                Glide.with(this@PredictActivity)
                    .load(data.signedUrl)
                    .placeholder(R.drawable.image_preview)
                    .error(R.drawable.image_preview)
                    .into(predictImage)

                data.products?.let { products ->
                    binding.recyclerProducts.layoutManager = LinearLayoutManager(this@PredictActivity)
                    val products = data.products.filterNotNull() ?: emptyList()
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
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        super.onBackPressed()
    }

}
