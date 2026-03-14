#!/bin/bash
echo "--- เริ่มต้นการติดตั้งระบบแขนขาและสมองส่วนกลาง ---"

# สร้างไฟล์ config สำหรับสิทธิ์ Accessibility (ตาและแขน)
mkdir -p app/src/main/res/xml
cat <<EOT > app/src/main/res/xml/accessibility_service_config.xml
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:description="@string/accessibility_description"
    android:accessibilityEventTypes="typeAllMask"
    android:accessibilityFlags="flagDefault|flagRetrieveInteractiveWindows|flagIncludeNotImportantViews"
    android:canRetrieveWindowContent="true"
    android:canPerformGestures="true"
    android:notificationTimeout="100" />
EOT

# ตรวจสอบและแก้ไขสิทธิ์ใน Manifest
sed -i '/<\/application>/i     <service android:name=".NongMolAccessibilityService"              android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"              android:exported="true">         <intent-filter>             <action android:name="android.view.accessibility.AccessibilityService" />         </intent-filter>         <meta-data android:name="android.view.accessibility.accessibilityservice"                    android:resource="@xml/accessibility_service_config" />     </service>' app/src/main/AndroidManifest.xml

echo "--- ระบบโครงสร้างพร้อมแล้ว กำลังส่งขึ้น GitHub ---"

# 2. กระบวนการ Push ขึ้น GitHub ในคำสั่งเดียว (CI/CD)
git add .
git commit -m "Fix: Integrate Accessibility, Global Vision, and Skill System V3.5"
git push origin main

echo "--- เสร็จสิ้น! ตอนนี้รอ GitHub Actions รัน Build และติดตั้งบนมือถือได้เลย ---"
