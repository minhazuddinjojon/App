package com.example.vangtichai

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // The taka notes we make change with, biggest first. Going through them in
    // this order and dividing one after another gives a simple greedy breakdown.
    private val noteValues = intArrayOf(500, 100, 50, 20, 10, 5, 2, 1)

    // The amount typed in so far. We build it up digit by digit.
    private var enteredAmount = 0L

    // We only print a number once the user has actually pressed something, so
    // the screen starts as "Taka:" with nothing after it (like the screenshots).
    private var hasTyped = false

    private lateinit var takaLabel: TextView

    // Each note value (500, 100, ...) is mapped to the TextView that shows it.
    private val noteRows = HashMap<Int, TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        takaLabel = findViewById(R.id.takaLabel)

        noteRows[500] = findViewById(R.id.note500)
        noteRows[100] = findViewById(R.id.note100)
        noteRows[50] = findViewById(R.id.note50)
        noteRows[20] = findViewById(R.id.note20)
        noteRows[10] = findViewById(R.id.note10)
        noteRows[5] = findViewById(R.id.note5)
        noteRows[2] = findViewById(R.id.note2)
        noteRows[1] = findViewById(R.id.note1)

        // Wire up the ten digit buttons.
        val digitButtonIds = intArrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        for (digit in 0..9) {
            findViewById<Button>(digitButtonIds[digit]).setOnClickListener {
                onDigitPressed(digit)
            }
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener { onClearPressed() }

        // If the activity was recreated (e.g. after rotating the device), put
        // the amount we saved earlier back so the interface keeps its state.
        if (savedInstanceState != null) {
            enteredAmount = savedInstanceState.getLong(KEY_AMOUNT, 0L)
            hasTyped = savedInstanceState.getBoolean(KEY_HAS_TYPED, false)
        }

        updateDisplay()
    }

    // A tapped digit is pushed onto the right of the number:
    // 2 -> 23 -> 234 is simply amount = amount * 10 + newDigit.
    // Cap at 9 digits (999,999,999 taka) to prevent Long overflow.
    private fun onDigitPressed(digit: Int) {
        if (enteredAmount > 99_999_999L) return
        enteredAmount = enteredAmount * 10 + digit
        hasTyped = true
        updateDisplay()
    }

    private fun onClearPressed() {
        enteredAmount = 0L
        hasTyped = false
        updateDisplay()
    }

    // Refresh the "Taka:" label and recalculate the change table.
    private fun updateDisplay() {
        takaLabel.text = if (hasTyped) "Taka: $enteredAmount" else "Taka:"

        var remaining = enteredAmount
        for (note in noteValues) {
            val count = remaining / note
            remaining %= note
            noteRows[note]?.text = "$note: $count"
        }
    }

    // Android destroys and recreates the activity on rotation, so we hold on to
    // the entered amount here and read it back in onCreate.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_AMOUNT, enteredAmount)
        outState.putBoolean(KEY_HAS_TYPED, hasTyped)
    }

    companion object {
        private const val KEY_AMOUNT = "entered_amount"
        private const val KEY_HAS_TYPED = "has_typed"
    }
}
