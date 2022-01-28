package com.example.sirene

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Transformations.map
import com.example.sirene.fragment.MyMapFragment
import com.example.sirene.model.SirenDataBase
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CompanyDetailsActivity : AppCompatActivity(){
    private var myMapFragment : MyMapFragment? = null




    @SuppressLint("UseCompatLoadingForColorStateLists", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_details)


        val company : Company = (intent.getSerializableExtra("company") as Company)
        val tvCompanyName = findViewById<TextView>(R.id.tv_company_name)
        tvCompanyName.text = company.nom_raison_sociale
        val tvSiret = findViewById<TextView>(R.id.tv_siret)
        tvSiret.text = String.format(getString(R.string.siret), company.siret)
        val tvSiren = findViewById<TextView>(R.id.tv_siren)
        tvSiren.text = String.format(getString(R.string.siren), company.siren)
        val tvSiege = findViewById<TextView>(R.id.tv_siege)
        val image = findViewById<ImageView>(R.id.imageView)
        val yes : Drawable = resources.getDrawable(R.drawable.ic_yes)
        val no : Drawable = resources.getDrawable(R.drawable.ic_no)
        if(company.is_siege=="1"){
            image.setImageDrawable(yes)
        }
        else{
            image.setImageDrawable(no)
        }




        val tvAdresse = findViewById<TextView>(R.id.tv_address)
        var address = ""
        if(company.numero_voie != null){
            address += company.numero_voie + " "
        }
        if(company.indice_repetition != null){
            address += company.indice_repetition + " "
        }
        if(company.type_voie != null){
            address += company.type_voie + " "
        }
        if(company.libelle_voie != null){
            address += company.libelle_voie + " "
        }
        if(company.code_postal != null){
            address += company.code_postal + " "
        }
        if(company.libelle_commune != null){
            address += company.libelle_commune + " "
        }
        if(company.cedex != null){
            address += "cedex " + company.cedex + " "
        }

        tvAdresse.text = String.format(getString(R.string.address), address )

        val tvDomain = findViewById<TextView>(R.id.tvDomain)
        tvDomain.text = company.libelle_activite_principale

        val tvTypeLegal = findViewById<TextView>(R.id.tv_type)

        tvTypeLegal.setText(company.libelle_nature_juridique_entreprise)

        val shareButton = findViewById<Button>(R.id.ButtonShare)
        shareButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.share_content),
                company.nom_raison_sociale, company.libelle_commune) )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, ""))
        }
        val saveButton = findViewById<Button>(R.id.ButtonSave)
        val database = SirenDataBase.getDatabase(this)
        val saved : Drawable = resources.getDrawable(R.drawable.ic_is_saved)
        val not_saved : Drawable = resources.getDrawable(R.drawable.ic_not_saved)
        if(database.SaveDAO().is_saved(company.id as Long) != null){

            saveButton.setCompoundDrawablesWithIntrinsicBounds(null, saved, null, null)
        }
        else{
            saveButton.setCompoundDrawablesWithIntrinsicBounds(null, not_saved, null, null)

        }
        saveButton.setOnClickListener{
            var save = database.SaveDAO().is_saved(company.id as Long)
            if(save != null)
            {
                saveButton.setCompoundDrawablesWithIntrinsicBounds(null, not_saved, null, null)
                database.SaveDAO().delete(save)

            }
            else{

                saveButton.setCompoundDrawablesWithIntrinsicBounds(null, saved, null, null)
                save = Save(null, company.id)
                database.SaveDAO().create(save)
            }
        }


        if(!company.latitude.isNullOrEmpty() && !company.longitude.isNullOrEmpty()){

            val fragmentManager: FragmentManager = this.supportFragmentManager

            myMapFragment = fragmentManager.findFragmentById(R.id.map) as MyMapFragment

            val location = LatLng((company.latitude as String).toDouble(), (company.longitude as String).toDouble())
            val markerOptions = MarkerOptions()
            markerOptions.position(location)
            markerOptions.title(address)
            // Clear previously click position.
            myMapFragment!!.getMapAsync{

                it.clear()
                // Zoom the Marker
                it.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
                // Add Marker on Map
                it.addMarker(markerOptions)
            }
        }





    }

}