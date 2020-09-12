package com.clay.covid_19helper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.clay.covid_19helper.R
import com.clay.covid_19helper.models.CountryCovidData
import com.clay.covid_19helper.models.CountryCovidDataItem
import kotlinx.android.synthetic.main.country_item_layout.view.*
import timber.log.Timber

class ProvinceListAdapter(val data: CountryCovidData) :
    RecyclerView.Adapter<ProvinceListAdapter.MyViewHolder>(), Filterable {

    var collapseFun: ((CountryCovidDataItem) -> Unit)? = null

    private val filteredData: ArrayList<CountryCovidDataItem> = arrayListOf()

    init {
        filteredData.addAll(data as ArrayList<CountryCovidDataItem>)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.country_item_layout, parent, false)
        )
    }

    override fun getItemCount() = filteredData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val province = filteredData[position]
        holder.itemView.tvCountryName.text = if (province.provinceState?.contains("Dadra")!!) {
            "Daman and Diu"
        } else {
            province.provinceState
        }
        holder.itemView.tvCountryCode.text = "IN"
        holder.itemView.tvCountryLatLng.text = "${province.lat}, ${province.long}"

        holder.itemView.setOnClickListener {
            collapseFun?.let { it(province) }
        }

    }

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterData = arrayListOf<CountryCovidDataItem>()
            val query = constraint?.toString()
            if (query != null) {
                if (query.isEmpty()) {
                    filterData.addAll(data as ArrayList<CountryCovidDataItem>)
                } else {
                    for (province in data) {
                        if (province.provinceState?.toLowerCase()
                                ?.startsWith(query.toLowerCase(), true)!!
                        ) {
                            Timber.d("${province.provinceState}")
                            filterData.add(province)
                        }
                    }
                }
            }
            return FilterResults().apply {
                values = filterData
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredData.clear()
            filteredData.addAll(results?.values as ArrayList<CountryCovidDataItem>)
            Timber.d("${filteredData}")
            notifyDataSetChanged()
        }
    }
}