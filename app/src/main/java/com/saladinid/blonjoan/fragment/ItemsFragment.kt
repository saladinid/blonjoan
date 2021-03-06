package com.saladinid.blonjoan.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.saladinid.blonjoan.R
import com.saladinid.blonjoan.data.ItemsModel
import com.saladinid.blonjoan.handler.CarRecyclerViewDataAdapter
import com.saladinid.blonjoan.handler.CarRecyclerViewItem
import com.saladinid.blonjoan.handler.ItemGridAdapter
import org.json.JSONArray
import org.json.JSONException

import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ItemsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ItemsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemsFragment: Fragment() {

    // TODO: Rename and change types of parameters
    private
    var mParam1: String ? = null
    private
    var mParam2: String ? = null
    private
    var dataDestinations: JSONArray ? = null
    private
    var itemsArrayListBuffer: List < ItemsModel > ? = null
    private
    var itemsArrayList: ArrayList < ItemsModel > ? = null
    private
    var imageUrl: String ? = null
    private
    var mRecyclerView: RecyclerView ? = null

    private
    var mListener: OnFragmentInteractionListener ? = null

    private
    var carItemList: MutableList < CarRecyclerViewItem > ? = null

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup ?, savedInstanceState : Bundle ? ): View ? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_items, container, false)

        // setTitle("dev2qa.com - Android CardView Example.");

        initializeCarItemList()

        getKarmaGroupsApiRequest()

        mRecyclerView = view.findViewById < View > (R.id.card_view_recycler_list) as RecyclerView

        /*

        // Create the recyclerview.
        val carRecyclerView = view.findViewById < View > (R.id.card_view_recycler_list) as RecyclerView
        // Create the grid layout manager with 2 columns.
        val gridLayoutManager = GridLayoutManager(this.activity, 2)
        // Set layout manager.
        carRecyclerView.layoutManager = gridLayoutManager

        // Create car recycler view data adapter with car item list.
        val carDataAdapter = CarRecyclerViewDataAdapter(carItemList)
        // Set data adapter.
        carRecyclerView.adapter = carDataAdapter

        */

        return view
    }


    /* Initialise car items in list. */
    private fun initializeCarItemList() {
        if (carItemList == null) {
            carItemList = ArrayList()
            carItemList!!.add(CarRecyclerViewItem("Cabai", R.drawable.cabai))
            carItemList!!.add(CarRecyclerViewItem("Garam", R.drawable.garam))
            carItemList!!.add(CarRecyclerViewItem("Bawang Merah", R.drawable.onion))
            carItemList!!.add(CarRecyclerViewItem("Bawang Putih", R.drawable.garlic))
            carItemList!!.add(CarRecyclerViewItem("Kangkung", R.drawable.kankung))
            carItemList!!.add(CarRecyclerViewItem("Tempeh", R.drawable.tempe))
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context ? ) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): ItemsFragment {
            val fragment = ItemsFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    private fun processData() {
        try {
            val dataJson = dataDestinations
            itemsArrayListBuffer = ArrayList <ItemsModel> ()
            itemsArrayList = ArrayList <ItemsModel> ()
//            mRecyclerView = findViewById(R.id.recyclerview)
            val mGridLayoutManager = GridLayoutManager(this.context, 2)
            mRecyclerView!!.layoutManager = mGridLayoutManager
            itemsArrayList!!.clear()
            val dma = ArrayList <ItemsModel> ()
            dma.clear()
            for (j in 0 until dataJson!!.length()) {
                val job = dataJson.getJSONObject(j)
                val model = ItemsModel(
                        job.optString("_id"),
                        job.optString("name"),
                        job.optString("image"),
                        job.optString("category"),
                        job.optString("unit"),
                        job.optString("price")
                )
                dma.add(model)
                itemsArrayList!!.add(model)
            }
            itemsArrayListBuffer = itemsArrayList
            val myAdapter = itemsArrayListBuffer?.let {
                ItemGridAdapter(this.context!!, it)
            }
            mRecyclerView!!.adapter = myAdapter

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun getKarmaGroupsApiRequest() {
        val linkTrang = "http://familygroceries.herokuapp.com/items"
        val queue = Volley.newRequestQueue(this.context)
        val stringRequest = object: StringRequest(Request.Method.GET, linkTrang,
                Response.Listener < String > {
                    response ->
                    Log.d("TAG", response.toString())
                    dataDestinations = JSONArray(response)
                    Log.d("dataDestinations", dataDestinations.toString())
                    processData()
                },
                Response.ErrorListener {}) {
            override fun getHeaders(): MutableMap < String, String > {
                val headers = HashMap < String, String > ()
                return headers
            }
        }
        queue.add(stringRequest)
    }

    override fun onResume() {
        super.onResume()
        getKarmaGroupsApiRequest()
    }



} // Required empty public constructor