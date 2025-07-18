package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {
    Button btn_AC, btn_percentage, btn_del, btn_division, btn_multiply, btn_minus, btn_plus, btn_equal,
            btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0, btn_dot, btn_plus_minus;

    TextView screenview, screenwrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        btn_AC = findViewById(R.id.btnAC);
        btn_percentage = findViewById(R.id.btnpercentage);
        btn_del = findViewById(R.id.btndel);
        btn_division = findViewById(R.id.btndivision);
        btn_multiply = findViewById(R.id.multiply);
        btn_minus = findViewById(R.id.btnminus);
        btn_plus = findViewById(R.id.btnplus);
        btn_equal = findViewById(R.id.equall);
        btn_1 = findViewById(R.id.btn1);
        btn_2 = findViewById(R.id.btn2);
        btn_3 = findViewById(R.id.btn3);
        btn_4 = findViewById(R.id.btn4);
        btn_5 = findViewById(R.id.btn5);
        btn_6 = findViewById(R.id.btn6);
        btn_7 = findViewById(R.id.btn7);
        btn_8 = findViewById(R.id.btn8);
        btn_9 = findViewById(R.id.btn9);
        btn_0 = findViewById(R.id.btn0);
        btn_dot = findViewById(R.id.btndot);
        btn_plus_minus = findViewById(R.id.btnplus_minus);

        screenview = findViewById(R.id.screenview);
        screenwrite = findViewById(R.id.screenwrite);

        // AC button
        btn_AC.setOnClickListener(view -> {
            screenview.setText("");
            screenwrite.setText("");
            adjustTextSize();
        });

        // Add all input buttons to a list
        ArrayList<Button> allButtons = new ArrayList<>();
        allButtons.add(btn_0); allButtons.add(btn_1); allButtons.add(btn_2); allButtons.add(btn_3); allButtons.add(btn_4);
        allButtons.add(btn_5); allButtons.add(btn_6); allButtons.add(btn_7); allButtons.add(btn_8); allButtons.add(btn_9);
        allButtons.add(btn_plus); allButtons.add(btn_minus); allButtons.add(btn_multiply); allButtons.add(btn_division);

        // Loop for input buttons with limit check
        for (Button btn : allButtons) {
            btn.setOnClickListener(view -> {
                String current = screenwrite.getText().toString();
                if (current.length() >= 100) {
                    Toast.makeText(this, "Maximum 100 characters allowed", Toast.LENGTH_SHORT).show();
                    return;
                }
                screenwrite.setText(current + btn.getText().toString());
                autoEvaluate();
                adjustTextSize();
            });
        }

        // Delete button
        btn_del.setOnClickListener(view -> {
            String currentText = screenwrite.getText().toString();
            if (currentText.length() > 0) {
                screenwrite.setText(currentText.substring(0, currentText.length() - 1));
                autoEvaluate();
                adjustTextSize();
            }
        });

        // Equals button
        btn_equal.setOnClickListener(view -> {
            autoEvaluate();
            screenwrite.setText(screenview.getText().toString());
        });

        // Plus-minus toggle
        btn_plus_minus.setOnClickListener(view -> {
            try {
                String current = screenwrite.getText().toString();
                if (current.length() >= 100) {
                    Toast.makeText(this, "Maximum 100 characters allowed", Toast.LENGTH_SHORT).show();
                    return;
                }
                double value = Double.parseDouble(current);
                value *= -1;
                screenwrite.setText(String.valueOf(value));
                autoEvaluate();
                adjustTextSize();
            } catch (NumberFormatException e) {
                screenview.setText("Error");
                adjustTextSize();
            }
        });

        // Dot button
        btn_dot.setOnClickListener(view -> {
            String currentText = screenwrite.getText().toString();
            if (currentText.length() >= 100) {
                Toast.makeText(this, "Maximum 100 characters allowed", Toast.LENGTH_SHORT).show();
                return;
            }

            String[] parts = currentText.split("[+\\-*/]");
            String lastNumber = parts[parts.length - 1];

            if (!lastNumber.contains(".")) {
                screenwrite.setText(currentText + ".");
                autoEvaluate();
                adjustTextSize();
            }
        });
    }

    // Evaluation logic
    private void autoEvaluate() {
        try {
            String expression = screenwrite.getText().toString()
                    .replace("x", "*")
                    .replace("÷", "/");

            Expression exp = new ExpressionBuilder(expression).build();
            double result = exp.evaluate();

            if (result == (int) result) {
                screenview.setText(String.valueOf((int) result));
            } else {
                screenview.setText(String.valueOf(result));
            }
        } catch (Exception e) {
            screenview.setText("");
        }
        adjustTextSize();
    }

    // Dynamic font size logic
    private void adjustTextSize() {
        String text = screenwrite.getText().toString();
        int length = text.length();

        if (length < 36) {
            screenwrite.setTextSize(45);
        } else if (length < 50) {
            screenwrite.setTextSize(40);
        } else if (length < 70) {
            screenwrite.setTextSize(35);
        } else if (length < 90) {
            screenwrite.setTextSize(30);
        } else {
            screenwrite.setTextSize(25);

        }
    }
}
