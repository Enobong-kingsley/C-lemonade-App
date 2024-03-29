package com.example.lemonadeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    // SELECT represents the "pick lemon" state
    private val SELECT = "select"
    // SQUEEZE represents the "squeeze lemon" state
    private val SQUEEZE = "squeeze"
    // DRINK represents the "drink lemonade" state
    private val DRINK = "drink"
    // RESTART represents the state where the lemonade has be drunk and the glass is empty
    private val RESTART = "restart"
    // Default the state to select
    private var lemonadeState = "select"
    // Default lemonSize to -1
    private var lemonSize = -1
    // Default the squeezeCount to -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // === DO NOT ALTER THE CODE IN THE FOLLOWING IF STATEMENT ===
        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }
        lemonImage = findViewById(R.id.image_lemon_state)
        setViewElements()
        lemonImage!!.setOnClickListener {
            // TODO: call the method that handles the state when the image is clicked
            clickLemonImage()
        }
        lemonImage!!.setOnLongClickListener {
            // TODO: replace 'false' with a call to the function that shows the squeeze count
            clickLemonImage()
            false
        }
//        clickLemonImage()
    }
    /**
 * === DO NOT ALTER THIS METHOD ===
 *
 * This method saves the state of the app if it is put in the background.
 */
override fun onSaveInstanceState(outState: Bundle) {
    outState.putString(LEMONADE_STATE, lemonadeState)
    outState.putInt(LEMON_SIZE, lemonSize)
    outState.putInt(SQUEEZE_COUNT, squeezeCount)
    super.onSaveInstanceState(outState)
}
    /**
     * Clicking will elicit a different response depending on the state.
     * This method determines the state and proceeds with the correct action.
     */
    private fun clickLemonImage() {
        // TODO: use a conditional statement like 'if' or 'when' to track the lemonadeState
        //  when the the image is clicked we may need to change state to the next step in the
        //  lemonade making progression (or at least make some changes to the current state in the
        //  case of squeezing the lemon). That should be done in this conditional statement
        setViewElements()
        when(lemonadeState) {
            SELECT -> {
                // TODO: When the image is clicked in the SELECT state, the state should become SQUEEZE
                //  - The lemonSize variable needs to be set using the 'pick()' method in the LemonTree class
                //  - The squeezeCount should be 0 since we haven't squeezed any lemons just yet.
                lemonadeState = SQUEEZE
                lemonSize = lemonTree.pick()
                squeezeCount = 0
            }

            SQUEEZE -> {
                // TODO: When the image is clicked in the SQUEEZE state the squeezeCount needs to be
                //  INCREASED by 1 and lemonSize needs to be DECREASED by 1.
                //  - If the lemonSize has reached 0, it has been juiced and the state should become DRINK
                //  - Additionally, lemonSize is no longer relevant and should be set to -1
                squeezeCount+=1
                lemonSize-=1

                if(lemonSize == 0){
                    lemonadeState = DRINK
                    lemonSize = -1
                }
                showSnackbar()
            }

            DRINK -> {

                // TODO: When the image is clicked in the DRINK state the state should become RESTART
                lemonadeState = RESTART
            }

            RESTART -> {
                // TODO: When the image is clicked in the RESTART state the state should become SELECT
                lemonadeState = SELECT
            }

        }

        // TODO: lastly, before the function terminates we need to set the view elements so that the
        //  UI can reflect the correct state

        Log.d("Lemonade state message",lemonadeState)

    }

    /**
     * Set up the view elements according to the state.
     */
    private fun setViewElements() {
        val textAction: TextView = findViewById(R.id.text_action)
        // TODO: set up a conditional that tracks the lemonadeState

        // TODO: for each state, the textAction TextView should be set to the corresponding string from
        //  the string resources file. The strings are named to match the state

        // TODO: Additionally, for each state, the lemonImage should be set to the corresponding
        //  drawable from the drawable resources. The drawables have the same names as the strings
        //  but remember that they are drawables, not strings.

        when(lemonadeState){
            SELECT -> {
                textAction.text = resources.getString(R.string.lemon_select)
                lemonImage?.setImageResource(R.drawable.lemon_tree)
            }
            SQUEEZE -> {
                textAction.text = resources.getString(R.string.lemon_squeeze)
                lemonImage?.setImageResource(R.drawable.lemon_squeeze)
            }
            DRINK -> {
                textAction.text = resources.getString(R.string.lemon_drink)
                lemonImage?.setImageResource(R.drawable.lemon_drink)
            }
            RESTART -> {
                textAction.text = resources.getString(R.string.lemon_empty_glass)
                lemonImage?.setImageResource(R.drawable.lemon_restart)
            }
        }
    }

    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * Long clicking the lemon image will show how many times the lemon has been squeezed.
     */
    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}

class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}

