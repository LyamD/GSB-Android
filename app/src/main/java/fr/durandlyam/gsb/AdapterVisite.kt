package fr.durandlyam.gsb

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.durandlyam.gsb.database.Visite
import fr.durandlyam.gsb.databinding.LayoutVisiteListItemBinding

class AdapterVisite :
    ListAdapter<Visite, AdapterVisite.ViewHolder>(VisitesDiffCallback()) {

    class VisitesDiffCallback : DiffUtil.ItemCallback<Visite>() {
        override fun areItemsTheSame(oldItem: Visite, newItem: Visite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Visite, newItem: Visite): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(val binding: LayoutVisiteListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val titre: TextView = binding.visiteItemTitre
        val sousTitre: TextView = binding.visiteItemSousTitre
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutVisiteListItemBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.titre.text = item.visiteurNom
        holder.sousTitre.text = item.date
    }


}