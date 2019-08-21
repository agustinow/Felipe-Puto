package com.odella.bacchus.Controller.ui.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.odella.bacchus.Adapter.WinesCartRecyclerAdapter

import com.odella.bacchus.R
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.view.*


class CartFragment : Fragment() {
    lateinit var model: TabsViewModel
    lateinit var root: View
    lateinit var recyclerAdapter: WinesCartRecyclerAdapter
    lateinit var layoutManager: LinearLayoutManager

    fun checkData() {
        recyclerAdapter.list = model.cart.values.toMutableList()
        recyclerAdapter.notifyDataSetChanged()

        root!!.txtCartItemNum.text = "${model.cart.size} items"
        var totalPrice = 0
        for (e in model.cart) {
            totalPrice += e.value.wine.price!! * e.value.amount
        }
        if (root!!.chkCartShipping.isChecked) totalPrice += 10
        root!!.txtCartTotalPrice.text = "$$totalPrice"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnCartPurchase.setOnClickListener {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        model = ViewModelProviders.of(activity!!)[TabsViewModel::class.java]
        recyclerAdapter = WinesCartRecyclerAdapter(context!!, {
            model.cart.remove(it.wine.wineId)
            checkData()
        },{ wine, num ->
            model.cart[wine.wine.wineId]!!.amount = num
            checkData()
        })
        root = inflater.inflate(R.layout.fragment_cart, container, false)
        root.chkCartShipping.setOnCheckedChangeListener { p0, p1 -> checkData() }


        layoutManager = LinearLayoutManager(context!!)
        val decoration = DividerItemDecoration(context!!, RecyclerView.VERTICAL)
        root.recyclerCart.adapter = recyclerAdapter
        root.recyclerCart.layoutManager = layoutManager
        root.recyclerCart.addItemDecoration(decoration)

        checkData()
        //MEME

        //ENDMEME

        return root
    }

    override fun onResume() {
        super.onResume()
        checkData()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }
}
