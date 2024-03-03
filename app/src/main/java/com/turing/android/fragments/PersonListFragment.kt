package com.turing.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.turing.android.R
import com.turing.android.databinding.FragmentPersonListBinding
import com.turing.android.ds.DataSourceAction
import com.turing.android.ds.DataSourceListener
import com.turing.android.ds.TuringPersonDs
import com.turing.android.dto.TuringPerson
import com.turing.android.ui.Navigator
import com.turing.android.ui.TuringPersonActionListener
import com.turing.android.ui.TuringPersonAdapter
import com.turing.android.utils.getParcelableObj

/**
 * Фрагмент списка активистов Тьюринга
 *
 * @author Diagorn
 */
class PersonListFragment : Fragment() {

    private var _binding: FragmentPersonListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TuringPersonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = TuringPersonAdapter(object : TuringPersonActionListener {

            override fun onDelete(person: TuringPerson) {
                TuringPersonDs.delete(person)
            }

            override fun onDetails(person: TuringPerson) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.delalis_person_msg, person.name),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        with(binding) {
            turingPersonListView.layoutManager = layoutManager
            turingPersonListView.adapter = adapter
            addButton.setOnClickListener {
                (activity as? Navigator)?.navigateToFragment(
                    AddPersonFragment.create(newPersonId = TuringPersonDs.getNextId())
                )
            }
        }

        with(TuringPersonDs) {
            addListeners(listOf(addPersonListener, deletePersonListener))
            loadObjects()
            getAll().forEach { item -> addPersonListener.perform(item) }
        }

        setFragmentResultListener(ADD_PERSON_KEY) { key, bundle ->
            val newTuringPerson: TuringPerson? =
                bundle.getParcelableObj(AddPersonFragment.NEW_PERSON_KEY)
            newTuringPerson ?: return@setFragmentResultListener
            TuringPersonDs.add(newTuringPerson)
        }
    }

    private val addPersonListener =
        object : DataSourceListener<TuringPerson>(DataSourceAction.INSERT) {
            override fun doPerform(obj: TuringPerson) {
                adapter.addItem(obj)
            }
        }

    private val deletePersonListener =
        object : DataSourceListener<TuringPerson>(DataSourceAction.REMOVE) {
            override fun doPerform(obj: TuringPerson) {
                adapter.removeItem(obj)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ADD_PERSON_KEY = "addPerson"

        fun create(): PersonListFragment {
            return PersonListFragment()
        }
    }
}