package com.clay.covid_19helper.models


import com.google.gson.annotations.SerializedName
import timber.log.Timber

data class StatesDaily(
    @SerializedName("an")
    val an: String,
    @SerializedName("ap")
    val ap: String,
    @SerializedName("ar")
    val ar: String,
    @SerializedName("as")
    val _as: String,
    @SerializedName("br")
    val br: String,
    @SerializedName("ch")
    val ch: String,
    @SerializedName("ct")
    val ct: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("dd")
    val dd: String,
    @SerializedName("dl")
    val dl: String,
    @SerializedName("dn")
    val dn: String,
    @SerializedName("ga")
    val ga: String,
    @SerializedName("gj")
    val gj: String,
    @SerializedName("hp")
    val hp: String,
    @SerializedName("hr")
    val hr: String,
    @SerializedName("jh")
    val jh: String,
    @SerializedName("jk")
    val jk: String,
    @SerializedName("ka")
    val ka: String,
    @SerializedName("kl")
    val kl: String,
    @SerializedName("la")
    val la: String,
    @SerializedName("ld")
    val ld: String,
    @SerializedName("mh")
    val mh: String,
    @SerializedName("ml")
    val ml: String,
    @SerializedName("mn")
    val mn: String,
    @SerializedName("mp")
    val mp: String,
    @SerializedName("mz")
    val mz: String,
    @SerializedName("nl")
    val nl: String,
    @SerializedName("or")
    val or: String,
    @SerializedName("pb")
    val pb: String,
    @SerializedName("py")
    val py: String,
    @SerializedName("rj")
    val rj: String,
    @SerializedName("sk")
    val sk: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tg")
    val tg: String,
    @SerializedName("tn")
    val tn: String,
    @SerializedName("tr")
    val tr: String,
    @SerializedName("tt")
    val tt: String,
    @SerializedName("un")
    val un: String,
    @SerializedName("up")
    val up: String,
    @SerializedName("ut")
    val ut: String,
    @SerializedName("wb")
    val wb: String
) {
/*    fun getDataAsMapOfStates(): Map<String, String> {
        Timber.d("Before Map    : $this")
        val casesMap = HashMap<String, String>()

        casesMap["an"] = an
        casesMap["ap"] = ap
        casesMap["ar"] = ar
        casesMap["as"] = _as
        casesMap["br"] = br
        casesMap["ch"] = ch
        casesMap["ct"] = ct
        casesMap.put("dd", dd)
        casesMap.put("dl", dl)
        casesMap.put("dn", dn)
        casesMap.put("ga", ga)
        casesMap.put("gj", gj)
        casesMap.put("hp", hp)
        casesMap.put("hr", hr)
        casesMap.put("jh", jh)
        casesMap.put("jk", jk)
        casesMap.put("ka", ka)
        casesMap.put("kl", kl)
        casesMap.put("la", la)
        casesMap.put("ld", ld)
        casesMap.put("mh", mh)
        casesMap.put("ml", ml)
        casesMap.put("mn", mn)
        casesMap.put("mp", mp)
        casesMap.put("mz", mz)
        casesMap.put("nl", nl)
        casesMap.put("or", or)
        casesMap.put("pb", pb)
        casesMap.put("py", py)
        casesMap.put("rj", rj)
        casesMap.put("sk", sk)
        casesMap.put("tg", tg)
        casesMap.put("tn", tn)
        casesMap["tr"] = tr
        casesMap["un"] = un
        casesMap["up"] = up
        casesMap["ut"] = ut
        casesMap["wb"] = wb

        Timber.d("The map is $casesMap")
        return casesMap
    }*/

    fun getDataAsList(): List<String> {
        val _list = mutableListOf(
            an, ap
            , ar
            , _as
            , br
            , ch
            , ct
            , dd
            , dl
            , dn
            , ga
            , gj
            , hp
            , hr
            , jh
            , jk
            , ka
            , kl
            , la
            , ld
            , mh
            , ml
            , mn
            , mp
            , mz
            , nl
            , or
            , pb
            , py
            , rj
            , sk
            , tg
            , tn
            , tr
            , un
            , up
            , ut
            , wb
        )
        //Timber.d("The converted list = $_list")
        return _list
    }

}