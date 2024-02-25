package com.turing.android.ui

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turing.android.R
import com.turing.android.databinding.TuringPersonItemBinding
import com.turing.android.dto.TuringPerson
import kotlin.system.exitProcess


/**
 * Адаптер списка активистов тьюринга
 *
 * @author Diagorn
 */
class TuringPersonAdapter(
    private val context: Context,
    private val actionListener: TuringPersonActionListener
) : RecyclerView.Adapter<TuringPersonViewHolder>() {

    private val turingPersons: MutableList<TuringPerson> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TuringPersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TuringPersonItemBinding.inflate(inflater, parent, false)
        return TuringPersonViewHolder(binding)
    }

    override fun getItemCount() = turingPersons.size

    override fun onBindViewHolder(holder: TuringPersonViewHolder, position: Int) {
        val person = turingPersons[position]
        with(holder.binding) {
            root.setOnClickListener { onClick(it, person) }
            moreImageViewButton.setOnClickListener { onClick(it, person) }
        }

        with(holder.binding) {
            nameTextView.text = context.getString(
                R.string.turing_person_name_string, person.name, person.surname, person.age
            )
            positionTextView.text =
                if (person.isStudent) context.getString(R.string.position_student)
                else context.getString(R.string.position_curator)
            Glide.with(avatarImageView.context)
                .load(person.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(avatarImageView)
        }
    }

    /**
     * Добавить новый элемент в список
     *
     * @param item - новый активист
     */
    fun addItem(item: TuringPerson) {
        this.turingPersons.add(item)
        this.notifyItemInserted(item.id.toInt() - 1)
    }

    /**
     * Удалить элемент из списка
     * Удаление происходит только если получилось найти активиста в списке
     *
     * @param item - удаляемый активист
     */
    fun removeItem(item: TuringPerson) {
        val index = this.turingPersons.indexOfFirst { it.id == item.id }
        if (index != -1) { // Объект по айдишнику найден
            this.turingPersons.removeAt(index)
            this.notifyItemRemoved(item.id.toInt() - 1)
        }
    }

    private fun onClick(v: View, person: TuringPerson) {
        when (v.id) {
            R.id.moreImageViewButton -> showPopupMenu(v, person)
            else -> actionListener.onDetails(person)
        }
    }

    private fun showPopupMenu(view: View, person: TuringPerson) {
        val popupMenu = PopupMenu(view.context, view)

        val deleteText = SpannableString(context.getString(R.string.delete_person_text))
        deleteText.setSpan(ForegroundColorSpan(Color.RED), 0, deleteText.length, 0)

        with(popupMenu.menu) {
            add(0, DELETE_ID, Menu.FIRST, deleteText)
            add(0, FUCK_OFF_ID, Menu.NONE, context.getString(R.string.fuck_off_text))
        }

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                DELETE_ID -> actionListener.onDelete(person)
                // А что это такое - секрет :)
                FUCK_OFF_ID -> exitProcess(-1)
            }

            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    companion object {
        private const val DELETE_ID = 1
        private const val FUCK_OFF_ID = 2
    }
}

/**
 * Вьюхолдер элемента списка активистов тьюринга
 *
 * @param binding - биндинг элемента списка
 *
 * @author Diagorn
 */
class TuringPersonViewHolder(val binding: TuringPersonItemBinding) :
    RecyclerView.ViewHolder(binding.root)

/**
 * Лисенер действий над элементом списка
 *
 * @author Diagorn
 */
interface TuringPersonActionListener {
    /**
     * Действие после удаления активиста
     * @param person - удаляемый активист
     */
    fun onDelete(person: TuringPerson)

    /**
     * Действие после клика на активиста
     * @param person - активист, на котором кликнули
     */
    fun onDetails(person: TuringPerson)
}