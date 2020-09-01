package com.clay.covid_19helper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    val worldDataFragment: WorldDataFragment
) :
    RecyclerView.Adapter<CountryListAdapter.MyViewHolder>() {

    var worldCountriesCovidData: WorldCountriesCovidData? = null

    var collapseFun: ((WorldCountryCovidData)->Unit)? =null

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.country_item_layout, parent, false)
        )
    }

    override fun getItemCount() = worldCountriesCovidData?.data?.size ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val country = worldCountriesCovidData?.data?.get(position)

        holder.itemView.tvCountryName.text = country?.location

        holder.itemView.tvCountryCode.text = country?.countryCode

        holder.itemView.tvCountryLatLng.text = "${country?.latitude}, ${country?.longitude}"

//        if (listener != null) {
//            holder.itemView.setOnClickListener(listener)
//        }
        holder.itemView.setOnClickListener {
            collapseFun?.let { it(country!!) }
            //Toast.makeText(context, country?.location, Toast.LENGTH_SHORT).show()
        }
    }

}