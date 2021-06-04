package com.yura.newaws.ui.troubleCodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yura.newaws.R
import com.yura.newaws.ui.troubleCodes.trouble_code.TroubleCodesContent


class TroubleCodeFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trouble_list, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.list)
        recycler.adapter = MyTroubleCodeRecyclerViewAdapter(TroubleCodesContent.ITEMS)
        val layManager = LinearLayoutManager(context)
        recycler.layoutManager = layManager
        val dividerItemDecoration = DividerItemDecoration(
            recycler.getContext(),
            layManager.orientation
        )
        recycler.addItemDecoration(dividerItemDecoration)






        return view
    }


}