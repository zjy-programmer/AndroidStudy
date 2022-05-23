package com.example.androidstudy.any.tablayout

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.androidstudy.R
import com.example.androidstudy.databinding.ActivityTabLayoutBinding
import com.google.android.material.tabs.TabLayout

class TabLayoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityTabLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = MyFragmentAdapter(listOf(MyFragment("1"), MyFragment("2"), MyFragment("3")), supportFragmentManager)

        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getTabAt(0)!!.customView = getTabView(0)
        binding.tabLayout.getTabAt(1)!!.customView = getTabView(1)
        binding.tabLayout.getTabAt(2)!!.customView = getTabView(2)
    }

     private fun getTabView(position: Int): View {
         val view: View = LayoutInflater.from(this).inflate(R.layout.item_tab, null)
         val title = when (position) {
             0 -> "资讯"
             1 -> "热榜"
             else -> "视听"
         }
        view.findViewById<TextView>(R.id.tv_tab).text = title
        return view
    }

    class MyFragmentAdapter(val list: List<MyFragment>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount() = list.size

        override fun getItem(position: Int) = list[position]

        override fun getPageTitle(position: Int) = "position$position"
    }

    class MyFragment(val s: String) : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_tab_content, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            view.findViewById<TextView>(R.id.tv_1).text = s
        }
    }


}

class MyTextView(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    val min = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics)
    val max = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 24f, resources.displayMetrics)
    var fontAnimation = min
        set(value) {
            field = value
            textSize = field
            invalidate()
        }
    private val fontAnimator: ObjectAnimator = ObjectAnimator.ofFloat(this, "fontAnimation", min, max).apply {
        duration = 1000
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            if (paint.strokeWidth == min) {
                fontAnimator.start()
            } else {
                fontAnimator.reverse()
            }
            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }
}