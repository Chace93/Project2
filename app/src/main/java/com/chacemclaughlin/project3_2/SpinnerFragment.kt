package com.chacemclaughlin.project3_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.volley.toolbox.Volley

import com.chacemclaughlin.project3_2.databinding.FragmentSpinnerBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SpinnerFragment : Fragment() {

    private var _binding: FragmentSpinnerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: SpinnerViewModel
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSpinnerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//      binding.buttonFirst.setOnClickListener {
//         findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//      }

        viewModel= ViewModelProvider(this)[SpinnerViewModel::class.java]
        val queue = Volley.newRequestQueue(context)
        viewModel.setCurrencies(queue)


        val currencyNames:ArrayList<String> = arrayListOf()

        val currencyObserver= Observer<ArrayList<CryptoCurrency>>{currencies->
        currencyNames.clear()
            for(currency in currencies){
                currencyNames.add(currency.name)
            }


        adapter= ArrayAdapter(this.requireContext(),android.R.layout.simple_selectable_list_item,currencyNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter=adapter
        binding.spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (currencyNames[p2]!= "Select a currency"){
                    Toast.makeText(context,currencyNames[p2],Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_SpinnerFragment_to_DetailFragment
                        ,bundleOf("ID" to currencies[p2].id)
                    )
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

        viewModel.getCurrencies().observe(viewLifecycleOwner,currencyObserver)
}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}