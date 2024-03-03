package com.turing.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.turing.android.R
import com.turing.android.databinding.FragmentAddPersonBinding
import com.turing.android.dto.TuringPerson
import com.turing.android.ui.Navigator

/**
 * Фрагмент добавления нового активиста
 *
 * @author Diagorn
 */
class AddPersonFragment : Fragment() {

    private var _binding: FragmentAddPersonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addPersonButton.setOnClickListener {
            if (isFormInvalid()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.fill_all_fields_toast),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            with(binding) {
                val person = TuringPerson(
                    identifyer = arguments?.getLong(NEW_PERSON_ID, -1L) ?: -1L,
                    name = nameEditText.text.toString(),
                    surname = surnameEditText.text.toString(),
                    age = ageEditText.text.toString().toInt(),
                    isStudent = studentRadioButton.isChecked,
                    avatarUrl = avatarUrlEditText.text.toString()
                )

                val result = if (person.id == -1L) {
                    bundleOf(NEW_PERSON_KEY to null)
                } else {
                    bundleOf(NEW_PERSON_KEY to person)
                }
                setFragmentResult(PersonListFragment.ADD_PERSON_KEY, result)

                (activity as? Navigator)?.navigateToFragment(PersonListFragment.create())
            }
        }
    }

    private fun isFormInvalid(): Boolean {
        with(binding) {
            return@isFormInvalid !studentRadioButton.isChecked && !curatorRadioButton.isChecked
                || ageEditText.text.isNullOrEmpty()
                || avatarUrlEditText.text.isNullOrEmpty()
                || nameEditText.text.isNullOrEmpty()
                || surnameEditText.text.isNullOrEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NEW_PERSON_KEY = "newPerson"
        const val NEW_PERSON_ID = "newPersonId"

        fun create(newPersonId: Long): AddPersonFragment {
            return AddPersonFragment().apply { arguments = bundleOf(NEW_PERSON_ID to newPersonId) }
        }
    }
}