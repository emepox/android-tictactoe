package de.sdomma.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.sdomma.tictactoe.databinding.ActivityFragmentsBinding

class FragmentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_fragmentHolder)

        if (currentFragment is TitleFragment) {
            currentFragment.onButtonClick = {
                supportFragmentManager.
                        beginTransaction().
                        replace(R.id.fcv_fragmentHolder, GameFragment()).
                        commit()

            }
        }
    }
}