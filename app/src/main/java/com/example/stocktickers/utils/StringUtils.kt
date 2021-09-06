package com.example.stocktickers.utils

import android.content.Context
import android.widget.Toast
import com.example.stocktickers.R

class StringUtils {
    companion object{
        fun showToastForError(context: Context){
            Toast.makeText(context, context.getText(R.string.error_message),Toast.LENGTH_LONG).show()
        }
    }
}