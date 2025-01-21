import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
            mapUIViewController: { (latLng: LatLng) -> UIViewController in
                return UIHostingController(rootView: GoogleMapView(latLng: latLng))
            },
            mapListUIViewController: { (component: IClinicsMapScreenComponent) -> UIViewController in
                
                return UIHostingController(
                    rootView: GoogleMapListView(
                        componentWrapper: ClinicsListScreenComponentWrapper(component: component)
                    )
                )
            }
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
    
    func updateUIView() {
        
    }
}



