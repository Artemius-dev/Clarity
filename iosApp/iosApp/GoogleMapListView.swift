//
//  GoogleMapListView.swift
//  iosApp
//
//  Created by Artem on 19.01.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import ComposeApp
import GoogleMaps
import GoogleMapsUtilsObjC

struct GoogleMapListView: UIViewRepresentable {

    
    @ObservedObject var componentWrapper: ClinicsListScreenComponentWrapper

    func makeCoordinator() -> Coordinator {
            Coordinator()
        }

    func makeUIView(context: Context) -> GMSMapView {
        // Initialize the GMSMapView
        let mapView = GMSMapView(frame: .zero)

        // Configure the initial map properties
        mapView.isBuildingsEnabled = false
        mapView.isIndoorEnabled = false
        mapView.isTrafficEnabled = false
        mapView.mapType = .terrain // Corresponds to MapType.TERRAIN
        mapView.setMinZoom(3, maxZoom: 21) // Min and max zoom preferences

        // Apply UI settings
        mapView.settings.compassButton = true
        mapView.settings.indoorPicker = false
        mapView.settings.myLocationButton = false
        mapView.settings.rotateGestures = true
        mapView.settings.scrollGestures = true
        mapView.settings.tiltGestures = true
        mapView.settings.zoomGestures = true
        mapView.settings.allowScrollGesturesDuringRotateOrZoom = true

        // Enable location if permission is granted
        if componentWrapper.isLocationPermissionGranted! {
            mapView.isMyLocationEnabled = true
        }
        context.coordinator.setupClusterManager(for: mapView)
        return mapView
    }

    func updateUIView(_ uiView: GMSMapView, context: Context) {
        // Update markers on the map based on the places in componentWrapper
        uiView.clear() // Clear existing markers to avoid duplicates
        guard let clusterManager = context.coordinator.clusterManager else { return }

        // Clear existing clusters
        clusterManager.clearItems()

        
        if let isLocationPermissionGranted = componentWrapper.isLocationPermissionGranted {
            uiView.isMyLocationEnabled = componentWrapper.isLocationPermissionGranted!
        }
        
        // Set the initial camera position if a location is available
        if let firstPlace = componentWrapper.places?.first,
           let latitude = Double(firstPlace.latLng.latitude),
           let longitude = Double(firstPlace.latLng.longitude) {
            if let isLocationPermissionGranted = componentWrapper.isLocationPermissionGranted {
                if (isLocationPermissionGranted == false) {
                    let camera = GMSCameraPosition.camera(withLatitude: latitude, longitude: longitude, zoom: 14.0)
                    uiView.camera = camera
                }
            }
        }

        if let places = componentWrapper.places {
                    for place in places {
                        if let latitude = Double(place.latLng.latitude),
                           let longitude = Double(place.latLng.longitude) {
                            // Add each place as a cluster item
                            let clusterItem = CustomClusterItem(
                                position: CLLocationCoordinate2D(latitude: latitude, longitude: longitude),
                                title: place.name
                            )
                            clusterManager.add(clusterItem)
                        }
                    }
                }
        
        clusterManager.cluster()

        // Update the camera position when the current user location changes
        if let currentUserLocation = componentWrapper.currentUserLocation {
            if(currentUserLocation.coordinates != nil) {
                if let isMyLocationButtonClicked = componentWrapper.isMyLocationButtonClicked {
                    if isMyLocationButtonClicked {
                        let cameraUpdate = GMSCameraUpdate.setTarget(
                                        CLLocationCoordinate2D(
                                            latitude: currentUserLocation.coordinates!.latitude,
                                            longitude: currentUserLocation.coordinates!.longitude
                                        ),
                                        zoom: 14.0
                                    )
                            uiView.animate(with: cameraUpdate)
                            componentWrapper.setIsMyLocationButtonClicked()
                    }
                }
            }
        }
    }

    class Coordinator {
            var clusterManager: GMUClusterManager?

            func setupClusterManager(for mapView: GMSMapView) {
                let iconGenerator = GMUDefaultClusterIconGenerator()
                let algorithm = GMUNonHierarchicalDistanceBasedAlgorithm()
                let renderer = GMUDefaultClusterRenderer(mapView: mapView, clusterIconGenerator: iconGenerator)
                clusterManager = GMUClusterManager(map: mapView, algorithm: algorithm, renderer: renderer)
            }
        }
}
