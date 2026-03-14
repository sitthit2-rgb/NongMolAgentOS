package com.nongmol.agent.v2;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

public class MainActivity extends Activity {
    private TextView logFeed, statusLight;
    private final String NEON_CYAN = "#00F2FF", MATRIX_GREEN = "#00FF41", ALERT_RED = "#FF0055";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // พื้นหลังสีดำ Space Black
        RelativeLayout root = new RelativeLayout(this);
        root.setBackgroundColor(Color.parseColor("#02040A"));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 60, 30, 30);

        // 1. Header UX: ระบบแสง สี เสียง
        statusLight = new TextView(this);
        statusLight.setText("● SYSTEM_SPECTRUM: ACTIVE | FREQUENCY: 432Hz");
        statusLight.setTextColor(Color.parseColor(MATRIX_GREEN));
        statusLight.setTextSize(10);
        statusLight.setGravity(Gravity.CENTER);
        layout.addView(statusLight);

        TextView title = new TextView(this);
        title.setText("NONGMOL COMMAND V5");
        title.setTextColor(Color.parseColor(NEON_CYAN));
        title.setTextSize(22);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(Typeface.create("sans-serif-thin", Typeface.BOLD));
        layout.addView(title);

        // 2. Body UX Matrix: ตา หู จมูก แขน ขา
        GridLayout bodyGrid = new GridLayout(this);
        bodyGrid.setColumnCount(3);
        bodyGrid.setPadding(0, 40, 0, 40);
        bodyGrid.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        bodyGrid.setUseDefaultMargins(true);

        // จัดวางตามตำแหน่งร่างกาย (UX Logic)
        bodyGrid.addView(createNode("ตา (EYE)", ALERT_RED));
        bodyGrid.addView(createNode("หู (EAR)", NEON_CYAN));
        bodyGrid.addView(createNode("จมูก (NOSE)", "#FFD700"));
        bodyGrid.addView(createNode("แขน (ARM-L)", "#AD00FF"));
        bodyGrid.addView(createNode("ลำตัว (CORE)", "#FFFFFF"));
        bodyGrid.addView(createNode("แขน (ARM-R)", "#AD00FF"));
        bodyGrid.addView(createNode("ขา (LEG-L)", MATRIX_GREEN));
        bodyGrid.addView(new Space(this)); // เว้นกลาง
        bodyGrid.addView(createNode("ขา (LEG-R)", MATRIX_GREEN));
        
        layout.addView(bodyGrid);

        // 3. Console UX: ระบบแสดงผล Log
        ScrollView scroll = new ScrollView(this);
        logFeed = new TextView(this);
        logFeed.setText("> NEURAL_BOOT: SUCCESS\n> SYNCING ALL BODY NODES...\n> READY FOR GLOBAL COMMAND.");
        logFeed.setTextColor(Color.parseColor("#B0FFFFFF"));
        logFeed.setBackground(createShape("#0A0F1E", 2, "#1E293B", 20));
        logFeed.setPadding(40, 40, 40, 40);
        logFeed.setTypeface(Typeface.MONOSPACE);
        logFeed.setTextSize(12);
        scroll.addView(logFeed);
        layout.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1f));

        // 4. Input & Control UX
        EditText input = new EditText(this);
        input.setHint("INSERT GLOBAL COMMAND...");
        input.setHintTextColor(Color.GRAY);
        input.setTextColor(Color.WHITE);
        input.setBackground(createShape("#161B22", 1, "#30363D", 10));
        input.setPadding(30, 30, 30, 30);
        layout.addView(input);

        Button btnExec = new Button(this);
        btnExec.setText("EXECUTE NEURAL SYNC");
        btnExec.setBackground(createShape(NEON_CYAN, 0, NEON_CYAN, 12));
        btnExec.setTextColor(Color.BLACK);
        btnExec.setTypeface(Typeface.DEFAULT_BOLD);
        LinearLayout.LayoutParams btnP = new LinearLayout.LayoutParams(-1, -2);
        btnP.setMargins(0, 20, 0, 0);
        btnExec.setOnClickListener(v -> {
            logFeed.append("\n\n[CMD]: " + input.getText().toString());
            logFeed.append("\n> ซิงค์ หู ตา จมูก แขน ขา... เรียบร้อย!");
            statusLight.setText("● ALL SYSTEMS SYNCED | GREEN LIGHT");
            input.setText("");
        });
        layout.addView(btnExec, btnP);

        root.addView(layout);
        setContentView(root);
    }

    private View createNode(String name, String color) {
        TextView tv = new TextView(this);
        tv.setText(name);
        tv.setTextColor(Color.parseColor(color));
        tv.setTextSize(8);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(10, 20, 10, 20);
        tv.setBackground(createShape("#00000000", 2, color, 8));
        GridLayout.LayoutParams p = new GridLayout.LayoutParams();
        p.width = 0; p.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        tv.setLayoutParams(p);
        return tv;
    }

    private GradientDrawable createShape(String bg, int sw, String sc, int r) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(bg));
        gd.setCornerRadius(r);
        if (sw > 0) gd.setStroke(sw, Color.parseColor(sc));
        return gd;
    }
}
