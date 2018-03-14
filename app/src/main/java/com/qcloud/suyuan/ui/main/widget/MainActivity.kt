package com.qcloud.suyuan.ui.main.widget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.annotation.NonNull
import android.view.KeyEvent
import com.qcloud.qclib.materialdesign.dialogs.MaterialDialog
import com.qcloud.qclib.materialdesign.enums.DialogAction
import com.qcloud.qclib.materialdesign.enums.GravityEnum
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseApplication
import com.qcloud.suyuan.realm.RealmHelper
import com.qcloud.suyuan.ui.main.presenter.impl.MainPresenterImpl
import com.qcloud.suyuan.ui.main.view.IMainView
import com.qcloud.suyuan.widgets.dialog.InputDialog
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 类说明：主页
 * Author: Kuzan
 * Date: 2018/3/12 15:29.
 */
class MainActivity: BaseActivity<IMainView, MainPresenterImpl>(), IMainView {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initPresenter(): MainPresenterImpl? {
        return MainPresenterImpl()
    }

    override fun initViewAndData() {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            MaterialDialog.Builder(this)
                    .content(R.string.toast_app_exit)
                    .contentColor(ApiReplaceUtil.getColor(this, R.color.colorTitle))
                    .positiveText(R.string.btn_confirm)
                    .negativeText(R.string.btn_cancel)
                    .onPositive(object : MaterialDialog.SingleButtonCallback {
                        override fun onClick(dialog: MaterialDialog, which: DialogAction) {
                            RealmHelper.instance.closeRealm()
                            BaseApplication.mAppManager?.appExit(this@MainActivity)
                        }
                    })
                    .show()
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}