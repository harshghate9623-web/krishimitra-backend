# Firebase to Aiven MySQL Migration Plan

This plan details the full removal of Firebase from the KrishiMitra Android app and the transition to your Spring Boot + Aiven MySQL backend.

## User Review Required

> [!WARNING]
> This is a major architectural change. Once we remove Firebase, your app will rely entirely on your Spring Boot backend. Some features (like uploading profile pictures or getting real-time updates) will need new backend APIs. Please review the "Backend Changes Required" section carefully.

## Open Questions

1. **Profile Images:** Firebase Storage was used for uploading profile pictures. Do you want to use AWS S3/Cloudinary in your Spring Boot backend, or should we save images as Base64 strings in MySQL (not recommended for large files), or temporarily disable profile picture uploads until you have a storage solution?
2. **Real-time Updates:** Firestore provided real-time updates for AI Advisory plans. Without Firestore, we will need to implement a "Pull to Refresh" or polling mechanism in Android. Is that acceptable?

## Proposed Changes

We will remove all Firebase SDKs and update the following Android files to use Retrofit (your Spring Boot API) instead:

### Android Project Configuration
#### [MODIFY] app/build.gradle.kts
- Remove `com.google.gms.google-services` plugin.
- Remove all `com.google.firebase` dependencies (Auth, Firestore, Storage, AppCheck, BoM).

#### [MODIFY] app/src/main/java/com/krishimitra/KrishiMitraApplication.kt
- Remove `FirebaseApp.initializeApp` and `FirebaseAppCheck` initialization.

### Authentication & Profile
#### [MODIFY] app/src/main/java/com/krishimitra/LoginActivity.kt
- Remove `FirebaseAuth` password reset logic.
- Replace with a Retrofit call: `POST /auth/forgot-password`.

#### [MODIFY] app/src/main/java/com/krishimitra/MainActivity.kt
- Remove `FirebaseFirestore` listener for user profile.
- Load user profile (Name, Email) from `SharedPreferences` (already populated during login).
- *Pending your decision on Profile Images* - adapt or disable `saveProfileImage()`.

### Farm & Advisory Features
#### [MODIFY] app/src/main/java/com/krishimitra/FarmHistoryFragment.kt
- Remove `FirebaseFirestore` queries.
- Replace with Retrofit call: `GET /farm-history/user/{uid}`.

#### [MODIFY] app/src/main/java/com/krishimitra/SoilEditFragment.kt
- Remove `FirebaseFirestore` writes.
- Replace with Retrofit call: `PUT /farm/{id}/soil` (or use existing `PUT /farm/{id}`).

#### [MODIFY] app/src/main/java/com/krishimitra/AdvisoryFragment.kt
- Remove `FirebaseFirestore` listeners.
- Replace with Retrofit call: `GET /advisory/farm/{farmId}`.

### API Service
#### [MODIFY] app/src/main/java/com/krishimitra/ApiService.kt
- Add new endpoints for Password Reset, Farm History, and Advisory.

---

## Backend Changes Required (Spring Boot)

To support this new Android code, you will need to add the following endpoints to your Spring Boot application:

### 1. Password Reset (Auth Controller)
```java
@PostMapping("/auth/forgot-password")
public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
    // String email = request.get("email");
    // Generate reset token, save to DB, send email
}
```

### 2. Profile Image Upload (User Controller)
*(Depends on your storage strategy)*
```java
@PostMapping("/user/{uid}/profile-image")
public ResponseEntity<?> uploadProfileImage(@PathVariable Long uid, @RequestParam("file") MultipartFile file) {
    // Upload file to S3/Cloudinary/Local Directory
    // Update user's profileImage URL in Aiven MySQL
}
```

### 3. Farm History (History Controller)
```java
@GetMapping("/farm-history/user/{uid}")
public ResponseEntity<List<FarmHistoryResponse>> getUserHistory(@PathVariable Long uid) {
    // Return list of past detections/activities for this user's farms
}

@PostMapping("/farm-history")
public ResponseEntity<?> addHistoryRecord(@RequestBody FarmHistoryRequest request) {
    // Save new AR/Upload detection history to Aiven MySQL
}
```

### 4. AI Advisory Plans (Advisory Controller)
```java
@GetMapping("/advisory/farm/{farmId}")
public ResponseEntity<AdvisoryPlanResponse> getAdvisoryPlan(@PathVariable Long farmId) {
    // Return the latest AI crop plan for this farm
}

@PostMapping("/advisory/farm/{farmId}")
public ResponseEntity<?> saveAdvisoryPlan(@PathVariable Long farmId, @RequestBody AdvisoryPlanRequest request) {
    // Save a newly generated AI crop plan to Aiven MySQL
}
```

## Verification Plan

### Automated/Manual Testing
- Verify the app compiles without Firebase dependencies.
- Verify Login flow still works perfectly (using the fix implemented earlier).
- Verify the Profile loads successfully using local data and the existing `/user/{uid}` API.
- Create placeholder responses in Android for APIs that aren't built yet, ensuring the app doesn't crash while you finish the Spring Boot side.
