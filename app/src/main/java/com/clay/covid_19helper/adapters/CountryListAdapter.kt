package com.clay.covid_19helper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.clay.covid_19helper.R
import com.clay.covid_19helper.models.WorldCountriesCovidData
import com.clay.covid_19helper.models.WorldCountryCovidData
import com.clay.covid_19helper.ui.fragments.WorldDataFragment
import kotlinx.android.synthetic.main.country_item_layout.view.*
import kotlinx.android.synthetic.main.fragment_world_data.*

class CountryListAdapter(
    val context: Context,
    val worldDataFragment: WorldDataFragment,
    val worldCountriesCovidData: WorldCountriesCovidData
) :
    RecyclerView.Adapter<CountryListAdapter.MyViewHolder>(), Filterable {



    var collapseFun: ((WorldCountryCovidData) -> Unit)? = null

    val filteredData: ArrayList<WorldCountryCovidData> = arrayListOf()

    init {
        worldCountriesCovidData?.data?.let { filteredData.addAll(it) }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.country_item_layout, parent, false)
        )
    }

    override fun getItemCount() = filteredData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val country = filteredData[position]

        holder.itemView.tvCountryName.text = country.location

        holder.itemView.tvCountryCode.text = country.countryCode

        holder.itemView.tvCountryLatLng.text = "${country.latitude}, ${country.longitude}"

//        if (listener != null) {
//            holder.itemView.setOnClickListener(listener)
//        }
        holder.itemView.setOnClickListener {
            collapseFun?.let { it(country) }
            //Toast.makeText(context, country?.location, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterData = arrayListOf<WorldCountryCovidData>()
            val query = constraint?.toString()
            if (query != null) {
                if (query.isEmpty()) {
                    filterData.addAll(worldCountriesCovidData?.data!!)
                } else {
                    for (country in worldCountriesCovidData?.data!!) {
                        if (country.location.toLowerCase().startsWith(query.toLowerCase(), true)) {
                            filterData.add(country)
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
            filteredData.addAll(results?.values as ArrayList<WorldCountryCovidData>)
            notifyDataSetChanged()
        }
    }

}