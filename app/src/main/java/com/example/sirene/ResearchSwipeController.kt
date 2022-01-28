package com.example.tp1

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ResearchSwipeController(): ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0,
            ItemTouchHelper.END)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        /*val task = taskdao.getOneFromPosition(category.id as Long, viewHolder.layoutPosition.toLong())
        taskdao.delete(task)
        taskAdapter.notifyDataSetChanged()*/
    }

}