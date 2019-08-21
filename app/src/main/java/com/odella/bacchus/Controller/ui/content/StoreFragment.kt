package com.odella.bacchus.Controller.ui.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.odella.bacchus.Adapter.WinesRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_client_main.view.*
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import android.graphics.drawable.Drawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.Canvas
import android.graphics.drawable.TransitionDrawable
import androidx.recyclerview.widget.DividerItemDecoration
import com.odella.bacchus.Constants.WINE_ID
import com.odella.bacchus.Controller.DialogWineActivity
import com.odella.bacchus.Model.WineCartFeed
import com.odella.bacchus.R



/**
 * A placeholder fragment containing a simple view.
 */
class StoreFragment : Fragment(){

    private lateinit var tabsViewModel: TabsViewModel
    lateinit var model: TabsViewModel
    lateinit var recyclerAdapter: WinesRecyclerAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var gridLayoutManager: GridLayoutManager

    fun updateCartlist(){
        recyclerAdapter.cartList = model.cart.keys
        recyclerAdapter.notifyDataSetChanged()
    }

    private fun getBitmapDrawableFromVectorDrawable(context: Context, drawableId: Int): BitmapDrawable {
        return BitmapDrawable(context.resources, getBitmapFromVectorDrawable(context, drawableId))
    }

    private fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
        var drawable = ContextCompat.getDrawable(context, drawableId)!!
        val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_client_main, container, false)

        //MEMES
        val td = TransitionDrawable(
            arrayOf<Drawable>(
                getBitmapDrawableFromVectorDrawable(context!!, R.drawable.icon_view_list),
                getBitmapDrawableFromVectorDrawable(context!!, R.drawable.icon_view_grid)
            )
        )
        td.isCrossFadeEnabled = true
        val decoration = DividerItemDecoration(context!!, RecyclerView.VERTICAL)
        model = ViewModelProviders.of(activity!!)[TabsViewModel::class.java]
        recyclerAdapter = WinesRecyclerAdapter(context!!,
        {
            var intent = Intent(context!!, DialogWineActivity::class.java)
            intent.putExtra(WINE_ID,it.wineId)
            startActivity(intent)


        },{
                return@WinesRecyclerAdapter if(model.cart.containsKey(it.wineId)){
                    model.cart.remove(it.wineId)
                    recyclerAdapter.cartList = model.cart.keys
                    true
                } else {
                    model.cart[it.wineId!!] = WineCartFeed(it, 1)
                    recyclerAdapter.cartList = model.cart.keys
                    false
                }
        })
        recyclerAdapter.cartList = model.cart!!.keys
        linearLayoutManager = LinearLayoutManager(context!!)
        gridLayoutManager = GridLayoutManager(context!!, 2, RecyclerView.VERTICAL, false)
        root.recycler.adapter = recyclerAdapter
        root.recycler.layoutManager = linearLayoutManager
        recyclerAdapter.setWineListItems(model.wines.value!!)
        model.wines.observe(this, Observer{
            recyclerAdapter.setWineListItems(it)
        })

        if(model.linearView){
            root.recycler.layoutManager = linearLayoutManager
            recyclerAdapter.isLinear = true
            root.recycler.adapter = null
            recyclerAdapter.notifyDataSetChanged()
            root.recycler.adapter = recyclerAdapter
            td.startTransition(0)
            root.recycler.addItemDecoration(decoration)
        } else {
            root.recycler.layoutManager = gridLayoutManager
            recyclerAdapter.isLinear = false
            root.recycler.adapter = null
            recyclerAdapter.notifyDataSetChanged()
            root.recycler.adapter = recyclerAdapter
            td.reverseTransition(0)
        }
        root.btnChangeLayout.background = td

        root.btnChangeLayout.setOnClickListener {
            model.linearView = !model.linearView

            if(model.linearView){
                root.recycler.addItemDecoration(decoration)
                root.recycler.layoutManager = linearLayoutManager
                recyclerAdapter.isLinear = true
                root.recycler.adapter = null
                recyclerAdapter.notifyDataSetChanged()
                root.recycler.adapter = recyclerAdapter

                td.reverseTransition(0)
                td.startTransition(200)
            } else {
                root.recycler.removeItemDecoration(decoration)
                root.recycler.layoutManager = gridLayoutManager
                recyclerAdapter.isLinear = false
                root.recycler.adapter = null
                recyclerAdapter.notifyDataSetChanged()
                root.recycler.adapter = recyclerAdapter

                td.startTransition(0)
                td.reverseTransition(200)

            }

        }

        root.btnOpenCart.setOnClickListener {

            Toast.makeText(context, "meme", Toast.LENGTH_SHORT).show()

            var intent = Intent(context!!, DialogWineActivity::class.java)
            startActivity(intent)
        }
        //ENDMEMES
        return root
    }

    override fun onResume() {
        super.onResume()
        updateCartlist()
    }

    companion object {
        @JvmStatic
        fun newInstance() = StoreFragment()
    }
}