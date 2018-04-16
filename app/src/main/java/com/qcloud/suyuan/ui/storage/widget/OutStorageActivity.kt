package com.qcloud.suyuan.ui.storage.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.KeyEvent
import android.view.View
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.OutStorageBean
import com.qcloud.suyuan.ui.storage.presenter.impl.OutStoragePresenterImpl
import com.qcloud.suyuan.ui.storage.view.IOutStorageView
import com.qcloud.suyuan.widgets.dialog.TipDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_out_storage.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Description: 撤消入库
 * Author: gaobaiqiang
 * 2018/3/15 上午12:46.
 */
class OutStorageActivity : BaseActivity<IOutStorageView, OutStoragePresenterImpl>(), IOutStorageView {


    var keyword: String = ""   //搜索关键字
    var mCurrentBean: OutStorageBean.InfoBean? = null
    var errTip: TipDialog? = null

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (errTip == null) {
            errTip = TipDialog(this)
        }
        if (isShow) {
            errTip?.setTip(errMsg)
            errTip?.show()
        }
        stopLoadingDialog()
    }

    override val layoutId: Int
        get() = R.layout.activity_out_storage

    override fun initPresenter(): OutStoragePresenterImpl? {
        return OutStoragePresenterImpl()
    }

    override fun initViewAndData() {
        et_search.setOnKeyListener({ _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_UP) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    KeyBoardUtil.hideKeybord(this, et_search)
                    searchProductInfo()
                }
            }
            true
        })
        btn_confirm.setOnClickListener({ _ ->
            et_search.requestFocus()
            var numberStr = et_number.text.toString().trim()
            if (StringUtil.isBlank(numberStr)) {
                loadErr(getString(R.string.hint_input_storge_number))
                return@setOnClickListener
            }
            var number = numberStr.toInt()
            if (mCurrentBean != null) {
                if (number > mCurrentBean!!.surplusNum) {
                    loadErr(getString(R.string.toast_number_too_larger))
                    return@setOnClickListener
                }
                et_number.setText("")
                mPresenter?.outStorage(mCurrentBean!!.recordId, number)
            }
        })
    }

    //    搜索产品消息
    override fun searchProductInfo() {
        keyword = et_search.text.toString().trim()
        Timber.e("keyword:::::${keyword}")
        mPresenter?.search(keyword)
        startLoadingDialog()
        Observable.timer(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            et_search.setText("")
            et_search.requestFocus()
        }
    }

    //    加载产品消息
    override fun loadProductInfo(bean: OutStorageBean.InfoBean) {
        stopLoadingDialog()
        mCurrentBean = bean
        if (bean != null) {
            ll_info.visibility = View.VISIBLE
            ll_number.visibility = View.VISIBLE
            tv_name.setText("${bean.name}")
            tv_rule.setText("${bean.specification}")
            tv_classify.setText("${bean.classifyName}")
            tv_unit.setText("${bean.unit}")
            tv_mill.setText("${bean.millName}")
            tv_in_time.setText("${bean.storageTime}")
            tv_in_number.setText("${bean.storageNum}")
            tv_valid_time.setText("${bean.endDate}")
            tv_price.setText("${bean.stockPrice}")
            tv_storage_number.setText("${bean.surplusNum}")
            GlideUtil.loadImage(this, img_product, bean.image, R.drawable.bmp_product)
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, OutStorageActivity::class.java))
        }
    }
}