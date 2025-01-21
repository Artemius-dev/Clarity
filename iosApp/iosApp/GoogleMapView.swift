//
//  GoogleMapView.swift
//  iosApp
//
//  Created by Artem on 11.12.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import ComposeApp
import GoogleMaps

struct GoogleMapView: UIViewRepresentable {
    var latLng: LatLng

    func makeUIView(context: Context) -> GMSMapView {
        let options = GMSMapViewOptions()
        
        // Safely parse latitude and longitude from String to Double
        let latitude = Double(latLng.latitude) ?? 0.0
        let longitude = Double(latLng.longitude) ?? 0.0

        options.camera = GMSCameraPosition.camera(
            withLatitude: latitude,
            longitude: longitude,
            zoom: 14.0
        )

        let mapView = GMSMapView(options: options)

        let marker = GMSMarker()
        marker.position = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
        marker.map = mapView

        return mapView
    }

    func updateUIView(_ uiView: GMSMapView, context: Context) {
        // Safely parse latitude and longitude from String to Double
        let latitude = Double(latLng.latitude) ?? 0.0
        let longitude = Double(latLng.longitude) ?? 0.0

        // Update the map's camera position if the LatLng changes
        let newPosition = GMSCameraPosition.camera(
            withLatitude: latitude,
            longitude: longitude,
            zoom: uiView.camera.zoom
        )
        uiView.animate(to: newPosition)

        // Update the marker's position
        if let marker = uiView.selectedMarker {
            marker.position = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
        }
    }
}

