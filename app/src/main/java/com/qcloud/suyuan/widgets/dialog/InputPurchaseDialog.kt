package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.qclib.utils.ValidateUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.IDBean
import com.qcloud.suyuan.utils.IDCardUtil
import kotlinx.android.synthetic.main.dialog_input_purchase_info.*
import timber.log.Timber

/**
 * Description: 输入购买者信息
 * Author: gaobaiqiang
 * 2018/4/17 下午1:44.
 */
class InputPurchaseDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {
    var currPurchaser: IDBean? = null
    private var idList: MutableList<IDBean> = ArrayList()

    override val viewId: Int
        get() = R.layout.dialog_input_purchase_info

    init {
        initView()
    }

    private fun initView() {
        initIdText()
        btn_close.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
    }


    /**初始化身份证*/
    private fun initIdText() {
        val adapter = ArrayAdapter(mContext, R.layout.item_of_id_search, R.id.tv_context, disposeId())
        et_id.setAdapter<ArrayAdapter<String>?>(adapter)
        et_id.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            getPurchase(adapter.getItem(position))
        }
    }

    /**
     * 获取对应的身份证信息
     * */
    private fun getPurchase(idCode: String) {
        for (bean in idList) {
            if (StringUtil.isEquals(bean.idCode, idCode)) {
                currPurchaser = bean
                break
            }
        }
        refreshPurchase()
    }

    /**
     * 解析身份证
     * */
    private fun disposeId(): List<String> {
        idList.clear()
        idList.addAll(IDCardUtil.listAll())
        val list: MutableList<String> = ArrayList()
        for (bean in idList) {
            list.add(bean.idCode ?: "")
        }
        return list
    }

    private fun refreshPurchase() {
        Timber.e("dialog: $currPurchaser")
        if (currPurchaser != null) {
            et_name.setText(currPurchaser!!.name)
            et_mobile.setText(currPurchaser!!.mobile)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> dismiss()
            R.id.btn_confirm -> {
                if (check()) {
                    dismiss()
                    onBtnClickListener?.onBtnClick(v)
                }
            }
        }
    }

    private fun check(): Boolean {
        val idCode = et_id.text.toString().trim()
        val name = et_name.text.toString().trim()
        val mobile = et_mobile.text.toString().trim()

        if (StringUtil.isBlank(idCode)) {
            QToast.show(mContext, R.string.hint_input_user_id)
            return false
        }
        if (!ValidateUtil.isIdCard(idCode)) {
            QToast.show(mContext, R.string.tip_input_right_id_card)
            return false
        }
        if (StringUtil.isBlank(name)) {
            QToast.show(mContext, R.string.hint_input_purchase_name)
            return false
        }
        if (StringUtil.isBlank(mobile)) {
            QToast.show(mContext, R.string.hint_input_mobile)
            return false
        }

        if (currPurchaser == null) {
            currPurchaser = IDBean()
        }
        currPurchaser?.idCode = idCode
        currPurchaser?.name = name
        currPurchaser?.mobile = mobile

        return true
    }

    fun clearData() {
        currPurchaser = null
        et_id.setText("")
        et_name.setText("")
        et_mobile.setText("")
    }

}