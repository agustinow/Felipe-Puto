package com.odella.bacchus.Controller.ui.content

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

private val TAB_TITLES = arrayOf(
    "",
    "Store",
    "Contact Us",
    "Find Us"
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                val frag = CartFragment.newInstance()
                return frag
            }
            1 -> {
                val frag = StoreFragment.newInstance()
                return frag
            }
            2 -> {
                val frag = StoreFragment.newInstance()
                return frag
            }
            3 -> {
                val frag = StoreFragment.newInstance()
                return frag
            }
        }
        val frag = StoreFragment.newInstance()
        return frag
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return 4
    }
}