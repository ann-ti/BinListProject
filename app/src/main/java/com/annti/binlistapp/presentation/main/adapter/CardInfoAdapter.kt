package com.annti.binlistapp.presentation.main.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.annti.binlistapp.R
import com.annti.binlistapp.databinding.ItemCardBinding
import com.annti.binlistapp.entity.CardInfoEntity
import com.annti.binlistapp.utils.inflate
import javax.inject.Inject

class CardInfoAdapter @Inject constructor(
    private val itemSelected: ItemSelected
) : RecyclerView.Adapter<CardInfoAdapter.CardInfoViewHolder>() {

    private val cardsDiffer = AsyncListDiffer(this, DiffUtilCallback())
    fun submitList(list: List<CardInfoEntity>) = cardsDiffer.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardInfoViewHolder {
        return CardInfoViewHolder(parent.inflate(R.layout.item_card), itemSelected)
    }

    override fun onBindViewHolder(holder: CardInfoViewHolder, position: Int) {
        val card = cardsDiffer.currentList[position]
        holder.bind(card)
    }

    override fun getItemCount(): Int =
        cardsDiffer.currentList.size

    class CardInfoViewHolder(
        private val view: View,
        private val itemSelected: ItemSelected
    ) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCardBinding.bind(view)
        fun bind(card: CardInfoEntity) {
            view.setOnClickListener {
                itemSelected.navigateToDetails(card)
            }
            with(binding) {
                numberCard.text = card.cardNumber
                schema.text = card.scheme
                currencyValue.text = card.currency
                countryValue.text = card.nameCountry
            }
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<CardInfoEntity>() {
    override fun areItemsTheSame(oldItem: CardInfoEntity, newItem: CardInfoEntity) =
        oldItem.cardNumber == newItem.cardNumber

    override fun areContentsTheSame(oldItem: CardInfoEntity, newItem: CardInfoEntity) =
        oldItem == newItem
}

interface ItemSelected {
    fun navigateToDetails(card: CardInfoEntity)
}