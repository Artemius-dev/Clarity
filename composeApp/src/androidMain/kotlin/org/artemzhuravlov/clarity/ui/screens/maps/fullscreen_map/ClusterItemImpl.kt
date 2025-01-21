package org.artemzhuravlov.clarity.ui.screens.maps.fullscreen_map

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class ClusterItemImpl(val location: LatLng, val name: String) : ClusterItem {
    override fun getPosition(): LatLng = location

    override fun getTitle(): String = name

    override fun getSnippet(): String? = null

    override fun getZIndex(): Float? = null
}