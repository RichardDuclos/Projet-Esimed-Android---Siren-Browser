package com.example.sirene.fragment

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MyMapFragment(       ) : SupportMapFragment(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    var lat: Double? = null
    var long: Double? = null

    override fun onMapReady(gmap: GoogleMap) {
        googleMap = gmap

        println("mapready")
        val location = LatLng(lat as Double, long as Double)


        //googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(vietnam))
        val markerOptions = MarkerOptions()
        markerOptions.position(location)
        markerOptions.title(location.latitude.toString() + " : " + location.longitude)
        // Clear previously click position.
        googleMap!!.clear()
        // Zoom the Marker
        googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
        // Add Marker on Map
        googleMap!!.addMarker(markerOptions)

        //}
    }

    init {
        getMapAsync(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("oncreate")

        val bundle : Bundle? = this.arguments
        if(bundle != null){
            lat = bundle.getDouble("latitude", 1.0)
            long = bundle.getDouble("longitude", 1.0)
        }
    }
}