package com.example.globalessenceapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CountryDetailActivity : AppCompatActivity() {

    private lateinit var countryImageView: ImageView
    private lateinit var countryNameTextView: TextView
    private lateinit var countryDescriptionTextView: TextView
    private lateinit var capitalTextView: TextView
    private lateinit var populationTextView: TextView
    private lateinit var languageTextView: TextView
    private lateinit var currencyTextView: TextView
    private lateinit var funFactTextView: TextView
    private lateinit var exploreMoreButton: Button
    private lateinit var closeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)

        // Initialize views
        initializeViews()

        // Getting the selected country name
        val countryName = intent.getStringExtra("country_name") ?: "Unknown"

        // Loading country details
        setCountryDetails(countryName)

        exploreMoreButton.setOnClickListener {
            val url = getExploreMoreUrl(countryName).trim()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)  // optional but sometimes helps

            val resolvedActivity = intent.resolveActivity(packageManager)
            if (resolvedActivity != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "No app found to open the link: $url", Toast.LENGTH_LONG).show()
                Log.e("CountryDetail", "No app found to handle URL: $url")
            }
        }

        // Close button finishes the activity
        closeButton.setOnClickListener {
            finish()
        }
    }

    private fun initializeViews() {
        countryImageView = findViewById(R.id.countryImage)
        countryNameTextView = findViewById(R.id.countryName)
        countryDescriptionTextView = findViewById(R.id.countryDescription)
        capitalTextView = findViewById(R.id.capitalText)
        populationTextView = findViewById(R.id.populationText)
        languageTextView = findViewById(R.id.languageText)
        currencyTextView = findViewById(R.id.currencyText)
        funFactTextView = findViewById(R.id.funFactText)
        exploreMoreButton = findViewById(R.id.btnExploreMore)
        closeButton = findViewById(R.id.btnClose)
    }

    private fun setCountryDetails(countryName: String) {
        when (countryName) {
            "USA" -> {
                setCountryData(
                    R.drawable.usa,
                    "United States",
                    "The United States of America is known for its cultural diversity, innovation, and iconic landmarks like the Statue of Liberty and the Grand Canyon.",
                    "Washington, D.C.",
                    "331 million",
                    "English",
                    "US Dollar",
                    "The U.S. has the world’s largest economy!"
                )
            }
            "India" -> {
                setCountryData(
                    R.drawable.india,
                    "India",
                    "India, the world’s largest democracy, is known for its rich culture, history, and landmarks like the Taj Mahal and the Himalayas.",
                    "New Delhi",
                    "1.3 billion",
                    "Hindi, English",
                    "Indian Rupee",
                    "India is the birthplace of yoga."
                )
            }
            "Japan" -> {
                setCountryData(
                    R.drawable.japan,
                    "Japan",
                    "Japan is an island country in East Asia known for its technological innovations, historical temples, and the cherry blossoms.",
                    "Tokyo",
                    "126 million",
                    "Japanese",
                    "Japanese Yen",
                    "Japan is home to more than 200 volcanoes!"
                )
            }
            "UAE" -> {
                setCountryData(
                    R.drawable.uae,
                    "United Arab Emirates",
                    "The UAE is a country in the Middle East, known for its luxurious lifestyle, the Burj Khalifa, and traditional Bedouin culture.",
                    "Abu Dhabi",
                    "9 million",
                    "Arabic",
                    "UAE Dirham",
                    "Dubai is home to the world’s tallest building, the Burj Khalifa."
                )
            }
            "Canada" -> {
                setCountryData(
                    R.drawable.canada,
                    "Canada",
                    "Canada is known for its natural beauty, from the Rocky Mountains to the forests of the west, as well as its multicultural population.",
                    "Ottawa",
                    "37 million",
                    "English, French",
                    "Canadian Dollar",
                    "Canada has the longest coastline in the world!"
                )
            }
            "Australia" -> {
                setCountryData(
                    R.drawable.australia,
                    "Australia",
                    "Australia is a large island nation known for its Great Barrier Reef, the Outback, and unique wildlife like kangaroos.",
                    "Canberra",
                    "25 million",
                    "English",
                    "Australian Dollar",
                    "Australia is home to the world's longest fence!"
                )
            }
            "UK" -> {
                setCountryData(
                    R.drawable.uk,
                    "United Kingdom",
                    "The UK is known for its royal family, historical landmarks like the Tower of London, and its global influence in arts and politics.",
                    "London",
                    "67 million",
                    "English",
                    "Pound Sterling",
                    "The UK has the world’s oldest underground railway system."
                )
            }
            "Brazil" -> {
                setCountryData(
                    R.drawable.brazil,
                    "Brazil",
                    "Brazil is famous for its vibrant carnival, the Amazon Rainforest, and beautiful beaches like Copacabana.",
                    "Brasília",
                    "213 million",
                    "Portuguese",
                    "Brazilian Real",
                    "Brazil is home to the largest rainforest in the world!"
                )
            }
            "Egypt" -> {
                setCountryData(
                    R.drawable.egypt,
                    "Egypt",
                    "Egypt is known for its ancient civilization, the pyramids, the Nile River, and a rich history of pharaohs.",
                    "Cairo",
                    "104 million",
                    "Arabic",
                    "Egyptian Pound",
                    "The Great Pyramid of Giza is one of the Seven Wonders of the Ancient World."
                )
            }
            "Philippines" -> {
                setCountryData(
                    R.drawable.philippines,
                    "Philippines",
                    "The Philippines is a Southeast Asian nation known for its stunning beaches, islands, and rich cultural heritage.",
                    "Manila",
                    "113 million",
                    "Filipino, English",
                    "Philippine Peso",
                    "The Philippines is made up of over 7,000 islands!"
                )
            }
            else -> {
                setCountryData(
                    R.drawable.sample_country,
                    "Country Not Found",
                    "Details about this country are not available.",
                    "Unknown",
                    "Unknown",
                    "Unknown",
                    "Unknown",
                    "No fun fact available."
                )
            }
        }
    }

    private fun setCountryData(
        imageResId: Int,
        name: String,
        description: String,
        capital: String,
        population: String,
        languages: String,
        currency: String,
        funFact: String
    ) {
        countryImageView.setImageResource(imageResId)
        countryImageView.contentDescription = "$name Image"
        countryNameTextView.text = name
        countryDescriptionTextView.text = description
        capitalTextView.text = "🗽 Capital: $capital"
        populationTextView.text = "👥 Population: $population"
        languageTextView.text = "🗣 Language: $languages"
        currencyTextView.text = "💰 Currency: $currency"
        funFactTextView.text = "✨ Fun Fact: $funFact"
    }

    private fun getExploreMoreUrl(countryName: String): String {
        return when (countryName) {
            "USA" -> "https://www.britannica.com/place/United-States"
            "India" -> "https://www.incredibleindia.gov.in/en"
            "Japan" -> "https://www.japan.travel/en/us/"
            "UAE" -> "https://u.ae/en/information-and-services/visiting-and-exploring-the-uae"
            "Canada" -> "https://travel.destinationcanada.com/en-ca"
            "Australia" -> "https://www.australia.com/en"
            "UK" -> "https://www.visitbritainshop.com/world/en/experiences-in-london-and-the-uk/attractions-and-tours"
            "Brazil" -> "https://visitbrasil.com/en/"
            "Egypt" -> "https://www.experienceegypt.eg/en"
            "Philippines" -> "https://www.lonelyplanet.com/destinations/philippines"
            else -> "https://en.wikipedia.org/wiki/Main_Page"
        }
    }

}

