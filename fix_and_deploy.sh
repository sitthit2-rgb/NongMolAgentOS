#!/bin/bash
echo "--- 🛠 เริ่มการล้างระบบและติดตั้งแขนขาใหม่ ---"

# ไฟล์ Manifest เจ้าปัญหา
MANIFEST="app/src/main/AndroidManifest.xml"

# ล้าง Service ที่ซ้ำซ้อนออกทั้งหมดก่อนเพื่อให้ไฟล์สะอาด
sed -i '/<service.*NongMolAccessibilityService/,/<\/service>/d' $MANIFEST

# ใส่ Service กลับเข้าไปใหม่เพียงชุดเดียวให้ถูกตำแหน่ง (ก่อนปิด application)
sed -i '/<\/application>/i \
    <service android:name=".NongMolAccessibilityService" \
             android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE" \
             android:exported="true"> \
        <intent-filter> \
            <action android:name="android.view.accessibility.AccessibilityService" /> \
        </intent-filter> \
        <meta-data android:name="android.view.accessibility.accessibilityservice" \
                   android:resource="@xml/accessibility_service_config" /> \
    </service>' $MANIFEST

# สร้างไฟล์คอนฟิก Accessibility (ถ้ายังไม่มี)
mkdir -p app/src/main/res/xml
cat <<'EOT' > app/src/main/res/xml/accessibility_service_config.xml
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:description="@string/accessibility_description"
    android:accessibilityEventTypes="typeAllMask"
    android:accessibilityFlags="flagDefault|flagRetrieveInteractiveWindows|flagIncludeNotImportantViews"
    android:canRetrieveWindowContent="true"
    android:canPerformGestures="true"
    android:notificationTimeout="100" />
EOT

echo "--- ✅ แก้ไขไฟล์ Manifest สำเร็จ! กำลังส่งขึ้น GitHub ---"

# 2. กระบวนการ Push เพื่อรัน GitHub Actions ใหม่
git add .
git commit -m "Fix: Remove duplicate Manifest entry and re-enable Accessibility"
git push origin main

echo "--- 🚀 ส่งงานเรียบร้อย! พี่รอเช็คไฟเขียวที่ GitHub Actions ได้เลย ---"
