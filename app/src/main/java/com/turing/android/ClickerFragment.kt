package com.turing.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.turing.android.databinding.ClickerFragmentBinding

/**
 * Фрагмент кликера
 *
 * @author mikhail.gasin
 */
class ClickerFragment : Fragment() {

    private var _binding: ClickerFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ClickerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ClickerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickerData.observe(viewLifecycleOwner) {
            with(binding) {
                predictionTextView.text = viewModel.clickerData.value?.predictionText
                Glide.with(predictionImage.context)
                    .load(viewModel.clickerData.value?.predictionImageSrc)
                    .fitCenter()
                    .into(predictionImage)
            }
        }

        binding.predictionButton.setOnClickListener {
            viewModel.initData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun create(): ClickerFragment {
            return ClickerFragment()
        }
    }
}