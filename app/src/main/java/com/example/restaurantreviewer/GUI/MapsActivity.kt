package com.example.restaurantreviewer.GUI

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.restaurantreviewer.Model.Restaurant
import com.example.restaurantreviewer.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val LOCATION_PERMISSION_REQUEST = 99
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var selectedRestaurant: Restaurant
    private lateinit var lastKnownLocation: Location
    private val markerMap: HashMap<Marker, Restaurant> = HashMap()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLocationPermission()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(55.5129136,9.9206371), 6.5F))

        val restaurants = getRestaurants()
        for (restaurant in restaurants) {
            val marker = mMap.addMarker(MarkerOptions().position(LatLng(restaurant.latitude, restaurant.longitude)).title(restaurant.name))
            markerMap[marker] = restaurant
        }

        mMap.setOnMarkerClickListener { marker ->
            btnOpenLocation.isEnabled = true
            marker.showInfoWindow()
            btnOpenLocation.text = "Open ${marker.title}"
            val filtered = markerMap[marker]?.id
            selectedRestaurant = filtered?.let { getClickedRestaurant(it) }!!
            true
        }
    }

    private fun getRestaurants(): ArrayList<Restaurant> {
        // This should come from intent
        val restaurants = ArrayList<Restaurant>()
        restaurants.add(
            Restaurant(1, "McDonalds", "Kongensgade 40", 55.466022876684434, 8.451934554143868, "10-00"),
        )
        restaurants.add(
                Restaurant(2, "McDonalds", "Kongensgade 41", 56.466022876684434, 8.451934554143868, "10-00"),
        )

        return restaurants
    }

    private fun getClickedRestaurant(id: Int): Restaurant {
        val restaurants = getRestaurants()
        val foundRestaurant = restaurants.single { r -> r.id == id }
        if (foundRestaurant != null) {
            return foundRestaurant
        } else {
            throw Resources.NotFoundException("Restaurant not found")
        }
    }

    fun onClickOpenLocation(view: View) {
        // This should send intent to detail view
        Log.e("xyz", selectedRestaurant.toString())
    }

    fun onClickGetLocation(view: View) {
        // Demo how to get current device location
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    lastKnownLocation = location
                    Log.e("xyz", "lat: ${location.latitude}, lng: ${location.longitude}")
                }
            }
    }


    /**
     * Check permission of device location
     */
    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
        }
    }

    /**
     * Ask for permission of device location and enables location
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode === LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return
                }
                mMap.isMyLocationEnabled = true
            } else {
                Log.e("xyz", "Permission denied")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}