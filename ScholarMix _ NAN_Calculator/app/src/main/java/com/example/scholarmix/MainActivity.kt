package com.example.scholarmix

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scholarmix.databinding.ActivityMainpageBinding
import com.example.scholarmix.databinding.SplashScreenBinding

class MainActivity : AppCompatActivity() {

    // View bindings for splash screen and main page
    private lateinit var splashBinding: SplashScreenBinding
    private lateinit var mainBinding: ActivityMainpageBinding

    // storing the variable that user selected (subject and personality)
    private var selectedSubject: String? = null
    private var selectedPersonality: String? = null

    // Map to track which planets are zoomed in
    private val zoomedMap = mutableMapOf<Int, Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Showing the splash screen first
        splashBinding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        // Delayed for 5 seconds before loading the main screen
        Handler(Looper.getMainLooper()).postDelayed({
            // Loads the main layout after splash screen
            mainBinding = ActivityMainpageBinding.inflate(layoutInflater)
            setContentView(mainBinding.root)

            // Initialize all parts of the UI
            setupPlanetAnimations()
            setupInstructionPopup()
            setupPlanetSelections()
            setupMixButton()
        }, 5000)
    }

    // Animates the planets with floating motion using ObjectAnimator
    private fun setupPlanetAnimations() {
        val planets = listOf(
            mainBinding.science, mainBinding.business, mainBinding.art,
            mainBinding.analytical, mainBinding.empathic, mainBinding.creative
        )

        // Loads zoom in and out animations from res/anim folder
        val zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoomin)
        val zoomOut = AnimationUtils.loadAnimation(this, R.anim.zoomout)

        planets.forEach { planet ->
            // Animating the planets to float continuously
            ObjectAnimator.ofFloat(planet, "translationY", 0f, -20f, 0f).apply {
                duration = 3000
                repeatCount = ObjectAnimator.INFINITE
                start()
            }
            zoomedMap[planet.id] = false
        }
    }

    // Handles subject and personality planet selection
    private fun setupPlanetSelections() {
        // Map each subject button to its id name
        val subjectPlanets = mapOf(
            mainBinding.science to "Science",
            mainBinding.business to "Business",
            mainBinding.art to "Art"
        )

        // Map each personality button to its id name
        val personalityPlanets = mapOf(
            mainBinding.analytical to "Analytical",
            mainBinding.empathic to "Empathic",
            mainBinding.creative to "Creative"
        )

        val zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoomin)
        val zoomOut = AnimationUtils.loadAnimation(this, R.anim.zoomout)

        // When a subject is selected, it saves and apply zoom animation
        subjectPlanets.forEach { (view, subject) ->
            view.setOnClickListener {
                clearSelections(subjectPlanets.keys) // Removes previous selections
                selectedSubject = subject
                toggleZoom(view, zoomIn, zoomOut)
                Toast.makeText(this, "$subject Selected", Toast.LENGTH_SHORT).show()
            }
        }

        // When a personality is selected, it saves and apply zoom animation
        personalityPlanets.forEach { (view, personality) ->
            view.setOnClickListener {
                clearSelections(personalityPlanets.keys) // Remove previous selections
                selectedPersonality = personality
                toggleZoom(view, zoomIn, zoomOut)
                Toast.makeText(this, "$personality Selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Removes zoom from all given views ( when changing selection)
    private fun clearSelections(views: Collection<View>) {
        val zoomOut = AnimationUtils.loadAnimation(this, R.anim.zoomout)
        views.forEach { view ->
            if (zoomedMap[view.id] == true) {
                view.startAnimation(zoomOut)
                zoomedMap[view.id] = false
            }
            view.isSelected = false
        }
    }

    // Handles zoom in/out animation logic for a view
    private fun toggleZoom(view: View, zoomIn: android.view.animation.Animation, zoomOut: android.view.animation.Animation) {
        if (zoomedMap[view.id] == true) {
            view.startAnimation(zoomOut)
            zoomedMap[view.id] = false
        } else {
            view.startAnimation(zoomIn)
            zoomedMap[view.id] = true
        }
    }

    // Logic for the Mix button – shows a career result if both choices are selected
    private fun setupMixButton() {
        mainBinding.mixButton.setOnClickListener {
            // Ensure both subject and personality are selected
            if (selectedSubject == null || selectedPersonality == null) {
                Toast.makeText(this, "Please select one subject and one personality", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // checking the selected subject and personality and showing a matching career
            val career = getCareerResult(selectedSubject!!, selectedPersonality!!)
            if (career != null) {
                showCareerPopup(career)
            } else {
                Toast.makeText(this, "No matching career found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Matches subject + personality to a career result
    private fun getCareerResult(subject: String, personality: String): CareerModel? {
        return when ("$subject + $personality") {
            "Science + Analytical" -> CareerModel("Data Scientist", R.drawable.data, "Solves problems using data.", "You’ll love making sense of the unknown! 🎯")
            "Science + Empathic" -> CareerModel("Healthcare Professional", R.drawable.healthcare, "Helps people get better.", "Healing is your superpower! 🩺")
            "Science + Creative" -> CareerModel("Biotech Innovator", R.drawable.biotech, "Combines science and creativity.", "Invent the future of medicine! 🧬")
            "Business + Analytical" -> CareerModel("Financial Analyst", R.drawable.financial, "Analyzes financial data.", "Predict market trends! 📈")
            "Business + Empathic" -> CareerModel("HR Manager", R.drawable.hr, "Cares for employee wellbeing.", "People are your passion! 🤝")
            "Business + Creative" -> CareerModel("Brand Strategist", R.drawable.brand, "Shapes company identity.", "Create iconic brands! 💡")
            "Art + Analytical" -> CareerModel("Architect", R.drawable.architect, "Designs buildings with logic.", "Shape skylines with precision! 🏛️")
            "Art + Creative" -> CareerModel("Fashion Designer", R.drawable.fashion, "Designs clothes.", "Dress the world in style! 👗")
            "Art + Empathic" -> CareerModel("Art Therapist", R.drawable.art_therapy, "Uses art to heal emotions.", "Paint your way to peace! 🎨")
            else -> null
        }
    }

    // Shows a custom popup with career result
    private fun showCareerPopup(career: CareerModel) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_career_popup, null)

        // Linking career pop up UI with its id name
        val title = dialogView.findViewById<TextView>(R.id.careerTitle)
        val image = dialogView.findViewById<ImageView>(R.id.careerImage)
        val desc = dialogView.findViewById<TextView>(R.id.careerDescription)
        val fact = dialogView.findViewById<TextView>(R.id.careerFact)
        val closeBtn = dialogView.findViewById<Button>(R.id.closeBtn)
        val tryAgainBtn = dialogView.findViewById<Button>(R.id.tryAgainBtn)

        // Setting values in popup
        title.text = career.title
        image.setImageResource(career.imageRes)
        desc.text = career.description
        fact.text = career.funFact

        // Building and shows the popup
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Close button
        closeBtn.setOnClickListener { dialog.dismiss() }

        // Reset selections when "Try Again button" is clicked
        tryAgainBtn.setOnClickListener {
            dialog.dismiss()
            selectedSubject = null
            selectedPersonality = null
            clearSelections(listOf(
                mainBinding.science, mainBinding.business, mainBinding.art,
                mainBinding.analytical, mainBinding.empathic, mainBinding.creative
            ))
        }

        dialog.show()
    }

    // Data class to hold details about each career suggestion
    data class CareerModel(
        val title: String,
        val imageRes: Int,
        val description: String,
        val funFact: String
    )

    // Shows instruction popup with slide-up animation
    private fun setupInstructionPopup() {
        mainBinding.instructButton.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_instructions, null)
            dialogView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))

            val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(true)
                .create()

            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val closeBtn = dialogView.findViewById<Button>(R.id.close_button)
            closeBtn.setOnClickListener { dialog.dismiss() }

            dialog.show()
        }
    }
}
