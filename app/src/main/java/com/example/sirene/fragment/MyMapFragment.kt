package com.example.sirene.fragment

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MyMapFragment : SupportMapFragment(), OnMapReadyCallback {
    public var googleMap: GoogleMap? = null
    override fun onMapReady(gmap: GoogleMap) {
        googleMap = gmap



    }

    init {
        getMapAsync(this)

    }

}