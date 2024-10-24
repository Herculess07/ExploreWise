## ExploreWise Tour Guide App 🗺️🍽️

This repository contains the source code for a Food Explorer mobile application built with Kotlin and Firebase.

## Screenshots

<table align="center">
  <tr>
    <td align="center" style="padding: 10px;">
      <img src="https://github.com/user-attachments/assets/b1e5d116-327e-4168-bb7d-25a2f8c9f7ab" width="200">
    </td>
    <td align="center" style="padding: 10px;">
      <img src="https://github.com/user-attachments/assets/3a31e6d1-c802-4c9a-a6db-a8d4f11ed37c" width="200">
    </td>
  </tr>
  <tr>
    <td align="center" style="padding: 10px;">
      <img src="https://github.com/user-attachments/assets/ec4e7ef0-2ee2-4f22-897f-f46a6eeea82e" width="200">
    </td>
    <td align="center" style="padding: 10px;">
      <img src="https://github.com/user-attachments/assets/97181a95-1097-4009-8fbc-74ee8fcd99ab" width="200">
    </td>
  </tr>
  <tr>
    <td align="center" style="padding: 10px;">
      <img src="https://github.com/user-attachments/assets/e0920465-f67c-4449-9361-1ff6b5602814" width="200">
    </td>
    <td align="center" style="padding: 10px;">
      <img src="https://github.com/user-attachments/assets/35b726ee-9aee-4d0d-8277-f8f19628e6fe" width="200">
    </td>
  </tr>
</table>


### 🏠  Home Page

*   Serves as the central hub of the app.
*   Handles user authentication and checks login status.
*   Implements a bottom navigation bar for switching between different sections:
    *   **Explore (HomeFragment):** Displays information about famous food, places, and devotional sites.
    *   **Bookmarks (BookmarkFragment):** Allows users to save their favorite food, places, and devotional sites for quick access.
    *   **Settings (SettingsFragment):** Provides options for customizing the app experience.
*   Includes a floating action button for adding new content.
*   Manages user permissions for camera, storage, and location access.
*   Provides a confirmation dialog before exiting the app.

### 🔎 Details Page

*   Displays detailed information about a selected food, place, or devotional site. 
*   Fetches data from Firebase based on the ID of the selected item.
*   Shows the item's name, description, city, area, contact information (email, website, phone number), and an image.
*   Allows users to bookmark items for later reference.
*   Provides a back button to return to the previous screen.

### 🔍 Search Page

*   Enables users to search for food, places, and devotional sites within the app.
*   Implements a search bar with real-time filtering as the user types.
*   Displays search results in separate RecyclerView lists for food, places, and devotional sites.
*   Allows users to click on a search result to view its details in the Details Activity.

### 🛠️ Technical Information

*   **Programming Language:** Kotlin
*   **Framework:** Android SDK
*   **Database:** Firebase Realtime Database
*   **Storage:** Firebase Storage
*   **Libraries:** 
    *   Glide (for image loading)
    *   Material Components (for UI elements)

### 🛣️  Future Enhancements 

*   **Map Integration:** Display food, places, and devotional sites on a map, allowing users to explore nearby options.
*   **User Reviews and Ratings:** Enable users to share their experiences and opinions on different items. 
*   **Social Features:** Allow users to connect and share recommendations with friends.
*   **Accessibility Improvements:** Enhance the app's usability for users with different abilities.
