package com.turing.android.fragments

import android.os.Bundle
import android.util.Log
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
import com.turing.android.ui.TuringPersonAdapter
import com.turing.android.utils.getParcelableObj
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Фрагмент списка активистов Тьюринга
 *
 * @author Diagorn
 */
class PersonListFragment : Fragment() {

    private var _binding: FragmentPersonListBinding? = null
    private val disposable = CompositeDisposable()
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

        adapter = TuringPersonAdapter({ person ->
            TuringPersonDs.delete(person)
        }, { person ->
            Toast.makeText(
                requireContext(),
                getString(R.string.delalis_person_msg, person.name),
                Toast.LENGTH_SHORT
            ).show()
        })
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        with(binding) {
            turingPersonListView.layoutManager = layoutManager
            turingPersonListView.adapter = adapter
            addButton.setOnClickListener {
                val activity = activity ?: return@setOnClickListener
                activity.supportFragmentManager.beginTransaction()
                    .add(
                        R.id.fragmentContainerId,
                        AddPersonFragment.create(newPersonId = TuringPersonDs.getNextId())
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }

        with(TuringPersonDs) {
            addListeners(listOf(addPersonListener, deletePersonListener))
            loadObjects()
            disposable.add(
                getAllAsObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        addPersonListener.perform(it)
                    }, logError)
            )
        }

        setFragmentResultListener(ADD_PERSON_KEY) { key, bundle ->
            val newTuringPerson: TuringPerson? =
                bundle.getParcelableObj(AddPersonFragment.NEW_PERSON_KEY)
            newTuringPerson ?: return@setFragmentResultListener
            disposable.add(
                Single.just(newTuringPerson)
                    .delay(3, TimeUnit.SECONDS) // Добавляем задержку в 3 секунды
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        TuringPersonDs.add(it)
                    }, logError)
            )
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

    private val logError: (t: Throwable) -> Unit = { error ->
        Log.e(LOG_TAG_SUBSCRIBE_FAILED, error.message ?: "Ошибка")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        disposable.clear()
    }

    companion object {
        const val ADD_PERSON_KEY = "addPerson"
        const val LOG_TAG_SUBSCRIBE_FAILED = "subscribeFailed"

        fun create(): PersonListFragment {
            return PersonListFragment()
        }
    }
}