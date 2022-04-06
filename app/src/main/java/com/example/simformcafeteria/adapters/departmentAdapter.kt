package com.example.simformcafeteria.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.simformcafeteria.Model.Departments
import com.example.simformcafeteria.R
import com.example.simformcafeteria.Utils.ZERO

class departmentAdapter(private val mContext: Context, private val mLayoutResourceId: Int, department: ArrayList<Departments>?) :
    ArrayAdapter<Departments>(mContext, mLayoutResourceId, department!!) {
    private var department: MutableList<Departments> = ArrayList(department)
    private var alldepartments: List<Departments> = ArrayList(department)

    override fun getCount(): Int {
        return department.size
    }

    override fun getItem(position: Int): Departments {
        return department[position]
    }

    override fun getItemId(position: Int): Long {
        return department[position].id.toLong()
    }

    fun setListData(data: ArrayList<Departments>) {
        this.department = data
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = (mContext as Activity).layoutInflater
            convertView = inflater.inflate(mLayoutResourceId, parent, false)
        }
        try {
            val department: Departments = getItem(position)
            val departmentAutoCompleteView = convertView!!.findViewById<View>(R.id.tvCustom) as TextView
            departmentAutoCompleteView.text = department.dname
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertView!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun convertResultToString(resultValue: Any): String {
                return (resultValue as Departments).dname
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    val departmentSuggestion: MutableList<Departments> = ArrayList()
                    for (item in alldepartments) {
                        if (item.dname.lowercase().startsWith(constraint.toString().lowercase())) {
                            departmentSuggestion.add(item)
                        }
                    }
                    filterResults.values = departmentSuggestion
                    filterResults.count = departmentSuggestion.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                department.clear()
                if (results.count > ZERO) {
                    for (result in results.values as List<*>) {
                        if (result is Departments) {
                            department.add(result)
                        }
                    }
                    notifyDataSetChanged()
                } else if (constraint == null) {
                    department.addAll(alldepartments)
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}

