//
//  ClinicsListScreenComponentWrapper.swift
//  iosApp
//
//  Created by Artem on 19.01.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import ComposeApp

class ClinicsListScreenComponentWrapper: ObservableObject {
    
    var componentWrapper : IClinicsMapScreenComponent
    
    var taskPlaces: Task<Void,Never>?
    var taskLocationPermission: Task<Void,Never>?
    var taskUserLocation: Task<Void,Never>?
    var taskIsMyLocationButtonClicked: Task<Void,Never>?
    
    @Published var places: [Place]? = nil
    @Published var isLocationPermissionGranted: Bool? = false
    @Published var currentUserLocation: CustomCoordinates? = CustomCoordinates(coordinates: nil)
    @Published var isMyLocationButtonClicked: Bool? = false
    
    init(component: IClinicsMapScreenComponent) {
        componentWrapper = component
        taskPlaces = Task { @MainActor [weak self] in
            guard let self = self else { return } // Safely unwrap `self`
                    
             await collect(flow: self.componentWrapper.places, onEach: { value in
                        self.places = value as? [Place]
                 debugPrint(value)
                    })
        }
        taskLocationPermission = Task { @MainActor [weak self] in
            guard let self = self else { return } // Safely unwrap `self`
                    
             await collect(flow: self.componentWrapper.locationPermission, onEach: { value in
                 self.isLocationPermissionGranted = value == Compass_permissionsPermissionState.granted
                 debugPrint(value)
                    })
        }
        taskUserLocation = Task { @MainActor [weak self] in
            guard let self = self else { return } // Safely unwrap `self`
                    
             await collect(flow: self.componentWrapper.locationCoordinates, onEach: { value in
                        self.currentUserLocation = value
                 debugPrint(value)
                    })
        }
        taskIsMyLocationButtonClicked = Task { @MainActor [weak self] in
            guard let self = self else { return } // Safely unwrap `self`
                    
             await collect(flow: self.componentWrapper.isMyLocationButtonClicked, onEach: { value in
                 self.isMyLocationButtonClicked = value.boolValue
                 debugPrint(value)
                    })
        }
    }
    
    func setIsMyLocationButtonClicked() {
        self.componentWrapper.setMyLocationButtonClicked()
    }
    
    deinit {
        taskPlaces?.cancel()
        taskLocationPermission?.cancel()
        taskUserLocation?.cancel()
    }
}

func collect<T>( flow:CommonStateFlow<T> , onEach : @escaping (T)-> Void) async {
    
    var countinuation : CheckedContinuation<Void,Never>?
    
    let cancellable  =  flow.startCollection(onEach: {value in onEach(value!)}) {
        countinuation?.resume()
    }
    
    await withTaskCancellationHandler(operation: {
        await withCheckedContinuation{value in countinuation = value}
    }, onCancel: {
        cancellable.onCancel()
    })
    
    
}
