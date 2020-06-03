package com.example.app1gsondata.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.denzcoskun.imageslider.models.SlideModel

import com.example.app1gsondata.R
import com.example.app1gsondata.adapters.AdapterCategory
import com.example.app1gsondata.app.EndPoint
import com.example.app1gsondata.models.Category
import com.example.app1gsondata.models.CategoryList
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.recycler_view


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    var list : ArrayList<Category> = ArrayList()
    lateinit var myadapter : AdapterCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_home, container, false)
        networkCall()
        initView(view)
        return view
    }

    private fun networkCall() {

//        var requestQueue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, EndPoint.getCategoryURL(),
            Response.Listener {
                var gson = GsonBuilder().create()
                var list = gson.fromJson(it.toString(), CategoryList::class.java)
                myadapter.setData(list.data)
            } , Response.ErrorListener {
                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
            } )
        Volley.newRequestQueue(activity).add(stringRequest)
    }

    private fun initView(view: View) {
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://lh3.googleusercontent.com/proxy/xPSE2nswgWYtjUMvnU7ZuS9ga45Oija1n_gZ0fvbu5gUo5teJ2TfLMfDU7PC857j3RPftLNd5FS23u1q98pkMvSHEZUz_rU740bSZqxmr-HpzzkiSnkRxwwUwP52urJ0KZGl1aEPTJ1EcmJaFQ"))
        imageList.add(SlideModel("https://yt3.ggpht.com/a/AATXAJw9A-TFtzSWmXKoFv-xIzsZU9HKsGn7Qvaqmw=s900-c-k-c0xffffffff-no-rj-mo"))
        imageList.add(SlideModel("https://www.doh.wa.gov/portals/1/images/8380/completeeats-full.jpg"))
        view.image_view_slider.setImageList(imageList)
//        Picasso
//            .get()
//            .load("https://apollo-singapore.akamaized.net/v1/files/xqv00lg2sj811-IN/image")
//            .into(view.image_view)

        myadapter = AdapterCategory(activity!!)
        view.recycler_view.adapter = myadapter
        // recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        view.recycler_view.layoutManager = GridLayoutManager(activity, 2)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
