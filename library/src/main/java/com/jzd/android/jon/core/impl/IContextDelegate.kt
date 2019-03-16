package com.jzd.android.jon.core.impl

/**
 * 完成一个重大使命：封装Activity和Fragment中的公用方法
 * @author Jzd
 * @since 1.0
 */
interface IContextDelegate
{
    companion object
    {
        const val REQUEST_CODE_PERMISSION: Int = 0x9000
        const val START_CODE_STRING: String = "START_CODE_STRING"
        const val START_CODE_STRING_LIST: String = "START_CODE_STRING_LIST"
        const val START_CODE_INT: String = "START_CODE_INT"
        const val START_CODE_LONG: String = "START_CODE_LONG"
        const val START_CODE_PARCELABLE_LIST: String = "START_CODE_PARCELABLE_LIST"
        const val START_CODE_PARCELABLE = "START_CODE_PARCELABLE"
        const val START_CODE_DOUBLE = "START_CODE_DOUBLE"
        const val START_CODE_BOOLEAN = "START_CODE_BOOLEAN"
        const val START_CODE_FLOAT = "START_CODE_FLOAT"
        const val START_CODE_SERIALIZABLE = "START_CODE_SERIALIZABLE"
        const val START_CODE_BUNDLE = "START_CODE_BUNDLE"
        const val START_FOR_RESULT_CODE = 0x8000
    }
}


