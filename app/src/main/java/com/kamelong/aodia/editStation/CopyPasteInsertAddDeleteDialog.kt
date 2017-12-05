package com.kamelong.aodia.editStation

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import com.kamelong.aodia.R
import javax.sql.ConnectionEventListener

/**
 * コピー、貼り付け、挿入、追加、削除を選択させるDialog
 */
class CopyPasteInsertAddDeleteDialog(context: Context,listener:CopyPasteInsertAddDeleteInterface) : AlertDialog(context) {
    val view = LayoutInflater.from(context).inflate(R.layout.cpiad_dialog, null)
    constructor(context: Context,listener:CopyPasteInsertAddDeleteInterface,enable:Boolean):this(context,listener){
        view.findViewById<Button>(R.id.pasteButton).isEnabled=enable
    }
    init{
        setView(view)
        view.findViewById<Button>(R.id.copyButton).setOnClickListener { listener.onClickCopyButton() }
        view.findViewById<Button>(R.id.pasteButton).setOnClickListener {listener.onClickPasteButton()  }
        view.findViewById<Button>(R.id.insertButton).setOnClickListener { listener.onClickInsertButton() }
        view.findViewById<Button>(R.id.addButton).setOnClickListener {listener.onClickAddButton()  }
        view.findViewById<Button>(R.id.deleteButton).setOnClickListener { listener.onClickDeleteButton() }
    }


    interface CopyPasteInsertAddDeleteInterface{
        fun onClickCopyButton()
        fun onClickPasteButton()
        fun onClickInsertButton()
        fun onClickAddButton()
        fun onClickDeleteButton()
    }
}
