package com.example.kortlinsampleform.LocationSearchActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kortlinsampleform.R
import com.example.kortlinsampleform.inflate

class SearchResultsAdapter(searchResults: List<String>,listener: ONNClickListener): RecyclerView.Adapter<SearchResultsAdapter.SearchResultsHolder>() {

    private var results: List<String>? = null
    private var onnClickListener: ONNClickListener? = null
    init {
        results = searchResults
        onnClickListener = listener
    }

    fun updatedSearchResults(searchResults: List<String>) {
        results = searchResults
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsHolder {

        val inflatedView = parent.inflate(R.layout.search_results_holder,false)
        return SearchResultsHolder(inflatedView,onnClickListener)
    }

    override fun getItemCount(): Int {
       return results!!.size
    }

    override fun onBindViewHolder(holder: SearchResultsHolder, position: Int) {
        holder.bindresult(results!!.get(position))
    }

    // SearchResultsHolder Class & Implementation
    inner class SearchResultsHolder(itemView: View, listener: ONNClickListener?): RecyclerView.ViewHolder(itemView) {
        private var resultTextView: TextView
        private var onnClickListener: ONNClickListener? = null
        init {
            resultTextView = itemView.findViewById(R.id.searchResults)
            onnClickListener = listener

            itemView.setOnClickListener {
                onnClickListener!!.onClick(adapterPosition)
            }
        }

        fun bindresult(value: String) {
            resultTextView.text = value
        }
    }

    interface ONNClickListener {
        fun onClick(position: Int)
    }
}