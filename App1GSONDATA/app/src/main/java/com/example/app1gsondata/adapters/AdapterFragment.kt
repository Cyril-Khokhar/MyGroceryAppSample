package com.example.app1gsondata.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.app1gsondata.fragments.ProductFragment
import com.example.app1gsondata.models.SubCategory

class AdapterFragment(fm  : FragmentManager) : FragmentPagerAdapter(fm){
    var fragmentList : ArrayList<Fragment> = ArrayList()
    var titleList : ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    fun addFragment(subCategory : SubCategory){
        fragmentList.add(ProductFragment.newInstance(subCategory.subId))
        titleList.add(subCategory.subName)
    }

}