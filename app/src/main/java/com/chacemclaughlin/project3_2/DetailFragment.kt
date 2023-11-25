package com.chacemclaughlin.project3_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.toolbox.Volley
import com.chacemclaughlin.project3_2.databinding.FragmentDetailBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private lateinit var viewModel: DetailViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        var id = arguments?.getString("ID").toString()
        var queue = Volley.newRequestQueue(context)
        viewModel.displayCurrencyInfo(queue,id)


        val nameObserver=Observer<String>{name-> binding.name.text=name}
        viewModel.getName().observe(viewLifecycleOwner,nameObserver)

        val symbolObserver=Observer<String>{symbol-> binding.symbol.text=symbol}
        viewModel.getSymbol().observe(viewLifecycleOwner,symbolObserver)

        val supplyObserver=Observer<String>{supply-> binding.supply.text=supply}
        viewModel.getSupply().observe(viewLifecycleOwner,supplyObserver)

        val priceObserver=Observer<String>{price-> binding.price.text=price}
        viewModel.getPrice().observe(viewLifecycleOwner,priceObserver)

        val changeObserver=Observer<String>{change-> binding.change.text=change}
        viewModel.getChange().observe(viewLifecycleOwner,changeObserver)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}