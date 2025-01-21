import SwiftUI
import GoogleMaps
import Firebase

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchingOptions:[UIApplication.LaunchOptionsKey: Any]? = nil) -> Bool {
        FirebaseApp.configure()
        
        return true
    }
}

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    init() {
    // There should be you Google MAPS API key
            GMSServices.provideAPIKey("")
        }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
