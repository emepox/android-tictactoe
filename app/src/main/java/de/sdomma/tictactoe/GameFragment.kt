package de.sdomma.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.sdomma.tictactoe.databinding.ActivityGameFragmentBinding


class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ActivityGameFragmentBinding.inflate(inflater)
        return binding.root
    }


}