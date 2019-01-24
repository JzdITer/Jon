package com.jzd.android.jon.app.module.excel

import android.os.Bundle
import android.os.Environment
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.utils.JLog
import com.jzd.android.jon.utils.JToast
import jxl.Workbook
import jxl.write.Label
import kotlinx.android.synthetic.main.activity_excel.*
import java.io.File

class ExcelActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excel)

        setOnClick(mBtnInput,mBtnOutput)
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.mBtnInput ->
            {
                readFromExcel()
            }
            R.id.mBtnOutput->
            {
                write2Excel()
            }
        }
    }

    private fun write2Excel()
    {
        try
        {
            val file = File(Environment.getExternalStorageDirectory(), "demo.xlsx")
            if(file.exists())
            {
                file.delete()
            }else
            {
                file.mkdirs()
            }
            file.createNewFile()
            val workbook = Workbook.createWorkbook(file)
            val sheet = workbook.createSheet("a",0)
            for(i in 0..3)
            {
                val labelName = Label(0,i,"第${i}个人")
                val labelSeat = Label(1,i,"第${i}的座次")
                sheet.addCell(labelName)
                sheet.addCell(labelSeat)
            }
            workbook.write()
            workbook.close()
        } catch(e: Exception)
        {
            JLog.e(e)
        }
    }

    /**
     * 表格格式
     *
     * 姓名 / 座次
     */
    private fun readFromExcel()
    {
        val file = File(Environment.getExternalStorageDirectory(), "demo.xlsx")
        if(!file.exists())
        {
            JToast.show("文件不存在")
            return
        }
        val list = arrayListOf<ExcelBean>()
        // 打开文件
        val workbook = Workbook.getWorkbook(file)
        // 打开第一张表
        if(workbook.sheets.isNotEmpty())
        {
            val sheet = workbook.getSheet(0)
            // 行数
            val rows = sheet.rows

            for(i in 0 until rows)
            {
                // 获取姓名
                val name = sheet.getCell(0, i).contents
                // 获取座次
                val seat = sheet.getCell(1, i).contents
                val bean = ExcelBean(name, seat)
                list.add(bean)
            }
        }
        for((index, value) in list.withIndex())
        {
            JLog.d("i=$index,value=${value.name},${value.seat}")
        }
    }
}
