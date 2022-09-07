package com.example.socialx.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.socialx.adapters.ViewPagerAdapter
import com.example.socialx.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private var binding :ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater);
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        binding?.tabLayout!!.addTab(binding?.tabLayout!!.newTab().setText("Sign In"))
        binding?.tabLayout!!.addTab(binding?.tabLayout!!.newTab().setText("Sign Up"))
        val adapter=ViewPagerAdapter(supportFragmentManager,lifecycle)
        binding?.viewPager2?.adapter=adapter
        TabLayoutMediator(binding!!.tabLayout,binding!!.viewPager2){tab,position->
            when(position){
                0->{
                    tab.text="Sign In"
                }
                1->{
                    tab.text="Sign Up"
                }
            }

        }.attach()
//        binding?.tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                if (tab != null) {
//                    binding?.viewPager2!!.currentItem=tab.position
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
//            }
//
//        })
//        binding?.viewPager2?.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
//            override fun onPageSelected(position: Int) {
//                binding?.tabLayout?.selectTab(binding?.tabLayout!!.getTabAt(position))
//            }
//        })
    }
}