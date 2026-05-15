# Dynamic Home Tab & Profile Farm Management

This implementation plan covers the architectural changes to make the KrishiMitra Home Tab fully dynamic, and moves farm management (Add/Edit) to the Profile Tab.

## User Review Required

> [!IMPORTANT]
> Please review the intended mappings for Quick Actions on the Home Tab. Let me know if you would like these changed:
> - **Camera Icon**: Navigates to AR Scan Tab
> - **Upload Icon**: Navigates to Advisory Tab
> - **Clock Icon**: Navigates to History / Farm History Tab
> - **Edit Icon**: Navigates to Profile Tab (where Farm Editing will now reside)
> - **List Icon**: Navigates to Farm History Tab

## Proposed Changes

### 1. `HomeFragment.kt` & Layout Changes
- **Remove Add/Edit Logic**: Strip the Add Farm, Edit Farm, and Delete Farm dialog logic and API calls from this fragment.
- **Dynamic Farm Details**: Bind the selected farm from the Spinner to dynamically update `tvCurrentCrop`, `tvFarmSize`, `tvFarmLocationDetail`, and the Crop Progress circular bar.
- **Dynamic Weather**: Attach an `OnClickListener` to the Weather Card that uses `MainActivity.loadFragment(WeatherFragment())` to open the weather details.
- **Dynamic News & Market**: Convert the static placeholders in `fragment_home.xml` to `RecyclerView`s. Use `NewsAdapter` and `MarketAdapter` to fetch real data via `NewsRetrofitInstance` and `MarketRetrofitInstance`, identical to how the `NewsFragment` does it.
- **Quick Actions**: Set up click listeners for the 5 Quick Action icons to correctly redirect to their respective tabs using `(activity as MainActivity).switchTab(...)` or `loadFragment(...)`.

---

### 2. `ProfileFragment.kt` & Layout Changes
- **Add Farm Button**: Add a "Add Farm" button aligned to the right side just below the phone number input field in `fragment_profile.xml`.
- **Edit Farm Button**: When dynamically rendering the user's farms into `profileFarmsContainer`, inject an "Edit Farm" button above each farm. 
- **Migrate Logic**: Move the `showAddFarmDialog()`, `showEditFarmDialog()`, and `deleteSelectedFarm()` methods from `HomeFragment.kt` into `ProfileFragment.kt`. Ensure that editing updates both the local UI and triggers the backend API accurately.

## Verification Plan

### Automated/Manual Verification
- Build and launch the app.
- Ensure the Home tab properly loads real news articles and market prices via the APIs.
- Select different farms on the Home tab and verify the Farm Details card updates automatically.
- Tap the Weather Card to verify it navigates to the detailed Weather tab.
- Tap each Quick Action to ensure they navigate to the correct tabs.
- Open the Profile tab via the Hamburger menu. Check if the "Add Farm" button is positioned correctly.
- Ensure every farm listed in the profile has an "Edit" button, and test modifying a farm to ensure the API call succeeds.
