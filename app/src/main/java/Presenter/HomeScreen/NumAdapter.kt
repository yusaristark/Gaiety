package Presenter.HomeScreen

import Model.EventData.Event
import Model.EventData.Value
import View.Activities.ItemMore
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import View.Fragments.HomeScreen.HomeFragment
import android.icu.util.LocaleData
import android.os.Build
import android.util.EventLog
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.gaiety.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NumAdapter(var homeFeed: Event) : RecyclerView.Adapter<NumAdapter.NumHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumHolder {
        val itemHolder = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item, parent, false)
        return NumHolder(itemHolder, homeFeed)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NumHolder, position: Int) {
        holder.itemView.homeName.text = Html.fromHtml(homeFeed.values.get(position).name)
        holder.itemView.homeCity.text = homeFeed.values.get(position).location.city
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
        val date =  LocalDateTime.parse(homeFeed.values.get(position).starts_at, formatter)
        holder.itemView.homeDate.text = "Дата: " + date.dayOfMonth.toString() + ":" + date.monthValue + ":" + date.year +
                 "\nВремя: " + date.hour + ":%02d".format(date.minute.toInt())

        if (homeFeed?.values?.get(position)?.poster_image == null) {
            holder.itemView.homeImageUrl.setImageResource(R.drawable.logo)
        } else {
            Picasso.get()
                .load(homeFeed?.values?.get(position)?.poster_image?.default_url)
                .into(holder.itemView.homeImageUrl)
        }
    }

    override fun getItemCount(): Int {
        return homeFeed.values.count()
    }

    fun addItem(item: Value){
        homeFeed.values  = homeFeed.values + item
    }


    class NumHolder(view: View, var homeFeed: Event) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                val intent = Intent(view.context, ItemMore::class.java)
                intent.putExtra("eventId", homeFeed.values.get(adapterPosition).id)
                view.context.startActivity(intent)
            }
        }
    }
}
