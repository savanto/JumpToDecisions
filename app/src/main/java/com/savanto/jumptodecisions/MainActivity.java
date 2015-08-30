package com.savanto.jumptodecisions;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;


public class MainActivity extends Activity {
    // TODO -- make this slightly variable?
    private static final int DECISIONS = 5;

    private ViewAnimator switcher;
    private EditText inputChoice1;
    private EditText inputChoice2;
    private Button btnChoice1;
    private Button btnChoice2;
    private TextView finalChoice;
    private Decisions decisions;
    private int decisionsLeft = DECISIONS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        // Setup inputs, buttons, etc.
        this.switcher = (ViewAnimator) this.findViewById(R.id.view_switcher);
        this.inputChoice1 = (EditText) this.findViewById(R.id.input_choice1);
        this.inputChoice2 = (EditText) this.findViewById(R.id.input_choice2);
        this.btnChoice1 = (Button) this.findViewById(R.id.btn_choice1);
        this.btnChoice2 = (Button) this.findViewById(R.id.btn_choice2);
        this.finalChoice = (TextView) this.findViewById(R.id.final_choice);

        // Load decisions.
        this.decisions = new Decisions(this.getResources().getStringArray(R.array.nouns));
    }

    /**
     * onClick handler for Start button. Checks inputs and shows the next screen with the choices.
     * @param v The clicked view.
     */
    public void start(View v) {
        // Check inputs.
        // TODO -- make more robust checks?
        if (this.inputChoice1.getText().toString().isEmpty()
                || this.inputChoice2.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.error_input, Toast.LENGTH_SHORT).show();
        } else {
            // Hide keyboard.
            try {
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(v.getWindowToken(), 0);
            } catch (Exception e) { /* NOP */ }

            // Show decision screen.
            // TODO -- start timer? To make decisions fast.
            this.setChoices();
            this.switcher.showNext();
        }
    }

    /**
     * onClick handler for Choice1 button. Just calls {@link #pickChoice(int)} with choice = 1.
     * @param v The clicked view.
     */
    public void pickChoice1(View v) {
        this.pickChoice(1);
    }

    /**
     * onClick handler for Choice1 button. Just calls {@link #pickChoice(int)} with choice = 2.
     * @param v The clicked view.
     */
    public void pickChoice2(View v) {
        this.pickChoice(2);
    }

    /**
     * Helper for the choice buttons. Decrement the number of decisions left to make, and if it hits
     * zero, show the conclusion screen. Otherwise set another decision onto the buttons.
     * TODO -- This function also keeps track of choice statistics.
     * @param choice The choice number that was made, 1 or 2.
     */
    private void pickChoice(int choice) {
        if (--this.decisionsLeft >= 0) {
            // More decisions left to make, set the buttons with new choices.
            this.setChoices();
        } else {
            // Done making decisions, set the real choice made in the conclusion and show it.
            final String finalChoice = choice == 1 ? this.inputChoice1.getText().toString()
                    : this.inputChoice2.getText().toString();
            this.finalChoice.setText(finalChoice);
            this.switcher.showNext();
        }
    }

    /**
     * 1. Check if we should present dummy decisions, or the real choices.
     * 2a. If the former, query the Decisions object for two choices and set the choice buttons.
     * 2b. If the latter, set the choice buttons with the real input choices.
     */
    private void setChoices() {
        final String choice1;
        final String choice2;
        if (this.decisionsLeft > 0) {
            // Get random choices.
            choice1 = this.decisions.getRandomWord();
            choice2 = this.decisions.getRandomWord();
        } else {
            // Get real choices.
            // TODO -- randomize order?
            choice1 = this.inputChoice1.getText().toString();
            choice2 = this.inputChoice2.getText().toString();
        }

        // Set the buttons.
        this.btnChoice1.setText(choice1);
        this.btnChoice2.setText(choice2);
    }

    /**
     * Share the decision you made.
     * @param v The clicked view.
     */
    public void share(View v) {
        // TODO -- open sharing dialog with choice made.
        Toast.makeText(this, "Share!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Start the whole thing over.
     * @param v The clicked view.
     */
    public void startOver(View v) {
        // Clear out inputs, and return to intro screen.
        this.inputChoice1.setText("");
        this.inputChoice2.setText("");
        this.decisionsLeft = DECISIONS;
        this.switcher.setDisplayedChild(0);
    }
}
