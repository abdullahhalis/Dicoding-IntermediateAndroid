package com.dicoding.mystory.ui.map

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.dicoding.mystory.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.dicoding.mystory.databinding.ActivityMapsBinding
import com.dicoding.mystory.utils.StoryViewModelFactory
import com.dicoding.mystory.utils.showLoading
import com.google.android.gms.maps.model.MapStyleOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val mapsViewModel: MapsViewModel by viewModels { StoryViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapsViewModel.getAllStoriesWithLocation()
        mapsViewModel.isLoading.observe(this){
            binding.progressBar.showLoading(it)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true

        mapsViewModel.listStoryWithLocation.observe(this){
            it.forEach{ data ->
                if (data.lat != null && data.lon != null) {
                    val latLng = LatLng(data.lat, data.lon)
                    mMap.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title(data.name)
                            .snippet(data.description)
                    )
                }
            }
            val latLng = LatLng(it[0].lat!!, it[0].lon!!)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        }
        setMapStyle()
    }

    private fun setMapStyle() {
        try {
            val succsess =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!succsess) {
                Log.e(TAG, "Style parsing failed")
            }
        }catch (exception: Resources.NotFoundException){
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }
    companion object{
        private const val TAG = "MapsActivity"
    }
}