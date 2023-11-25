package com.chacemclaughlin.project3_2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

class DetailViewModel:ViewModel() {
    private var name: MutableLiveData<String> = MutableLiveData()
    private var symbol: MutableLiveData<String> = MutableLiveData()
    private var supply: MutableLiveData<String> = MutableLiveData()
    private var price: MutableLiveData<String> = MutableLiveData()
    private var change: MutableLiveData<String> = MutableLiveData()

    fun getName(): MutableLiveData<String>{
        return name;
    }
    fun getSymbol(): MutableLiveData<String>{
        return symbol;
    }
    fun getSupply(): MutableLiveData<String>{
        return supply;
    }
    fun getPrice(): MutableLiveData<String>{
        return price;
    }
    fun getChange(): MutableLiveData<String>{
        return change;
    }


    fun displayCurrencyInfo(queue: RequestQueue, id: String){
        val url="https://api.coincap.io/v2/assets/$id"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val obj= JSONObject(response)
                var data = obj.getJSONObject("data")
                name.setValue(data.getString("name"))
                symbol.setValue(data.getString("symbol"))
                var s = data.getDouble("supply")
                supply.setValue("%.0f".format(s))
                var p = data.getDouble("priceUsd")
                price.setValue("%.2f".format(p))
                var c = data.getDouble("changePercent24Hr")
                change.setValue("%.2f".format(c))
            },
            Response.ErrorListener {  })


        queue.add(stringRequest)
    }

}