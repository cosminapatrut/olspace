package com.orange.olsnasa.screens.globe

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.google.gson.Gson
import com.orange.domain.model.SatelliteAbove
import com.orange.olsnasa.R
import com.orange.olsnasa.screens.satellites.SatellitesViewModel
import gov.nasa.worldwind.WorldWind
import gov.nasa.worldwind.WorldWindow
import gov.nasa.worldwind.geom.LookAt
import gov.nasa.worldwind.geom.Offset
import gov.nasa.worldwind.geom.Position
import gov.nasa.worldwind.layer.RenderableLayer
import gov.nasa.worldwind.render.ImageSource
import gov.nasa.worldwind.shape.Placemark
import gov.nasa.worldwind.shape.PlacemarkAttributes
import org.koin.android.viewmodel.ext.android.viewModel

class PlacemarksPickingFragment : BasicGlobeFragment() {
    companion object {
        private const val ARG_SATELLITE = "PlacemarksPickingFragment:ARG_SATELLITE"
        private const val NORMAL_IMAGE_SCALE = 0.7
        private const val HIGHLIGHTED_IMAGE_SCALE = 0.5

        fun intent(satelliteAbove: String? = null): PlacemarksPickingFragment {
            return PlacemarksPickingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SATELLITE, satelliteAbove)
                }
            }
        }
    }

    private val satellitesViewModel by viewModel<SatellitesViewModel>()
    private var satellite: SatelliteAbove? = null

    override fun createWorldWindow(): WorldWindow {

        val wwd = super.createWorldWindow()

        // Create a RenderableLayer for placemarks and add it to the WorldWindow
        val placemarksLayer = RenderableLayer("Placemarks")
        wwd.layers.addLayer(placemarksLayer)
        // wwd.verticalExaggeration = 100.0
        wwd.fieldOfView = 45.0

        val sateliteExtras = arguments?.get(ARG_SATELLITE) as? String
        satellite = Gson().fromJson(
            sateliteExtras,
            SatelliteAbove::class.java
        )

        if (satellite != null) {
            //pinpoint satelite
            val satelite = Placemark(
                Position.fromDegrees(
                    satellite!!.satlat.toDouble(),
                    satellite!!.satlng.toDouble(),
                    satellite!!.satalt.toDouble()
                ),
                PlacemarkAttributes.createWithImageAndLeader(
                    ImageSource.fromResource(R.drawable.ic_satelite_v3)
                ).setImageScale(NORMAL_IMAGE_SCALE)
            ).apply {
                attributes.leaderAttributes.outlineWidth = 40f
            }

            placemarksLayer.addRenderable(satelite)
        } else {
            satellitesViewModel.getSatellites(44.4062823f, 26.0918518f, 2000L)
        }

        satellitesViewModel.data.observe(this,
            Observer {
                placemarksLayer.removeAll { renderable -> renderable.displayName != "Me" }

                it?.forEach { sateliteAbove ->

                    val satelite = Placemark(
                        Position.fromDegrees(
                            sateliteAbove.satlat.toDouble(),
                            sateliteAbove.satlng.toDouble(),
                            sateliteAbove.satalt.toDouble()
                        ),
                        PlacemarkAttributes.createWithImageAndLeader(
                            ImageSource.fromResource(R.drawable.ic_satelite_v3)
                        ).setImageScale(NORMAL_IMAGE_SCALE)
                    ).apply {
                        attributes.leaderAttributes.outlineWidth = 40f
                    }

                    placemarksLayer.addRenderable(satelite)
                }
                wwd.requestRedraw()

            })

        val myLocation = Placemark(
            Position.fromDegrees(44.4062823, 26.0918518, 0.0),
            PlacemarkAttributes.createWithImage(ImageSource.fromResource(R.drawable.ic_my_location)).setImageOffset(
                Offset.bottomCenter()
            ).setImageScale(HIGHLIGHTED_IMAGE_SCALE),
            "Me"
        )

        placemarksLayer.addRenderable(myLocation)

        val pos = myLocation.position
        val lookAt = LookAt().set(
            pos.latitude, pos.longitude, pos.altitude, WorldWind.ABSOLUTE,
            1e5 /*range*/, 0.0 /*heading*/, 80.0 /*tilt*/, 0.0 /*roll*/
        )

        wwd.navigator.setAsLookAt(wwd.globe, lookAt)
        return wwd
    }
}
