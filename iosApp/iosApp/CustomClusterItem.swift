//
//  CustomClusterItem.swift
//  iosApp
//
//  Created by Artem on 20.01.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import GoogleMapsUtils
import GoogleMaps

class CustomClusterItem: NSObject, GMUClusterItem {
    var position: CLLocationCoordinate2D
    var title: String?
    var snippet: String?

    init(position: CLLocationCoordinate2D, title: String? = nil, snippet: String? = nil) {
        self.position = position
        self.title = title
        self.snippet = snippet
    }
}
