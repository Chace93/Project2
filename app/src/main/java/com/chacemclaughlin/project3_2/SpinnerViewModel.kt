package com.chacemclaughlin.project3_2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

class SpinnerViewModel:ViewModel() {
    private var currencies:MutableLiveData<ArrayList<CryptoCurrency>> = MutableLiveData()
    private var currencyList:ArrayList<CryptoCurrency> = ArrayList()


    fun getCurrencies():MutableLiveData<ArrayList<CryptoCurrency>>{
        return currencies
    }

    fun setCurrencies(queue: RequestQueue){
        currencyList.clear()
        currencyList.add(CryptoCurrency("","Select a Currency"))
        val url="https://api.coincap.io/v2/assets"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val obj= JSONObject(response)
                var data = obj.getJSONArray("data")
                for(i in 0 until data.length()){
                    var id = data.getJSONObject(i).getString("id")
                    var name = data.getJSONObject(i).getString("name")
                    currencyList.add(CryptoCurrency(id, name))
                }
                currencies.setValue(currencyList)
            },
            Response.ErrorListener {  })


        queue.add(stringRequest)
    }




}