VangtiChai - CSE 489 Assignment 1
=================================

Language: Kotlin
Min SDK : API 21 (Android 5.0 Lollipop)
Target  : API 34

What the app does
-----------------
Enter a money amount on a numeric keypad that is built from scratch (digits
0-9 plus a CLEAR button). The app shows the amount next to a "Taka:" label and
lists how many of each note (500, 100, 50, 20, 10, 5, 2, 1) make up the change.
Each digit you tap is added to the right of the current amount
(2 -> 23 -> 234). CLEAR resets it.

Four layouts
------------
- Phone portrait   : res/layout/activity_main.xml
- Phone landscape  : res/layout-land/activity_main.xml
- Tablet portrait  : res/layout-sw720dp/activity_main.xml
- Tablet landscape : res/layout-sw720dp-land/activity_main.xml

Portrait shows the change table in one column with a 3-wide keypad
(1 2 3 / 4 5 6 / 7 8 9 / 0 CLEAR). Landscape shows the change table in two
columns with a 4-wide keypad (1 2 3 4 / 5 6 7 8 / 9 0 CLEAR). The CLEAR button
spans two columns in both. All four use ConstraintLayout as the root.

Tablets are detected automatically by the smallest-width qualifier sw720dp
(Nexus 10 = 800x1280dp, smallest width 800 -> tablet; Pixel XL = 411x731dp,
smallest width 411 -> phone).

State saved on rotation
-----------------------
Android recreates the activity when the device rotates, so the entered amount
and "has the user typed yet" flag are saved in onSaveInstanceState() and read
back in onCreate(). The amount and change list therefore survive rotation.

No hardcoded sizes
------------------
Every text size, padding and margin is defined in res/values/sizes.xml (phones)
and res/values-sw720dp/sizes.xml (tablets). The layout files only reference
@dimen/... names, so nothing is hardcoded in the XML.

Devices / screens tested
------------------------
- Pixel XL  (411 x 731 dp)  - portrait and landscape   [required]
- Nexus 10  (800 x 1280 dp) - portrait and landscape   [required]
- Pixel 6   (medium phone)  - portrait and landscape
- Pixel Tablet              - portrait and landscape

How to run
----------
1. Unzip and open the VangtiChai folder in Android Studio
   (File > Open > select the VangtiChai folder).
2. Let Gradle sync finish (it downloads Gradle 8.7 and the dependencies).
3. Pick a Pixel XL or Nexus 10 emulator and press Run.
4. Use the rotate button on the emulator to switch portrait/landscape.
