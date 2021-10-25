package com.example.gps

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.gps.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

       binding.btnupdate.setOnClickListener{
            updateLocation()
        }
    }

    fun actualizarLocacion(){
        fusedLocationClient=LocationServices.getFusedLocationProviderClient(this)
         if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
         {
                Toast.makeText(this,"has permission", Toast.LENGTH_SHORT).show()
                Log.d("locationpermiso", "has permiso")
                fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                    binding.tvCoordenadas.setText("${location?.latitude}, ${location?.longitude}")
                }
         }else{
             Toast.makeText(this,"does not have permission", Toast.LENGTH_SHORT).show()
             Log.d("locationpermiso", "does not have permiso")
         }

    }
    fun updateLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Doesn't have permission",Toast.LENGTH_SHORT).show()
            Log.d("LocationPermissions", "Doesn't have permission")
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),1)
            }
            return
        }else{
            Toast.makeText(this,"Has permission",Toast.LENGTH_SHORT).show()

            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                if (location != null){
                Log.d("LocationPermissions", "Success ${location?.latitude}, ${location?.longitude}")
                val geocoder = Geocoder(this, Locale.getDefault())
                val direccion: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    Log.d("location permisos", "${direccion}")
                    binding.longitud.setText("${location.longitude}")
                    binding.altitud.setText("${location.latitude}")
                    binding.tvCoordenadas.setText("${direccion[0].locality}, ${direccion[0].adminArea} , ${direccion[0].countryName}")
                }


            }
            Log.d("LocationPermissions", "Has permission ")
            //val square = { number: Int -> number * number}
            //val sixteen = square(4)
            //val sixteen: Int? = null
        }
    }
}