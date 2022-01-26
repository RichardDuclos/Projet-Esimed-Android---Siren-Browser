package com.example.sirene

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


    @SuppressLint("UseCompatLoadingForColorStateLists", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_details)


        val company : Company = (intent.getSerializableExtra("company") as Company)
        val tvCompanyName = findViewById<TextView>(R.id.tv_company_name)
        tvCompanyName.text = company.nom_raison_sociale
        val tvSiret = findViewById<TextView>(R.id.tv_siret)
        tvSiret.text = String.format(getString(R.string.siret), company.siret)
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
        /*val arguments = Bundle()
        arguments.putDouble("latitude", (company.latitude as String).toDouble())
        arguments.putDouble("longitude", (company.longitude as String).toDouble())

        val fragmentManager: FragmentManager = this.supportFragmentManager
        myMapFragment = fragmentManager.findFragmentById(R.id.map) as MyMapFragment
        myMapFragment!!.arguments = arguments*/


    }

}